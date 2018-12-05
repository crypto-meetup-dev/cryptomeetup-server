package com.ly.admin.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-23
 */
@Data
@TableName("sys_user_wallet")
public class SysUserWallet extends Model<SysUserWallet> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户钱包主键
     */
    private Integer id;
    /**
     * 用户中心的用户唯一编号
     */
    @TableField("user_open_id")
    private String userOpenId;
    @TableField("user_amount")
    private BigDecimal userAmount;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("pay_password")
    private String payPassword;
    /**
     * 0:代表未开启支付密码，1:代表开发支付密码
     */
    @TableField("is_open")
    private Integer isOpen;
    /**
     * 平台进行用户余额更改时，首先效验key值，否则无法进行用户余额更改操作
     */
    @TableField("check_key")
    private String checkKey;
    /**
     * 基于mysql乐观锁，解决并发访问
     */
    private Integer version;


    /**
     * 0-正常，1-删除
     */
    @TableField("del_flag")
    private String delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUserWallet{" +
                ", id=" + id +
                ", userOpenId=" + userOpenId +
                ", userAmount=" + userAmount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", payPassword=" + payPassword +
                ", isOpen=" + isOpen +
                ", checkKey=" + checkKey +
                ", version=" + version +
                "}";
    }
}
