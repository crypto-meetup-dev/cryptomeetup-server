package com.ly.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ly.admin.model.entity.SysUserWalletLog;

/**
 * <p>
 * 钱包日志信息 服务类
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-24
 */
public interface SysUserWalletLogService extends IService<SysUserWalletLog> {

    //用户收入
    public SysUserWalletLog createUserIn(SysUserWalletLog redWalletLog);

    //用户支出
    public SysUserWalletLog createUserOut(SysUserWalletLog redWalletLog);

    //系统支出
    public SysUserWalletLog createSysOut(SysUserWalletLog redWalletLog);

    //系统收入
    public SysUserWalletLog createSysIn(SysUserWalletLog redWalletLog);


}
