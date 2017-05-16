package com.sjtu.zc.trader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zcoaolas on 2017/5/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraderUser {
    private Integer tu_id;
    private String tu_name;
    private Integer tu_role;
    private String tu_email;
    private String tu_password;
    private Integer t_id;

    /*public Integer getTu_id(){
        return tu_id;
    }
    public void setTu_id(Integer tu_id){
        this.tu_id = tu_id;
    }
    public String getTu_name(){
        return tu_name;
    }
    public void setTu_name(String tu_name){
        this.tu_name = tu_name;
    }
    public Integer getTu_role(){
        return tu_role;
    }
    public void setTu_role(Integer tu_role){
        this.tu_role = tu_role;
    }
    public String getTu_email(){
        return tu_email;
    }
    public void setTu_email(String tu_email){
        this.tu_email = tu_email;
    }
    public String getTu_password(){
        return tu_password;
    }
    public void setTu_password(String tu_password){
        this.tu_password = tu_password;
    }
    public Integer getT_id(){
        return t_id;
    }
    public void setT_id(Integer t_id){
        this.t_id = t_id;
    }*/
}
