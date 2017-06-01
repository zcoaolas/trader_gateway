package com.sjtu.zc.trader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOrder implements Serializable {
    private Integer uo_id;
    private String uo_is_sent;
    private Integer c_id;
    private Integer tu_id;
    private Double uo_price;
    private String uo_type;
    private Integer uo_vol;
    private String uo_status;
    private Timestamp uo_create_time;
    private Integer uo_year;
    private Integer uo_month;
    private Integer uo_is_buy;
    private String uo_limit_value;
    private String uo_stop_value;
}
