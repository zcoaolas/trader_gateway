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
public class ActualOrder {
    private Integer ao_id;
    private Integer sell_o_id;
    private Double ao_price;
    private Integer ao_vol;
    private Timestamp ao_create_time;
    private Integer buy_o_id;
}
