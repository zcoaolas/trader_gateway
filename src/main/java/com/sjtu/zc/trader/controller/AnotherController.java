package com.sjtu.zc.trader.controller;

import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.model.UserPool;
import com.sjtu.zc.trader.service.Impl.CommodityServiceImpl;
import com.sjtu.zc.trader.util.MyHttpHeader;
import com.sjtu.zc.trader.util.Params;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by zcoaolas on 2017/5/30.
 */
@CrossOrigin
@RestController
public class AnotherController {

    private UserPool tuPool = UserPool.getInstance();
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/MarketDepth", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONObject> getMarketDepth(@RequestHeader("TU-Name") String tu_name,
                                                     @RequestHeader("TU-Hash") Integer tu_hash,
                                                     @RequestParam Integer cid, @RequestParam Integer year,
                                                     @RequestParam Integer month, @RequestParam Integer isFloat) throws IOException {
        TraderUser tu = tuPool.validateUser(tu_name, tu_hash);
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        if (tu == null) {
            return new ResponseEntity<>(ret, headers, HttpStatus.UNAUTHORIZED);
        }

        String params = String.format("cid=%d&year=%d&month=%d&isFloat=%d", cid, year, month, isFloat );
        //String jsonStr = CommodityServiceImpl.getConnection(Params.brokerURLPrefix + "rest/retMarketDepth?" + params);
        String jsonStr = restTemplate.getForObject(Params.brokerURLPrefix + "rest/retMarketDepth?" + params, String.class);
        ret = JSONObject.fromObject(jsonStr);

        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/MarketDepth", method = RequestMethod.OPTIONS)
    public ResponseEntity<JSONObject> supportAnotherOptions(@RequestHeader("TU-Name") String tu_name,
                                                     @RequestHeader("TU-Hash") Integer tu_hash,
                                                     @RequestParam Integer cid, @RequestParam Integer year,
                                                     @RequestParam Integer month, @RequestParam Integer isFloat) {
        JSONObject ret = new JSONObject();
        HttpHeaders headers = MyHttpHeader.getOptionsHttpHeaders();
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }
}
