package com.ly.bt.mapper;

import com.ly.bt.model.entity.BtPointGis;
import com.ly.bt.model.entity.BtPointInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ly.common.util.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 打开点详细信息 Mapper 接口
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
public interface BtPointInfoMapper extends BaseMapper<BtPointInfo> {
    List<BtPointInfo> selectUserList(BtPointInfo info);

    BtPointInfo selectDetailById(Integer id);

    int updateLatLong(BtPointInfo info);

    List<BtPointInfo> selectByLocation(Query objectQuery, Map params);

}
