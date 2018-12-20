package com.ly.bt.service;

import com.baomidou.mybatisplus.service.IService;
import com.ly.bt.model.bean.PortalBean;
import com.ly.bt.model.entity.BtPointGit;

/**
 * <p>
 * 打开点详细信息 服务类
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
public interface BtPointGitService extends IService<BtPointGit> {


    BtPointGit findBtInfoByPortalService(PortalBean portalBean);

    void updateStatusService(BtPointGit pointGit);

}
