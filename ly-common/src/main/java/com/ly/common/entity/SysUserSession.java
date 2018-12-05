package com.ly.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ly.common.vo.UserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户会话信息
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-17
 */
@Data
@TableName("sys_user_session")
public class SysUserSession extends Model<SysUserSession> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户的ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 会话渠道
     */
    private String channel;
    /**
     * 会话的KEY
     */
    @TableField("session_key")
    private String sessionKey;
    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionid;

    /**
     * 用户的唯一标识符
     */
    @TableField("open_id")
    private String openId;
    /**
     * 修改的时间
     */
    @TableField("update_time")
    private Date updateTime;

    @TableField("create_time")
    private Date createTime;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;

    private String gender;

    @TableField("app_id")
    private Integer appId;

    UserVO user;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUserSession{" +
        ", id=" + id +
        ", userId=" + userId +
        ", channel=" + channel +
        ", sessionKey=" + sessionKey +
        ", unionid=" + unionid +
        ", openId=" + openId +
        ", nickName=" + nickName +
        ", avatarUrl=" + avatarUrl +
        ", gender=" + gender +
        ", appId=" + appId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }

}
