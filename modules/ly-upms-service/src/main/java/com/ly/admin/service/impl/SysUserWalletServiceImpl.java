package com.ly.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.mapper.SysUserMapper;
import com.ly.admin.mapper.SysUserWalletMapper;
import com.ly.admin.model.entity.SysUserWallet;
import com.ly.admin.service.SysUserWalletService;
import com.ly.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-23
 */
@Service
public class SysUserWalletServiceImpl extends ServiceImpl<SysUserWalletMapper, SysUserWallet> implements SysUserWalletService {


    @Autowired
    private SysUserWalletMapper userWalletMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUserWallet selectByOpenId(String openId) {
        // TODO Auto-generated method stub
        SysUserWallet wallet =userWalletMapper.selectByOpenId(openId);
        if(wallet==null){
            UserVO userVO = userMapper.selectUserVoByOpenId(openId);
            if(userVO!=null){
                wallet = new SysUserWallet();
                wallet.setUserOpenId(openId);
                wallet.setCreateTime(new Date());
                wallet.setUserAmount(new BigDecimal(0));
                userWalletMapper.insert(wallet);
            }
        }

        return wallet;
    }

    @Override
    public int updateUserWallet(SysUserWallet record) {
        // TODO Auto-generated method stub
        return userWalletMapper.updateUserWallet(record);
    }


}
