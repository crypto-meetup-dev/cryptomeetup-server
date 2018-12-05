package com.ly.bt.model.dto;

import com.ly.bt.model.entity.BtPointInfo;
import lombok.Data;

/**
 * @auther Administrator liyang
 * @create 2018/11/19 6:22
 */
@Data
public class BtPointInfoDTO extends BtPointInfo {
    private String latitude;
    private String longitude;

}
