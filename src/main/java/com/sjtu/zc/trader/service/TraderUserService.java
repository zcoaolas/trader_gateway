package com.sjtu.zc.trader.service;

import com.sjtu.zc.trader.model.TraderUser;

/**
 * Created by zcoaolas on 2017/5/16.
 */
public interface TraderUserService {
    TraderUser getTUByNamePwd(String tu_name, String tu_pwd);
}
