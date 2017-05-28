package com.sjtu.zc.trader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by zcoaolas on 2017/5/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraderUser implements Serializable {
    private Integer tu_id;
    private String tu_name;
    private Integer tu_role;
    private String tu_email;
    private String tu_password;
    private Integer t_id;
}
