package com.ly.common.constant.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author liyang
 * @Create 2018/5/16
 * 短信通道模板
 */
public enum EnumSmsChannelTemplate {


    /**
     * 登录验证
     */
    LOGIN_NAME_LOGIN("loginCodeChannel", "103house好家"),
    /**
     * 注册验证
     */
    REGISTER_CODE_CHANNEL("registerCodeChannel", "103house好家"),
    /**
     *
     */
    SERVICE_STATUS_CHANGE("systemStatusChannel", "103house好家"),

    NONE("", "");


    /**
     * 模板名称
     */
    @Getter
    @Setter
    private String template;
    /**
     * 模板签名
     */
    @Getter
    @Setter
    private String signName;

    EnumSmsChannelTemplate(String template, String signName) {
        this.template = template;
        this.signName = signName;
    }
}
