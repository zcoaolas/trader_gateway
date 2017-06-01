package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserOrder;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.OrderService;
import com.sjtu.zc.trader.service.TraderUserService;
import com.sjtu.zc.trader.service.UserOrderService;
import com.sjtu.zc.trader.util.DateJsonValueProcessor;
import com.sjtu.zc.trader.util.MyHttpHeader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private UserPool tuPool = UserPool.getInstance();

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
}