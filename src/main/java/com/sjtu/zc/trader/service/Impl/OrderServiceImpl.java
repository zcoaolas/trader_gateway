package com.sjtu.zc.trader.service.Impl;

import com.sjtu.zc.trader.dao.OrderDao;
import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.model.UserOrder;
import com.sjtu.zc.trader.service.OrderService;
import com.sjtu.zc.trader.util.Params;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by zcoaolas on 2017/5/22.
 *
 * OrderService merge UserOrders into an Order
 * This Order will be sent to the Broker Gateway
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void placeUserOrder(UserOrder uo){

        // When match the criteria, place an order automatically
        Integer uoVol = uo.getUo_vol();
        try {
            while (uoVol > Params.orderVolMax) {
                // Random vol
                Random random = new Random();
                int r = random.nextInt(Params.orderVolMax) % (Params.orderVolMax - Params.orderVolMin + 1) + Params.orderVolMin;
                // Split order
                Order order = new Order(
                        null, Params.traderId, uo.getC_id(), uo.getUo_price(), r, uo.getUo_type(), uo.getUo_status(),
                        uo.getUo_create_time(), uo.getUo_year(), uo.getUo_month(), uo.getUo_is_buy(), uo.getUo_limit_value(), uo.getUo_stop_value()
                );
                order = createLocalOrder(order);
                sendOrderMessage(destination, order);

                uoVol -= r;
                Thread.sleep(3000);
            }
        }
        catch (InterruptedException ie) {
            logger.error(ie.getMessage());
        }
        Order o = new Order(
                null, Params.traderId, uo.getC_id(), uo.getUo_price(), uoVol, uo.getUo_type(), uo.getUo_status(),
                uo.getUo_create_time(), uo.getUo_year(), uo.getUo_month(), uo.getUo_is_buy(), uo.getUo_limit_value(), uo.getUo_stop_value()
        );
        o = createLocalOrder(o);
        sendOrderMessage(destination, o);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }


    private Order createLocalOrder(Order order) {
        orderDao.createOrder(order);
        return order;
    }

    private void sendOrderMessage(Destination destination, final Order orderMessage) {
        logger.info(this.getClass().getName() + ": " + orderMessage.toString());

        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                //return session.createObjectMessage(orderMessage);
                JSONObject json = JSONObject.fromObject(orderMessage);
                json.remove("o_create_time");
                return session.createTextMessage(json.toString());
            }
        });
    }

    /*private Order userOrdersToOrder(List<UserOrder> userOrderList){
        if(userOrderList.size() > 0) {
            UserOrder uo = userOrderList.get(0);
            return new Order(
                    null, Params.traderId, uo.getC_id(), uo.getUo_price(), uo.getUo_vol(), uo.getUo_type(), uo.getUo_status(),
                    uo.getUo_create_time(), uo.getUo_year(), uo.getUo_month(), uo.getUo_is_buy(), uo.getUo_limit_value(), uo.getUo_stop_value()
            );
        }
        else {
            return null;
        }
    }*/
}
