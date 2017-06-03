package com.sjtu.zc.trader.service.Impl;

import com.sjtu.zc.trader.service.BrokerUserService;
import com.sjtu.zc.trader.util.Params;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by zcoaolas on 2017/5/30.
 */
@Service
@Transactional
public class BrokerUserServiceImpl implements BrokerUserService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public JSONObject getAllBrokerUsers() throws IOException {
        //String jsonStr = CommodityServiceImpl.getConnection(Params.brokerURLPrefix + "rest/getAllBrokerUsers" );
        String jsonStr = restTemplate.getForObject(Params.brokerURLPrefix + "rest/getAllBrokerUsers", String.class);
        JSONArray ja = JSONArray.fromObject(jsonStr);
        JSONObject commObj = new JSONObject();
        commObj.put("brokerUsers", ja);
        return commObj;
    }

    public JSONObject getCommoditiesByBuid(Integer bu_id) throws IOException {
        //String jsonStr = CommodityServiceImpl.getConnection(Params.brokerURLPrefix + "rest/getAllCommodityByBuId/" + bu_id);
        String jsonStr = restTemplate.getForObject(Params.brokerURLPrefix + "rest/getAllCommodityByBuId/" + bu_id, String.class);
        JSONArray ja = JSONArray.fromObject(jsonStr);
        JSONObject commObj = new JSONObject();
        commObj.put("commodities", ja);
        //commObj.put("bu_id", bu_id);
        return commObj;
    }
}
