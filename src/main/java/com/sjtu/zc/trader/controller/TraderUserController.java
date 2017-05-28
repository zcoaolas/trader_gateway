package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.TraderUserService;
import com.sjtu.zc.trader.util.MyHttpHeader;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zcoaolas on 2017/5/16.
 */

@CrossOrigin
@RestController
public class TraderUserController {

    @Autowired
    private TraderUserService traderUserService;

    private UserPool tuPool = UserPool.getInstance();

    @RequestMapping(value = "/TraderUser/Login", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> authenticateUser(@RequestBody TraderUser traderUser) {
        TraderUser tu = traderUserService.getTUByNamePwd(traderUser.getTu_name(), traderUser.getTu_password());
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu != null){
            ret = JSONObject.fromObject(tu);
            int userHash = tuPool.addUser(tu);
            ret.put("tu_hash", userHash);
        }
        else{
            ret.put("message", "Log in Failed");
        }
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    /*@RequestMapping(value = {"/TraderUser", "/TraderUser/Login"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<JSONObject> supportOptions() {
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }*/
}
