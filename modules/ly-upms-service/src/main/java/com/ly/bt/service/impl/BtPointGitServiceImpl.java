package com.ly.bt.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.bt.mapper.BtPointGitMapper;
import com.ly.bt.model.bean.PortalBean;
import com.ly.bt.model.entity.BtPointGit;
import com.ly.bt.service.BtPointGitService;
import org.springframework.stereotype.Service;

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


    /**
     * update git portal status
     * @param pointGit
     */
    @Override
    public void updateStatusService(BtPointGit pointGit) {
        pointGit.setUpdateTime(new Date());
        baseMapper.updateStatus(pointGit);
    }


}
