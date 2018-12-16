package com.ly.bt.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.mapper.SysUserMapper;
import com.ly.bt.mapper.BtPointGisMapper;
import com.ly.bt.mapper.BtPointGitMapper;
import com.ly.bt.model.entity.BtPointGis;
import com.ly.bt.model.entity.BtPointGit;
import com.ly.bt.service.BtPointGitService;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Query;
import org.apache.commons.collections.map.HashedMap;
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
public class BtPointGitServiceImpl extends ServiceImpl<BtPointGitMapper, BtPointGit> implements BtPointGitService {

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
    public BtPointGit selectDetailByIdService(Integer id) {
        BtPointGit BtPointGit = baseMapper.selectDetailById(id);
//        if(BtPointGit!=null){
//            BtPointGit.setGis(gisMapper.selectById(BtPointGit.getGisId()));
//        }
        return BtPointGit;
    }

    /**
     * 创建打卡点
     *
     * @param pointGit
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public BtPointGit createPointService(BtPointGit pointGit, String latitude, String longitude) {

        pointGit.setCreateTime(new Date());

        pointGit.setLatitude(latitude);
        pointGit.setLongitude(longitude);
        baseMapper.insert(pointGit);

        return pointGit;
    }


    /**
     * query point by lat lng
     *
     * @param latitude
     * @param longitude
     * @param distance
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<BtPointGit> selectDistanceService(String latitude, String longitude, Integer distance, Integer page, Integer limit) {
        Map<String, String> params = new HashedMap();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("distance", distance + "");
        params.put("page", page + "");
        params.put("limit", limit + "");
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);

        Query query = new Query(params);

        List<BtPointGit> gisList = this.baseMapper.selectByLocation(query, params);
        query.setRecords(gisList);

        return query;
    }
}
