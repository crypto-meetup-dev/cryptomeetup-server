package com.ly.bt.controller.admin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ly.admin.model.entity.SysUser;
import com.ly.admin.service.SysUserService;
import com.ly.bt.controller.customer.BtCustomerFileController;
import com.ly.bt.model.entity.BtPointInfo;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Query;
import com.ly.common.util.R;
import com.ly.common.vo.UserVO;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户
 *
 * @auther Administrator liyang
 * @create 2018/12/1 11:58
 */
@Api(value = "管理员管理用户controller", tags = {"管理员管理用户-->管理员接口"})
@RestController
@RequestMapping("/bt/admin/user")
public class BtAdminUserController extends BtCustomerFileController {


    @Autowired
    private SysUserService userService;


    /**
     * 获取用户列表
     *
     * @param params
     * @param appId
     * @return
     */
    @GetMapping("/page")
    public R<Page<BtPointInfo>> page(@RequestParam Map params, @RequestHeader("appId") Integer appId, @RequestParam(value = "search", required = false) String search) {

        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        params.put("appId", appId);
        EntityWrapper entityWrapper = new EntityWrapper<>();
        if (StringUtils.isNotEmpty(search)) {
            entityWrapper.like("username", search);
            entityWrapper.like("nick_name", search);
            params.remove("search");
        }

        Query query = new Query(params);
        Page<SysUser> page = userService.selectPage(query, entityWrapper);

        if (page.getRecords() != null && page.getRecords().size() > 0) {
            for (SysUser user : page.getRecords()) {
                user.setPassword(null);
                user.setAppId(null);
                user.setCreateTime(null);
                user.setUpdateTime(null);
            }
        }

        return new R(page);
    }

    /**
     * 创建
     *
     * @param username
     * @return
     */
    @PostMapping("/create")
    public R create(@RequestParam("username") String username, @RequestHeader("appId") Integer appId) {

        UserVO userVO = userService.createUserByAccount(username, appId);

        return new R(userVO);
    }

}
