package com.sjtu.zc.trader.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcoaolas on 2017/4/6.
 */
@Component
public class UserPool {
    private Map<String, TraderUser> userMap;
    private static final String salt = "9f0sdu>jp-523";
    private static UserPool userPool = null;

    private UserPool() {
        userMap = new HashMap<String, TraderUser>();
    }

    public static UserPool getInstance(){
        if (userPool == null)
            userPool = new UserPool();
        return userPool;
    }

    /**
     *
     * @param u a User
     * @return the hash code of the User
     */
    public int addUser(TraderUser u){
        userMap.put(u.getTu_name(), u);
        return (u.getTu_name() + u.getT_id().toString() + salt).hashCode();
    }

    public TraderUser getUser(String uname){
        return userMap.get(uname);
    }

    /**
     *
     * @param uname u_name in http header
     * @param hashCode u_hash in http header
     * @return an User if valid, null otherwise
     */
    public TraderUser validateUser(String uname, Integer hashCode){
        if(userMap.containsKey(uname)){
            TraderUser u = userMap.get(uname);
            Integer uid = u.getT_id();
            if (hashCode == (uname + uid + salt).hashCode())
                return u;
        }
        return null;
    }

    public void userLogout(String u_name){
        if (userMap.containsKey(u_name)) {
            userMap.remove(u_name);
        }
    }
}
