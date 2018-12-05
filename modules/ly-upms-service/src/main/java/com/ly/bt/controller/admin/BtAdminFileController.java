package com.ly.bt.controller.admin;

import com.ly.bt.controller.customer.BtCustomerFileController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
