package com.sjtu.zc.trader.dao;

import base.BaseTest;
import com.sjtu.zc.trader.model.TraderUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zcoaolas on 2017/5/16.
 */
public class TraderUserDaoTest extends BaseTest {
    @Autowired
    private TraderUserDao traderUserDao;

    @Test
    public void getTUDaoTest() throws Exception{
        String tu_name = "zhanchen";
        String tu_password = "zhanchen";

    }
}
