package com.ly.bt.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 打开点详细信息
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
@TableName("bt_point_git")
public class BtPointGit extends Model<BtPointGit> {

    private static final long serialVersionUID = 1L;

    public static final String C_GIS_ID = "gis_id";


    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static final String STATUS_NONE = "1";// data no join server
    public static final String STATUS_JOIN = "2";// data join server

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * customer account
     */
    private String account;

    /**
     * image path
     */
    private String path;


    /**
     * customer dapp id
     */
    @TableField("dapp_id")
    private String dappId;


    /**
     * 图片信息
     */
    private String images;
    /**
     * 坐标名称
     */
    private String title;
    /**
     * 坐标描述
     */
    private String des;
    /**
     * 状态　1 审核中　２已经拥有　０删除
     */
    private String status;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 0 正常 1删除
     */
    @TableField("del_flag")
    private String delFlag;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    /**
     * 坐标
     */
    private String latitude;
    /**
     * 坐标
     */
    private String longitude;


    private String st;

    private String k;

    private String refFee;

    private String creatorFee;

    private String price;

    private String parent;


    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getRefFee() {
        return refFee;
    }

    public void setRefFee(String refFee) {
        this.refFee = refFee;
    }

    public String getCreatorFee() {
        return creatorFee;
    }

    public void setCreatorFee(String creatorFee) {
        this.creatorFee = creatorFee;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDappId() {
        return dappId;
    }

    public void setDappId(String dappId) {
        this.dappId = dappId;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BtPointGit{" +
                ", id=" + id +
                ", dappId=" + dappId +
                ", account=" + account +
                ", path=" + path +
                ", st=" + st +
                ", k=" + k +
                ", price=" + price +
                ", refFee=" + refFee +
                ", creatorFee=" + creatorFee +
                ", price=" + price +
                ", parent=" + parent +
                ", title=" + title +
                ", des=" + des +
                ", status=" + status +
                ", remarks=" + remarks +
                ", delFlag=" + delFlag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
