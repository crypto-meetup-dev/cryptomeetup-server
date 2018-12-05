package com.ly.common.vo;

import lombok.Data;

/**
 * 商场信息
 *
 * @auther Administrator liyang
 * @Create 2018/10/8 11:00
 */
@Data
public class ShopAppVo {
    private Integer appId;
    private String appName;
    private String wxAppId;
    private String appSecret;
    private String phoneNo;
}
