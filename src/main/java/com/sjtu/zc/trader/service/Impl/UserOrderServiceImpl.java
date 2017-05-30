package com.sjtu.zc.trader.service.Impl;

import com.sjtu.zc.trader.dao.UserOrderDao;
import com.sjtu.zc.trader.model.UserOrder;
import com.sjtu.zc.trader.service.OrderService;
import com.sjtu.zc.trader.service.UserOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by zcoaolas on 2017/5/18.
 */
@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService {
    @Resource
    private UserOrderDao userOrderDao;
    @Resource
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserOrder createLocalUserOrder(UserOrder userOrder) {
        logger.info(this.getClass().getName() + ": " + userOrder.toString());

        userOrder.setUo_price(-1.0);
        userOrder.setUo_status("Placed");
        userOrder.setUo_create_time(new Timestamp(System.currentTimeMillis()));
        userOrderDao.createUserOrder(userOrder);

        orderService.placeUserOrder(userOrder);

        return userOrder;
    }


}
