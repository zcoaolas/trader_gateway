package com.sjtu.zc.trader.jms;

import com.sjtu.zc.trader.controller.ChatController;
import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.service.OrderService;
import com.sjtu.zc.trader.util.Params;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by zcoaolas on 2017/5/30.
 *
 * Listen all kinds of messages
 */
public class CompoundMessageListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderService orderService;
    @Autowired
    private ChatController chatController;

    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            JSONObject obj = JSONObject.fromObject(tm.getText());

            if(obj.has("actual_order")) {
                // This is an actual order and a market depth
                chatController.sendMessage(obj);
                logger.info(this.getClass().getName() + ": " + obj.toString());
            }
            else {
                // This is an order message
                obj.remove("o_create_time");
                Order order = (Order) JSONObject.toBean(obj, Order.class);
                if (Params.traderId.equals(order.getT_id())) {
                    //Update order in local database when receive a new one
                    orderService.updateOrder(order);
                    logger.info(this.getClass().getName() + ": " + order.toString());
                }
            }
        } catch (JMSException jmse) {
            logger.info(this.getClass().getName() + ": " + jmse.getMessage());
        }
    }
}
