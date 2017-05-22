package com.sjtu.zc.trader.jms;

import com.sjtu.zc.trader.model.Order;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by zcoaolas on 2017/5/22.
 */
public class OrderMessageListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void onMessage(Message message) {

        TextMessage tm = (TextMessage) message;
        try {
            String orderStr = tm.getText();

            //orderService.placeOrder(order);
            JSONObject obj = JSONObject.fromObject(orderStr);
            obj.remove("o_create_time");
            Order order = (Order) JSONObject.toBean(obj, Order.class);

            logger.info(this.getClass().getName() + ": " + order.toString());
        } catch (JMSException e) {
            //e.printStackTrace();
            logger.info(this.getClass().getName() + ": " + e.getMessage());
        }
    }

}
