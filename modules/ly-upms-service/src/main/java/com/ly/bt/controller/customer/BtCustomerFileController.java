package com.ly.bt.controller.customer;

import com.aliyun.oss.OSSClient;
import com.ly.common.bean.config.AliyunOssPropertiesConfig;
import com.ly.common.util.R;
import com.ly.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 客户文件管理
 *
 * @auther Administrator liyang
 * @create 2018/11/17 1:28
 */
@Api(value = "用户文件controller", tags = {"用户文件-->用户接口"})
@RestController
@RequestMapping("/bt/customer/file")
public class BtCustomerFileController extends BaseController {


    @Autowired
    private AliyunOssPropertiesConfig ossPropertiesConfig;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "上传图片", notes = "客户端上传图片到阿里云OSS")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public R<String> uploadPost(@ApiParam(value = "上传的文件", required = true) @RequestParam(value = "file", required = false) MultipartFile file) {
        return upload(file);
    }


    @PutMapping(value = "/upload")
    public R<String> uploadPut(@RequestParam(value = "file", required = false) MultipartFile file) {
        return upload(file);
    }

    private R<String> upload(MultipartFile file) {
        String url = ossPropertiesConfig.createImagePath();
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ossPropertiesConfig.getEndpoint(), ossPropertiesConfig.getSecretId(), ossPropertiesConfig.getSecretKey());
        // 上传网络流。
        try {
            ossClient.putObject(ossPropertiesConfig.getBucketName(), url, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return new R<>(url);
    }
}
