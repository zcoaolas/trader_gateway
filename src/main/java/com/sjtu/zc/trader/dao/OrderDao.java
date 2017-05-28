package com.sjtu.zc.trader.dao;

import com.sjtu.zc.trader.model.Order;
import org.springframework.stereotype.Repository;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Repository
public interface OrderDao {
    void createOrder(Order order);

    void updateOrder(Order order);
}
