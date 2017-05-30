package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.BrokerUserService;
import com.sjtu.zc.trader.util.MyHttpHeader;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by zcoaolas on 2017/5/30.
 */
@CrossOrigin
@RestController
public class BrokerUserController {
    @Autowired
    private BrokerUserService brokerUserService;
    private UserPool tuPool = UserPool.getInstance();

    @RequestMapping(value = "/BrokerUser/All", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getAllBrokerUsers(@RequestHeader("TU-Name") String tu_name,
                                                        @RequestHeader("TU-Hash") Integer tu_hash) throws IOException {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }
        // Return all available commodities visible to this trader
        ret = brokerUserService.getAllBrokerUsers();
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/BrokerUser/{bu_id}/Commodities", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getAllBrokerUsers(@RequestHeader("TU-Name") String tu_name,
                                                        @RequestHeader("TU-Hash") Integer tu_hash,
                                                        @PathVariable Integer bu_id) throws IOException {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }
        // Return all available commodities visible to this trader
        ret = brokerUserService.getCommoditiesByBuid(bu_id);
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }
}
