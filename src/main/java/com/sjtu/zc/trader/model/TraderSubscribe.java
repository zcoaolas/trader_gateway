package com.sjtu.zc.trader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by zcoaolas on 2017/5/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraderSubscribe implements Serializable {
    private Integer ts_id;
    private Integer t_id;
    private Integer c_id;
}
