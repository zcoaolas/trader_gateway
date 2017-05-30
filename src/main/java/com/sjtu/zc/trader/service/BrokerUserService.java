package com.sjtu.zc.trader.service;

import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created by zcoaolas on 2017/5/30.
 */
public interface BrokerUserService {
    JSONObject getAllBrokerUsers() throws IOException;

    JSONObject getCommoditiesByBuid(Integer bu_id) throws IOException;
}
