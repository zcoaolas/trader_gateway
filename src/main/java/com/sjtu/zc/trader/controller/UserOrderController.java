package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserOrder;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.OrderService;
import com.sjtu.zc.trader.service.TraderUserService;
import com.sjtu.zc.trader.service.UserOrderService;
import com.sjtu.zc.trader.util.MyHttpHeader;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

}