package com.sjtu.zc.trader.dao;

import com.sjtu.zc.trader.model.Order;
import com.sjtu.zc.trader.model.SplitInfo;
import com.sjtu.zc.trader.model.UserOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zcoaolas on 2017/6/5.
 */
@Repository
public interface SplitInfoDao {

    List<UserOrder> getCancelableUserOrders();

    void insertSplitInfo(SplitInfo splitInfo);

    List<Order> getOByUO(Integer uo_id);
}
