package com.ly.bt.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ly.admin.model.entity.SysUser;

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
@TableName("bt_point_info")
public class BtPointInfo extends Model<BtPointInfo> {

    private static final long serialVersionUID = 1L;

    public static final String C_GIS_ID = "gis_id";


    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";


    public static final String STATUS_IN = "1";
    public static final String STATUS_SUCCESS = "2";
    public static final String STATUS_NONE = "3";


    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 拥有者ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 点地图信息
     */
    @TableField(C_GIS_ID)
    private Integer gisId;


    /**
     *
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


    @TableField(exist = false)
    private String distance;


    @TableField(exist = false)
    private BtPointGis gis;

    @TableField(exist = false)
    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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


    public void setGis(BtPointGis gis) {
        this.gis = gis;
    }

    public BtPointGis getGis() {
        return gis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGisId() {
        return gisId;
    }

    public void setGisId(Integer gisId) {
        this.gisId = gisId;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BtPointInfo{" +
                ", id=" + id +
                ", userId=" + userId +
                ", dappId=" + dappId +
                ", gisId=" + gisId +
                ", images=" + images +
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
