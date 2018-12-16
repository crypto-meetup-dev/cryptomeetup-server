package com.ly.bt.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ly.bt.model.dto.BtPointInfoDTO;
import com.ly.bt.model.entity.BtPointGit;
import com.ly.bt.model.entity.BtPointInfo;

/**
 * <p>
 * 打开点详细信息 服务类
 * </p>
 *
 * @author liyang
 * @since 2018-11-16
 */
public interface BtPointGitService extends IService<BtPointGit> {

    BtPointGit selectDetailByIdService(Integer id);

    BtPointGit createPointService(BtPointGit pointInfo, String latitude, String longitude);

    Page<BtPointGit> selectDistanceService(String latitude, String longitude, Integer distance, Integer page, Integer limit);
}
