package com.sjtu.zc.trader.dao;

import base.BaseTest;
import com.sjtu.zc.trader.model.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zcoaolas on 2017/5/16.
 */
public class OrderDaoTest extends BaseTest {
    @Autowired
    private OrderDao orderDao;

    @Test
    public void updateOrderTest() throws Exception{
        Order order = new Order(
                3, 1, 1, 123.21, 500, "Limit", "Placed", null,
                2019, 10, 0, "3.7%", ""
        );
        orderDao.updateOrder(order);
    }
}
