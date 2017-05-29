package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.CommodityService;
import com.sjtu.zc.trader.util.MyHttpHeader;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by zcoaolas on 2017/5/29.
 */
@CrossOrigin
@RestController
public class CommodityController {

    @Autowired
    private CommodityService commodityService;
    private UserPool tuPool = UserPool.getInstance();

    @RequestMapping(value = "/Commodity/All", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getAllCommodities(@RequestHeader("TU-Name") String tu_name,
                                                        @RequestHeader("TU-Hash") Integer tu_hash) throws IOException {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }
        // Return all available commodities visible to this trader
        ret = commodityService.getAvailableCommodities();
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/Commodity/{c_id}/BrokerUser", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getBrokerUserByCommodity(@RequestHeader("TU-Name") String tu_name,
                                                        @RequestHeader("TU-Hash") Integer tu_hash,
                                                        @PathVariable Integer c_id) throws IOException {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null){
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        ret = commodityService.getBrokerUserOfCommodity(c_id);
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
        /*String s = commodityService.getConnection();
        System.out.println(s);
        return null;*/
    }
}
