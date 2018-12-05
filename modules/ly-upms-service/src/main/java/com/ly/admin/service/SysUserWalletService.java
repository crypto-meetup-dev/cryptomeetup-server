package com.ly.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ly.admin.model.entity.SysUserWallet;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-23
 */
public interface SysUserWalletService extends IService<SysUserWallet> {
    SysUserWallet selectByOpenId(String openId);

    int updateUserWallet(SysUserWallet record);
}
