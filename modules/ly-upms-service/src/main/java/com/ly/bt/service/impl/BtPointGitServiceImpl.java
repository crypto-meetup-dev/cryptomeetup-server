package com.ly.bt.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.mapper.SysUserMapper;
import com.ly.bt.mapper.BtPointGisMapper;
import com.ly.bt.mapper.BtPointGitMapper;
import com.ly.bt.model.bean.PortalBean;
import com.ly.bt.model.entity.BtPointGit;
import com.ly.bt.service.BtPointGitService;
import com.ly.common.bean.config.AliyunOssPropertiesConfig;
import com.ly.common.bean.config.GitPortalPullPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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
    GitPortalPullPropertiesConfig portalPullPropertiesConfig;

    @Autowired
    AliyunOssPropertiesConfig ossPropertiesConfig;

    @Autowired
    BtPointGisMapper gisMapper;

    @Autowired
    SysUserMapper userMapper;


    @Autowired
    RestTemplate restTemplate;

    /**
     * 查询打卡点详细信息
     *
     * @param id
     * @return
     */
    @Override
    public BtPointGit selectDetailByIdService(Integer id) {
        BtPointGit BtPointGit = baseMapper.selectDetailById(id);
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
    public BtPointGit createService(BtPointGit pointGit, String latitude, String longitude) {

        pointGit.setCreateTime(new Date());

        pointGit.setLatitude(latitude);
        pointGit.setLongitude(longitude);
        baseMapper.insert(pointGit);

        return pointGit;
    }


    /**
     * find portal info
     *
     * @param portalBean
     * @return
     */
    @Override
    public BtPointGit findBtInfoByPortalService(PortalBean portalBean) {
        return null;
    }


}
