package com.sjtu.zc.trader.service;

import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.model.UserOrder;

import javax.jms.Destination;
import java.util.List;

/**
 * Created by zcoaolas on 2017/5/22.
 */
public interface OrderService {

    //Order createLocalOrder(Order order);

    //void sendOrderMessage(Destination destination, final Order orderMessage);

    void addUserOrder(UserOrder userOrder);
}
