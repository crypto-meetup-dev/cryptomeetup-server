package com.ly.bt.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.mapper.SysUserMapper;
import com.ly.bt.mapper.BtPointGisMapper;
import com.ly.bt.mapper.BtPointInfoMapper;
import com.ly.bt.model.dto.BtPointInfoDTO;
import com.ly.bt.model.entity.BtPointGis;
import com.ly.bt.model.entity.BtPointInfo;
import com.ly.bt.service.BtPointInfoService;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Query;
import com.ly.common.util.exception.ParamsErrorException;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 打开点详细信息 服务实现类
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
@Service
public class BtPointInfoServiceImpl extends ServiceImpl<BtPointInfoMapper, BtPointInfo> implements BtPointInfoService {

    @Autowired
    BtPointGisMapper gisMapper;

    @Autowired
    SysUserMapper userMapper;

    /**
     * 查询打卡点详细信息
     *
     * @param id
     * @return
     */
    @Override
    public BtPointInfo selectDetailByIdService(Integer id) {
        BtPointInfo btPointInfo = baseMapper.selectDetailById(id);
//        if(btPointInfo!=null){
//            btPointInfo.setGis(gisMapper.selectById(btPointInfo.getGisId()));
//        }
        return btPointInfo;
    }

    /**
     * 创建打卡点
     *
     * @param pointInfo
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public BtPointInfo createPointService(BtPointInfo pointInfo, String latitude, String longitude) {

        //创建坐标
        BtPointGis pointGis = new BtPointGis();
        pointGis.setCreateTime(new Date());
        pointGis.setLatitude(latitude);
        pointGis.setLongitude(longitude);
        int k = gisMapper.insert(pointGis);
//        pointGis.setId();

        pointInfo.setGisId(pointGis.getId());
        pointInfo.setCreateTime(new Date());

        pointInfo.setLatitude(latitude);
        pointInfo.setLongitude(longitude);

        baseMapper.insert(pointInfo);
        pointInfo.setGis(pointGis);

        return pointInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateInfoService(BtPointInfoDTO pointInfo) {


        BtPointInfo btPointInfo = selectById(pointInfo.getId());


        boolean f = baseMapper.updateById(pointInfo) > 0;


        if(!StringUtils.equals(btPointInfo.getLatitude(),pointInfo.getLatitude())
                ||!StringUtils.equals(btPointInfo.getLongitude(),pointInfo.getLongitude())){
            btPointInfo.setUpdateTime(new Date());
            btPointInfo.setLatitude(pointInfo.getLatitude());
            btPointInfo.setLongitude(pointInfo.getLongitude());
            int num = baseMapper.updateLatLong(pointInfo);
        }

        if (f) {
            BtPointInfo info = baseMapper.selectById(pointInfo.getId());

            BtPointGis btPointGis = gisMapper.selectById(info.getGisId());
            btPointGis.setLatitude(pointInfo.getLatitude());
            btPointGis.setLongitude(pointInfo.getLongitude());
            btPointGis.setUpdateTime(new Date());
            int num = gisMapper.updateLatLong(btPointGis);
            if (num > 0) {
                return true;
            }
            throw new ParamsErrorException();
        }

        return f;
    }

    /**
     * query point by lat lng
     * @param latitude
     * @param longitude
     * @param distance
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<BtPointInfo> selectDistanceService(String latitude, String longitude, Integer distance, Integer page, Integer limit) {
        Map<String, String> params = new HashedMap();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("distance", distance+"");
        params.put("page", page+"");
        params.put("limit", limit+"");
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);

        Query query = new Query(params);

        List<BtPointInfo> gisList = this.baseMapper.selectByLocation(query, params);
        query.setRecords(gisList);

        return query;
    }
}
