package com.ly.bt.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ly.bt.model.entity.BtPointGis;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ly.common.util.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地图坐标信息 Mapper 接口
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
public interface BtPointGisMapper extends BaseMapper<BtPointGis> {

    List<BtPointGis> selectByLocation(Query objectQuery, Map params);

    int updateLatLong(BtPointGis btPointGis);
}
