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
public class WorkingOrder {
    private Integer wo_id;
    private Integer o_id;
    private Double wo_price;
    private Integer wo_vol;
    private Timestamp wo_modified_time;
}
