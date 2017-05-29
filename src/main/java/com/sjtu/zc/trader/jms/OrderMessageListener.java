package com.sjtu.zc.trader.jms;

import com.sjtu.zc.trader.dao.OrderDao;
import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.service.OrderService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;

/**
 * Created by zcoaolas on 2017/5/22.
 */
public class OrderMessageListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderService orderService;

    public void onMessage(Message message) {

        TextMessage tm = (TextMessage) message;
        try {
            String orderStr = tm.getText();

            //orderService.placeOrder(order);
            JSONObject obj = JSONObject.fromObject(orderStr);
            obj.remove("o_create_time");
            Order order = (Order) JSONObject.toBean(obj, Order.class);

            //Update order in local database when receive a new one
            orderService.updateOrder(order);

            logger.info(this.getClass().getName() + ": " + order.toString());
        } catch (JMSException e) {
            //e.printStackTrace();
            logger.info(this.getClass().getName() + ": " + e.getMessage());
        }
    }

}
