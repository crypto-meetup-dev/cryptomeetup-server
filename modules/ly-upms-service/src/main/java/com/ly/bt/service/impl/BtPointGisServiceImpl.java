package com.ly.bt.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.bt.mapper.BtPointGisMapper;
import com.ly.bt.mapper.BtPointInfoMapper;
import com.ly.bt.model.dto.BtPointInfoDTO;
import com.ly.bt.model.entity.BtPointGis;
import com.ly.bt.model.entity.BtPointInfo;
import com.ly.bt.service.BtPointGisService;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Query;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地图坐标信息 服务实现类
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
@Service
public class BtPointGisServiceImpl extends ServiceImpl<BtPointGisMapper, BtPointGis> implements BtPointGisService {


    @Autowired
    BtPointInfoMapper infoMapper;


    /**
     * 查询附近的坐标点
     *
     * @param latitude
     * @param latitude
     * @param distance
     * @return
     */
    @Override
    public Page<BtPointGis> selectDistanceService(String latitude, String longitude, Integer distance, Integer page, Integer limit){
        Map<String, String> params = new HashedMap();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("distance", distance+"");
        params.put("page", page+"");
        params.put("limit", limit+"");
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);

        Query query = new Query(params);

        List<BtPointGis> gisList = this.baseMapper.selectByLocation(query, params);

//        EntityWrapper entityWrapper = new EntityWrapper();
        for (BtPointGis pointGis : gisList) {
//            entityWrapper.eq(BtPointInfo.C_GIS_ID, pointGis.getId());
//            pointGis.setInfos(infoMapper.selectList(entityWrapper));
            BtPointInfo info = new BtPointInfo();
            info.setGisId(pointGis.getId());
            pointGis.setInfos(infoMapper.selectUserList(info));
        }
        query.setRecords(gisList);

        return query;
    }

    /**
     * 查询坐标详细信息
     *
     * @param id
     * @return
     */
    @Override
    public BtPointGis selectDetailByIdService(Integer id) {

        BtPointGis btPointGis = selectById(id);
        if (btPointGis != null) {
//            EntityWrapper entityWrapper = new EntityWrapper(new BtPointInfo());
//            entityWrapper.eq(BtPointInfo.C_GIS_ID, btPointGis.getId());
            BtPointInfo info = new BtPointInfo();
            info.setGisId(btPointGis.getId());
            btPointGis.setInfos(infoMapper.selectUserList(info));
        }
        return btPointGis;
    }
}
