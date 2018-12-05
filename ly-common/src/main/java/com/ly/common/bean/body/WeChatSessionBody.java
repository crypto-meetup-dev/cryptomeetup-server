package com.ly.common.bean.body;

import org.apache.commons.lang.StringUtils;

/**
 * @Author liyang
 * @Create 2018-2-22
 * 获取微信用户session信息
 *
"openid": "OPENID",
"session_key": "SESSIONKEY",
"unionid": "UNIONID"

"errcode": 40029,
"errmsg": "invalid code"
 **/
public class WeChatSessionBody {

    //用户唯一标识
    private String openid;
    //会话密钥
    private String session_key;
    //用户在开放平台的唯一标识符。本字段在满足一定条件的情况下才返回。具体参看UnionID机制说明
    private String unionid;
    //错误码
    private String errcode;
    //错误信息
    private String errmsg;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return StringUtils.isEmpty(unionid)?openid:unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
