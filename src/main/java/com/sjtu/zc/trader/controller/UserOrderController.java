package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserOrder;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.TraderUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zcoaolas on 2017/5/18.
 */
@CrossOrigin
@RestController
public class UserOrderController {

    @Autowired
    private TraderUserService traderUserService;


    private UserPool tuPool = UserPool.getInstance();

    @RequestMapping(value = "/UserOrder", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> addUserOrder(@RequestHeader("TU-Name") String tu_name, @RequestHeader("TU-Hash") Integer tu_hash,
                                                   @RequestBody UserOrder userOrder) {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        // TODO
        return null;
    }

}