package com.sjtu.zc.trader.service;

import base.BaseTest;
import com.sjtu.zc.trader.model.TraderUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zcoaolas on 2017/5/16.
 */
public class TraderUserServiceTest extends BaseTest {
    @Autowired
    private TraderUserService traderUserService;

    @Test
    public void getTUTest() throws Exception{
        String tu_name = "zhanchen";
        String tu_password = "zhanchen";
        TraderUser tu = traderUserService.getTUByNamePwd(tu_name, tu_password);
        assert tu != null;
    }
}
