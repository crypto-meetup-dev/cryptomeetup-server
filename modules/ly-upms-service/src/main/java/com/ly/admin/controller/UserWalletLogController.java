package com.ly.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ly.admin.model.entity.SysUserWalletLog;
import com.ly.admin.service.SysUserWalletLogService;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Query;
import com.ly.common.util.R;
import com.ly.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 钱包日志信息 前端控制器
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-24
 */
@RestController
@RequestMapping("/user/wallet/log")
public class UserWalletLogController extends BaseController {
    @Autowired
    private SysUserWalletLogService redWalletLogService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysUserWalletLog
     */
    @GetMapping("/{id}")
    public R<SysUserWalletLog> get(@PathVariable Integer id) {
        return new R<>(redWalletLogService.selectById(id));
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
        return redWalletLogService.selectPage(new Query<>(params), new EntityWrapper<>());
    }

    /**
     * 添加
     *
     * @param redWalletLog 实体
     * @return success/false
     */
    @PostMapping
    public R<Boolean> add(@RequestBody SysUserWalletLog redWalletLog) {
        return new R<>(redWalletLogService.insert(redWalletLog));
    }


}
