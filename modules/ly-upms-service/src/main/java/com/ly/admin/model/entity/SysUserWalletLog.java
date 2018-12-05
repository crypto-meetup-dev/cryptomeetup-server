package com.ly.admin.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 钱包日志信息
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-24
 */
@TableName("sys_user_wallet_log")
public class SysUserWalletLog extends Model<SysUserWalletLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 用户ID
     */
    @TableField("open_id")
    private String openId;
    /**
     * 关联对象的ID
     */
    @TableField("obj_id")
    private String objId;
    /**
     * 产生金额
     */
    private BigDecimal money;
    /**
     * 信息
     */
    private String title;
    /**
     * 类型
     */
    private String type;
    /**
     * 支出还是收入
     */
    private Integer expense;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 删除标记 0正常 1删除
     */
    @TableField("del_flag")
    private String delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUserWalletLog{" +
        ", id=" + id +
        ", openId=" + openId +
        ", objId=" + objId +
        ", money=" + money +
        ", title=" + title +
        ", type=" + type +
        ", expense=" + expense +
        ", createDate=" + createDate +
        ", delFlag=" + delFlag +
        "}";
    }
}
