package com.ly.bt.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ly.bt.model.entity.BtPointGit;
import com.ly.bt.model.entity.BtPointInfo;
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
public interface BtPointGitMapper extends BaseMapper<BtPointGit> {

    void updateStatus(BtPointGit pointGit);
}
