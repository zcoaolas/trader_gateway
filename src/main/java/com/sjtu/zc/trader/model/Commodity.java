package com.sjtu.zc.trader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commodity {
    private Integer c_id;
    private Integer bu_id;
    private String c_name;
    private Double c_price;
}
