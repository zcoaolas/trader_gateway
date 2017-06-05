package com.sjtu.zc.trader.dao;

import base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zcoaolas on 2017/6/5.
 */
public class SplitInfoDaoTest extends BaseTest {
    @Autowired
    private SplitInfoDao splitInfoDao;

    @Test
    public void getTUDaoTest() throws Exception{
        System.out.println(splitInfoDao.getOByUO(173));
    }
}