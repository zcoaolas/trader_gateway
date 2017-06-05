package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserOrder;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.OrderService;
import com.sjtu.zc.trader.service.TraderUserService;
import com.sjtu.zc.trader.service.UserOrderService;
import com.sjtu.zc.trader.util.DateJsonValueProcessor;
import com.sjtu.zc.trader.util.MyHttpHeader;
import com.sjtu.zc.trader.util.Params;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.sql.Timestamp;
import java.util.List;


/**
 * Created by zcoaolas on 2017/5/18.
 */
@CrossOrigin
@RestController
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;
    private UserPool tuPool = UserPool.getInstance();
    @Resource
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/UserOrder", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> addUserOrder(@RequestHeader("TU-Name") String tu_name, @RequestHeader("TU-Hash") Integer tu_hash,
                                                   @RequestBody UserOrder userOrder) {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        userOrder.setTu_id(tu.getTu_id());
        userOrder = userOrderService.createLocalUserOrder(userOrder);
        ret.put("msg", "Success");
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/UserOrder/All", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getAllUserOrders(@RequestHeader("TU-Name") String tu_name, @RequestHeader("TU-Hash") Integer tu_hash) {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null) {
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        List<UserOrder> userOrderList = userOrderService.getAllUserOrders();
        JSONArray uoArray = new JSONArray();
        for (UserOrder uo : userOrderList) {
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            uoArray.add(JSONObject.fromObject(uo, config));
        }
        ret.put("user_orders", uoArray);
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/UserOrder/Cancelable", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getCancelableOrders(@RequestHeader("TU-Name") String tu_name, @RequestHeader("TU-Hash") Integer tu_hash) {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null) {
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        List<UserOrder> userOrderList = userOrderService.getCancelableUserOrders();
        JSONArray uoArray = new JSONArray();
        for (UserOrder uo : userOrderList) {
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            uoArray.add(JSONObject.fromObject(uo, config));
        }
        ret.put("cancelable_user_orders", uoArray);
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/UserOrder/Cancel", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getCancelableOrders(@RequestHeader("TU-Name") String tu_name,
                                                          @RequestHeader("TU-Hash") Integer tu_hash,
                                                          @RequestParam("uo_id") Integer uo_id) {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null) {
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        List<Order> orderList = orderService.getOrdersByUoid(uo_id);
        for (Order o : orderList) {
            JSONObject requestBody = new JSONObject();
            requestBody.put("o_id", o.getO_id());
            requestBody.put("c_id", o.getC_id());
            requestBody.put("t_id", o.getT_id());
            requestBody.put("o_year", o.getO_year());
            requestBody.put("o_month", o.getO_month());
            requestBody.put("o_is_buy", o.getO_is_buy());
            sendCancelMessage(destination, requestBody);
        }

        ret.put("msg", "Success");
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    private void sendCancelMessage(Destination destination, final JSONObject obj) {
        logger.info(this.getClass().getName() + ": " + obj.toString());

        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(obj.toString());
            }
        });
    }
}