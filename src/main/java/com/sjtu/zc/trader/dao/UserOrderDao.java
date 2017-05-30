package com.sjtu.zc.trader.dao;

import com.sjtu.zc.trader.model.UserOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Repository
public interface UserOrderDao {
    void createUserOrder(UserOrder userOrder);

    List<UserOrder> getAllUserOrders();
}
