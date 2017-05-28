package com.sjtu.zc.trader.jms;

import com.sjtu.zc.trader.controller.ChatController;
import com.sjtu.zc.trader.model.ActualOrder;
import com.sjtu.zc.trader.util.TimestampMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by zcoaolas on 2017/5/28.
 */
public class ActualOrderMessageListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ChatController chatController;

    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            String actualOrderJStr = tm.getText();

            String[] formats={"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};
            JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));
            JSONObject jsonObject=JSONObject.fromObject(actualOrderJStr);
            ActualOrder actualOrder = (ActualOrder) JSONObject.toBean(jsonObject, ActualOrder.class);
            logger.info("Received ActualOrder {}", actualOrder.toString());

            // Forward this ActualOrder to Frontend
            chatController.actualOrderMessage(actualOrder);
        }
        catch (JMSException e) {
            //e.printStackTrace();
            logger.info(this.getClass().getName() + ": " + e.getMessage());
        }
    }
}
