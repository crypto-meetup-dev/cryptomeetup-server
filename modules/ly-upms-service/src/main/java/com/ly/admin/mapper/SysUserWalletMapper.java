package com.ly.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ly.admin.model.entity.SysUserWallet;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-23
 */
public interface SysUserWalletMapper extends BaseMapper<SysUserWallet> {

    SysUserWallet selectByOpenId(String openId);

    int updateUserWallet(SysUserWallet record);

}
