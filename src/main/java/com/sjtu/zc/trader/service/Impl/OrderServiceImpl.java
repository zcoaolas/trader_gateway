package com.sjtu.zc.trader.service.Impl;

import com.sjtu.zc.trader.dao.OrderDao;
import com.sjtu.zc.trader.dao.SplitInfoDao;
import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.model.SplitInfo;
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
import java.util.*;


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
    private SplitInfoDao splitInfoDao;
    @Resource
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void placeUserOrder(UserOrder uo){

        Runnable task = () -> {
            // When match the criteria, place an order automatically
            Integer timeLimit = uo.getUo_time_limit();
            Integer maxOrderVol = uo.getUo_max_order_vol();
            Integer uoVol = uo.getUo_vol();
            Integer splitN = timeLimit/Params.orderInterval + 1;
            LinkedList<ArrayList<Integer>> splitRes = splitOrder(uoVol, splitN, maxOrderVol);
            try {
                for (ArrayList<Integer> timeV: splitRes) {
                    Integer timeVAll = timeLimit / splitN;
                    for (int i = 0; i < timeV.size(); i++) {
                        Order order = new Order(
                                null, Params.traderId, uo.getC_id(), uo.getUo_price(), timeV.get(i), uo.getUo_type(), uo.getUo_status(),
                                uo.getUo_create_time(), uo.getUo_year(), uo.getUo_month(), uo.getUo_is_buy(), uo.getUo_limit_value(), uo.getUo_stop_value()
                        );
                        order = createLocalOrder(order);
                        createSplitInfo(new SplitInfo(order.getO_id(), uo.getUo_id()));
                        sendOrderMessage(destination, order);
                        Thread.sleep(timeVAll / timeV.size());
                    }
                }
            }
            catch (InterruptedException ie) {
                logger.error(ie.getMessage());
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }


    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public List<Order> getOrdersByUoid(Integer uo_id) {
        return splitInfoDao.getOByUO(uo_id);
    }


    private Order createLocalOrder(Order order) {
        orderDao.createOrder(order);
        return order;
    }

    private void createSplitInfo(SplitInfo si) {
        splitInfoDao.insertSplitInfo(si);
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
    private static List<Integer> historyVol = Arrays.asList( 60, 55, 47, 41, 30, 39, 41, 49, 60, 49, 38);

    private LinkedList<ArrayList<Integer>> splitOrder(Integer totalVol, Integer splitN, Integer maxOrderVol) {
        LinkedList<ArrayList<Integer>> res = new LinkedList<>();
        for (int i = 0; i < splitN - 1; i++) {
            ArrayList<Integer> array = new ArrayList<>();
            Integer v = rangeRandom(totalVol * nthPercent(i, splitN) * 0.9, totalVol * nthPercent(i, splitN) * 1.1);
            totalVol -= v;

            while (v > maxOrderVol) {
                Integer vv = rangeRandom(maxOrderVol * 0.8, maxOrderVol * 1.0);
                array.add(vv);
                v -= vv;
            }
            array.add(v);
            res.add(array);
        }
        // Rest
        ArrayList<Integer> array = new ArrayList<>();
        while (totalVol > maxOrderVol) {
            Integer vv = rangeRandom(maxOrderVol * 0.8, maxOrderVol * 1.0);
            array.add(vv);
            totalVol -= vv;
        }
        array.add(totalVol);
        res.add(array);
        return res;
    }

    private Double nthPercent(Integer n, Integer totalN) {
        Double total = 0.0;
        for (int i = 0; i < totalN; i++) {
            total += historyVol.get(i);
        }
        return historyVol.get(n) / total;
    }

    private Integer rangeRandom(Double fromVal, Double toVal) {
        Integer from = (int)Math.round(fromVal);
        Integer to = (int)Math.round(toVal);
        Random random = new Random();
        return random.nextInt(to) % (to - from + 1) + from;
    }
}
