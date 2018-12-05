package com.ly.common.bean.body;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther Administrator liyang
 * @Create 2018/8/17 3:12
 */
public class MobileLoginBody {

    public static final String CHANNEL = "channel";
    public static final String CHANNEL_KEY = "channel_key";
    public static final String CHANNEL_EVENT = "channel_event";
    public static final String CHANNEL_WECHAT = "WECHAT";
    public static final String CHANNEL_WEIBO = "WEIBO";
    public static final String CHANNEL_EOS = "EOS";
    public static final String CHANNEL_EVENT_LOGIN = "login";
    public static final String GRANT_TYPE_MOBILE = "mobile";
    public static final String GRANT_TYPE = "grant_type";
    public static final String NICK_NAME = "nickName";
    public static final String AVATAR_URL = "avatarUrl";
    public static final String APP_ID = "appId";
    public static final String GENDER = "gender";
    public static final String ACCOUNT = "account";

    String mobile;
    String channel;
    String channel_key;
    String channel_event;
    String grant_type;
    String nickName;
    String avatarUrl;
    String gender;
    String appId;
    String account;

    public MobileLoginBody(HttpServletRequest request) {
        this.mobile = request.getParameter(GRANT_TYPE_MOBILE);
        this.channel = request.getParameter(CHANNEL);
        this.channel_key = request.getParameter(CHANNEL_KEY);
        this.channel_event = request.getParameter(CHANNEL_EVENT);
        this.grant_type = request.getParameter(GRANT_TYPE);
        this.nickName = request.getParameter(NICK_NAME);
        this.avatarUrl = request.getParameter(AVATAR_URL);
        this.gender = request.getParameter(GENDER);
        this.appId = request.getParameter(APP_ID);
        this.account = request.getParameter(ACCOUNT);
    }

    public boolean isMobile() {
        return GRANT_TYPE_MOBILE.equals(grant_type);
    }

    public boolean isMobileWeChatLogin() {
        return CHANNEL_WECHAT.equals(getChannel()) && CHANNEL_EVENT_LOGIN.equals(getChannel_event());
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isEOS(){
        return CHANNEL_EOS.equalsIgnoreCase(getChannel());
    }

    public boolean isMobileEmpty() {
        return StringUtils.isEmpty(getMobile());
    }

    public boolean isChannelEmpty() {
        return StringUtils.isEmpty(getChannel());
    }

    public boolean isChannelKeyEmpty() {
        return StringUtils.isEmpty(getChannel_key());
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_key() {
        return channel_key;
    }

    public void setChannel_key(String channel_key) {
        this.channel_key = channel_key;
    }

    public String getChannel_event() {
        return channel_event;
    }

    public void setChannel_event(String channel_event) {
        this.channel_event = channel_event;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
