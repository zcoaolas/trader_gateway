package com.sjtu.zc.trader.service.Impl;

import com.sjtu.zc.trader.service.BrokerUserService;
import com.sjtu.zc.trader.util.Params;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by zcoaolas on 2017/5/30.
 */
@Service
@Transactional
public class BrokerUserServiceImpl implements BrokerUserService {

    @Override
    public JSONObject getAllBrokerUsers() throws IOException {
        String jsonStr = CommodityServiceImpl.getConnection(Params.brokerURLPrefix + "rest/getAllBrokerUsers" );
        JSONArray ja = JSONArray.fromObject(jsonStr);
        JSONObject commObj = new JSONObject();
        commObj.put("brokerUsers", ja);
        return commObj;
    }

    public JSONObject getCommoditiesByBuid(Integer bu_id) throws IOException {
        String jsonStr = CommodityServiceImpl.getConnection(Params.brokerURLPrefix + "rest/getAllCommodityByBuId/" + bu_id);
        JSONArray ja = JSONArray.fromObject(jsonStr);
        JSONObject commObj = new JSONObject();
        commObj.put("commodities", ja);
        //commObj.put("bu_id", bu_id);
        return commObj;
    }
}
