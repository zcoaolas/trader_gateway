package com.sjtu.zc.trader.dao;

import com.sjtu.zc.trader.model.TraderUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Repository
public interface TraderUserDao {
    TraderUser getTraderUser(@Param("tu_name") String tu_name);

}
