package com.ly.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.mapper.SysLogMapper;
import com.ly.admin.service.SysLogService;
import com.ly.common.constant.CommonConstant;
import com.ly.common.entity.SysLog;
import com.ly.common.util.Assert;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @Author liyang
 * @Create 2018-5-20
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public Boolean updateByLogId(Long id) {
        Assert.isNull(id, "日志ID为空");

        SysLog sysLog = new SysLog();
        sysLog.setId(id);
        sysLog.setDelFlag(CommonConstant.STATUS_DEL);
        sysLog.setUpdateTime(new Date());
        return updateById(sysLog);
    }
}
