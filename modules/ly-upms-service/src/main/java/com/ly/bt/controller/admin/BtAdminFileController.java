package com.ly.bt.controller.admin;

import com.ly.bt.controller.customer.BtCustomerFileController;
import com.ly.common.bean.config.GitPortalPullPropertiesConfig;
import com.ly.common.util.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 客户文件管理
 *
 * @auther Administrator liyang
 * @create 2018/11/17 1:28
 */
@Api(value = "管理员管理文件controller", tags = {"管理员管理文件-->管理员接口"})
@RestController
@RequestMapping("/bt/admin/file")
public class BtAdminFileController extends BtCustomerFileController {


    @Autowired
    GitPortalPullPropertiesConfig portalPullPropertiesConfig;

    /**
     * sync git portal
     * @return
     */
    @GetMapping(value = "/git/portal/sync")
    public R<String> gitPortalSync() {

//        portalPullPropertiesConfig.get

        return new R<String>();
    }


    /**
     *
     * @return
     */
    @GetMapping(value = "/git/portal/list")
    public R<String> gitPortalList() {


        return new R<String>();
    }



}
