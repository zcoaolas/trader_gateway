package com.sjtu.zc.trader.service.Impl;

import com.sjtu.zc.trader.dao.TraderUserDao;
import com.sjtu.zc.trader.model.TraderUser;
import com.sjtu.zc.trader.service.TraderUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Service
@Transactional
public class TraderUserServiceImpl implements TraderUserService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TraderUserDao traderUserDao;

    public TraderUser getTUByNamePwd(String tu_name, String tu_pwd){
        logger.info(this.getClass().getName() + "--getTUByNamePwd tu_name:" + tu_name);
        TraderUser tu = traderUserDao.getTraderUser(tu_name);
        if (tu != null && tu.getTu_password().equals(tu_pwd)) {
            tu.setTu_password("");
            return tu;
        }
        else
            return null;
    }


}
