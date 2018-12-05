package com.ly.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ly.admin.model.dto.SysUserWalletDTO;
import com.ly.admin.model.entity.SysUserWallet;
import com.ly.admin.service.SysUserWalletService;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Query;
import com.ly.common.util.R;
import com.ly.common.web.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *
 * 前端控制器
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-23
 */
@RestController
@RequestMapping("/user/wallet")
public class UserWalletController extends BaseController {

    @Autowired
    private SysUserWalletService redUserWalletService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysUserWallet
     */
    @GetMapping("/{id}")
    public R<SysUserWallet> get(@PathVariable Integer id) {
        return new R<>(redUserWalletService.selectById(id));
    }


    /**
     * @return
     */
    @GetMapping("/test")
    public R<String> test1() {

        return new R<>("test1 mapping");
    }


    /**
     * 分页查询信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        return redUserWalletService.selectPage(new Query<>(params), new EntityWrapper<>());
    }

    /**
     * 添加
     *
     * @param redUserWallet 实体
     * @return success/false
     */
    @PostMapping
    public R<Boolean> add(@RequestBody SysUserWallet redUserWallet) {
        return new R<>(redUserWalletService.insert(redUserWallet));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Integer id) {
        SysUserWallet redUserWallet = new SysUserWallet();
        redUserWallet.setId(id);
        redUserWallet.setUpdateTime(new Date());
        redUserWallet.setDelFlag(CommonConstant.STATUS_DEL);
        return new R<>(redUserWalletService.updateById(redUserWallet));
    }

    /**
     * 编辑
     *
     * @param redUserWallet 实体
     * @return success/false
     */
    @PutMapping
    public R<Boolean> edit(@RequestBody SysUserWallet redUserWallet) {
        redUserWallet.setUpdateTime(new Date());
        return new R<>(redUserWalletService.updateById(redUserWallet));
    }



    /**
     * 针对业务系统高并发-----修改用户钱包数据余额，采用乐观锁
     *
     * @return
     */
    @RequestMapping(value = "/walleroptimisticlock", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> walleroptimisticlock(@RequestBody SysUserWalletDTO walletDTO) {
//        SysUserWalletDTO walletDTO = new SysUserWalletDTO();
        String result = "";

        try {
//            String openId = request.getParameter("openId") == null ? null
//                    : request.getParameter("openId").trim(); // 用户唯一编号
//            String openType = request.getParameter("openType") == null ? null
//                    : request.getParameter("openType").trim(); // 1:代表增加，2：代表减少
//            String amount = request.getParameter("amount") == null ? null
//                    : request.getParameter("amount").trim(); // 金额

            if (StringUtils.isEmpty(walletDTO.getUserOpenId())) {
                logger.debug("openId is null");
                return new R(Boolean.FALSE);
            }
            if (StringUtils.isEmpty(walletDTO.getOpenType())) {
                logger.debug("openType is null");
                return new R(Boolean.FALSE);
            }
            if (StringUtils.isEmpty(walletDTO.getAmount())) {
                logger.debug("amount is null");
                return new R(Boolean.FALSE);
            }
            SysUserWallet wallet = redUserWalletService.selectByOpenId(walletDTO.getUserOpenId());

            // 用户操作金额
            BigDecimal cash = BigDecimal.valueOf(Double.parseDouble(walletDTO.getAmount()));
            cash.doubleValue();
            cash.floatValue();
            if (Integer.parseInt(walletDTO.getOpenType()) == 1) {
                wallet.setUserAmount(wallet.getUserAmount().add(cash));
            } else if (Integer.parseInt(walletDTO.getOpenType()) == 2) {
                wallet.setUserAmount(wallet.getUserAmount().subtract(cash));
            }
            wallet.setUpdateTime(new Date());

            int target = redUserWalletService.updateUserWallet(wallet);
            System.out.println("修改用户金额是否：" + (target == 1 ? "成功" : "失败"));

        } catch (Exception e) {
            result = e.getMessage();
            return new R(Boolean.FALSE);
        }

        return new R(Boolean.TRUE);
    }


}
