package com.sjtu.zc.trader.service;

import net.sf.json.JSONObject;


/**
 * Created by zcoaolas on 2017/5/28.
 */
public interface CommodityService {

    JSONObject getAvailableCommodities(Integer traderId);
}
