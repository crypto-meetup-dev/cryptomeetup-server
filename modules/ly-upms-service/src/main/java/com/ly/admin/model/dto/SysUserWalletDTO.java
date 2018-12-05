package com.ly.admin.model.dto;

import com.ly.admin.model.entity.SysUserWallet;
import lombok.Data;

/**
 * @auther Administrator liyang
 * @Create 2018/8/23
 */
@Data
public class SysUserWalletDTO extends SysUserWallet {

    String amount;
    String openType;


}
