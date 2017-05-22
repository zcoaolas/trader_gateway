package com.sjtu.zc.trader.service;

import com.sjtu.zc.trader.model.UserOrder;

/**
 * Created by zcoaolas on 2017/5/18.
 *
 * UserOrder are orders placed by trader users
 */
public interface UserOrderService {
    UserOrder createLocalUserOrder(UserOrder userOrder);

}
