package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.OrderService;
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
 * Created by zcoaolas on 2017/5/30.
 */
@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    private UserPool tuPool = UserPool.getInstance();

    @RequestMapping(value = "/Order/All", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getAllOrders(@RequestHeader("TU-Name") String tu_name,
                                                   @RequestHeader("TU-Hash") Integer tu_hash) {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        List<Order> orderList = orderService.getAllOrders();
        JSONArray ja = new JSONArray();
        for (Order o : orderList) {
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            JSONObject json = JSONObject.fromObject(o, config);
            ja.add(json);
        }
        ret.put("orders", ja);
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }
}
