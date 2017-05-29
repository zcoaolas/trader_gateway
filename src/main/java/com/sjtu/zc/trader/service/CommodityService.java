package com.sjtu.zc.trader.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;


/**
 * Created by zcoaolas on 2017/5/28.
 */
public interface CommodityService {

    JSONObject getAvailableCommodities() throws IOException;

    JSONObject getBrokerUserOfCommodity(Integer c_id) throws IOException ;

}
