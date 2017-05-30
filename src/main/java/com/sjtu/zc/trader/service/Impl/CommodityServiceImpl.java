package com.sjtu.zc.trader.service.Impl;

import com.sjtu.zc.trader.service.CommodityService;
import com.sjtu.zc.trader.util.Params;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zcoaolas on 2017/5/29.
 */
@Service
@Transactional
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    @Qualifier("getRestTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;

    public JSONObject getAvailableCommodities() throws IOException {
        /*return restTemplate.getForObject(Params.brokerURLPrefix + "rest/getCommodityListByTId/" + Params.traderId,
                JSONObject.class);*/
        String jsonStr = getConnection(Params.brokerURLPrefix + "rest/getCommodityListByTId/" + Params.traderId);
        JSONArray ja = JSONArray.fromObject(jsonStr);
        JSONObject commObj = new JSONObject();
        commObj.put("commodities", ja);
        return commObj;
    }

    public JSONObject getBrokerUserOfCommodity(Integer c_id) throws IOException {
        //return restTemplate.getForObject(Params.brokerURLPrefix + "rest/getBrokerUserByCId/" + c_id,
        //       JSONObject.class);

        String jsonStr = getConnection(Params.brokerURLPrefix + "rest/getBrokerUserByCId/" + c_id);
        return JSONObject.fromObject(jsonStr);
    }

    public static String getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(15000);// 连接超时 单位毫秒
        conn.setReadTimeout(15000);// 读取超时 单位毫秒
        byte bytes[]=new byte[4096];
        InputStream inStream=conn.getInputStream();
        inStream.read(bytes, 0, inStream.available());
        String str = new String(bytes, "UTF-8");

        return str;
    }

}
