package com.ly.bt.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ly.bt.model.dto.BtPointInfoDTO;
import com.ly.bt.model.entity.BtPointGis;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 地图坐标信息 服务类
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
public interface BtPointGisService extends IService<BtPointGis> {

    Page<BtPointGis> selectDistanceService(String latitude, String longitude, Integer distance,Integer page,Integer limit);

    public BtPointGis selectDetailByIdService(Integer id);
}
