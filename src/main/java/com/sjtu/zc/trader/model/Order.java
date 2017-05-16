package com.sjtu.zc.trader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private Integer o_id;
    private Integer t_id;
    private Integer c_id;
    private Double p_price;
    private Integer o_vol;
    private String o_type;
    private String o_status;
    private Timestamp o_create_time;
    private Integer o_year;
    private Integer o_month;
}
