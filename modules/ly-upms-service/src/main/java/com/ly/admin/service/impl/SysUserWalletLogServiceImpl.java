package com.ly.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.mapper.SysUserWalletLogMapper;
import com.ly.admin.model.entity.SysUserWalletLog;
import com.ly.admin.service.SysUserWalletLogService;
import com.ly.common.constant.DictConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 钱包日志信息 服务实现类
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-24
 */
@Service
public class SysUserWalletLogServiceImpl extends ServiceImpl<SysUserWalletLogMapper, SysUserWalletLog> implements SysUserWalletLogService {


    @Autowired
    SysUserWalletLogMapper walletLogMapper;


    private SysUserWalletLog create(String type, SysUserWalletLog walletLog){
        walletLog.setType(type);
        return walletLog;
    }

    @Override
    public SysUserWalletLog createUserIn(SysUserWalletLog redWalletLog) {
        create(DictConstant.WALLET_USER_IN,redWalletLog);
        return redWalletLog;
    }

    @Override
    public SysUserWalletLog createUserOut(SysUserWalletLog redWalletLog) {
        create(DictConstant.WALLET_USER_OUT,redWalletLog);
        return redWalletLog;
    }

    @Override
    public SysUserWalletLog createSysIn(SysUserWalletLog redWalletLog) {
        create(DictConstant.WALLET_SYS_IN,redWalletLog);
        return redWalletLog;
    }

    @Override
    public SysUserWalletLog createSysOut(SysUserWalletLog redWalletLog) {
        create(DictConstant.WALLET_SYS_OUT,redWalletLog);
        return redWalletLog;
    }


}
