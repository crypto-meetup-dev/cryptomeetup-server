package com.ly.bt.controller.admin;

import com.alibaba.fastjson.JSON;
import com.ly.admin.utils.ImageMateUtils;
import com.ly.bt.controller.customer.BtCustomerFileController;
import com.ly.bt.model.bean.PortalBean;
import com.ly.bt.model.entity.BtPointGit;
import com.ly.bt.service.BtPointGitService;
import com.ly.bt.service.BtPointInfoService;
import com.ly.common.bean.config.AliyunOssPropertiesConfig;
import com.ly.common.bean.config.GitPortalPullPropertiesConfig;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.R;
import com.ly.common.util.UserUtils;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    AliyunOssPropertiesConfig ossPropertiesConfig;


    @Autowired
    BtPointGitService gitService;

    @Autowired
    BtPointInfoService infoService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * sync git portal
     *
     * @return
     */
    @GetMapping(value = "/git/portal/sync")
    public R<String> gitPortalSync() {


        File file = new File(portalPullPropertiesConfig.getObjectPath() + portalPullPropertiesConfig.getObjectPath());

        File[] file1 = file.listFiles();


        return new R<String>();
    }


    /**
     * @return
     */
    @GetMapping(value = "/git/portal/list")
    public R<String> gitPortalList() {

        if(!redisTemplate.hasKey(CommonConstant.PORTAL_SYNC)){

            SyncGitThread syncGitThread =new SyncGitThread();

            //add flag
            redisTemplate.expire(CommonConstant.PORTAL_SYNC, 30, TimeUnit.MINUTES);

            new Thread(syncGitThread).start();

        }
        return new R<String>();
    }


    class SyncGitThread implements Runnable {

        @Override
        public void run() {

        }

        /**
         * read portal list
         *
         * @param files
         */
        public void readPortal(File[] files) throws IOException {

            File meta = null, img = null;

            for (File file : files) {
                if (file.isDirectory()) {
                    readPortal(file.listFiles());
                } else {
                    if (file.getName().toLowerCase().endsWith("json")) {
                        meta = file;
                    } else if (file.getName().toLowerCase().endsWith("jpeg")
                            || file.getName().toLowerCase().endsWith("jpg")) {
                        img = file;
                    } else if (img == null && file.getName().toLowerCase().endsWith("heic")) {
                        img = file;
                    }
                }
            }

            if (meta != null && img != null) {
                PortalBean portalBean = readMeta(meta);

                BtPointGit btPointGit = gitService.findBtInfoByPortalService(portalBean);
                //if bt point git is null ,upload img
                if (btPointGit == null) {

                    R uploadResult = upload(new FileInputStream(meta));
                    if (R.SUCCESS == uploadResult.getCode()) {

                        btPointGit = new BtPointGit();
                        btPointGit.setDappId(portalBean.getId());
                        btPointGit.setAccount(portalBean.getCreator());
                        btPointGit.setImages(String.format("[\"path\":\"%s\"]", uploadResult.getData()));

                        //get point name
                        String parentName = img.getParentFile().getName();
                        String pointTitle = parentName.substring(parentName.indexOf(".")+1, parentName.length()-1);

                        btPointGit.setTitle(pointTitle);
                        btPointGit.setDes(pointTitle);


                        btPointGit.setLatitude("");
                        btPointGit.setLongitude("");
                        btPointGit.setStatus(BtPointGit.STATUS_NONE);
                        btPointGit.setPath(img.getParentFile().getAbsolutePath().replace(portalPullPropertiesConfig.getLocalPath(), ""));

                        if (StringUtils.isNotEmpty(portalBean.getLatitude())
                                && StringUtils.isNotEmpty(portalBean.getLongitude())) {

                            btPointGit.setLatitude(portalBean.getLatitude());
                            btPointGit.setLongitude(portalBean.getLongitude());
                        } else {
                            ImageMateUtils.MateImage mateImage = readImg(img);
                            if (mateImage != null) {
                                btPointGit.setLatitude(mateImage.getLatitude());
                                btPointGit.setLongitude(mateImage.getLongitude());
                            }
                        }




                    }
                }

            }

        }

        private PortalBean readMeta(File file) throws IOException {
            PortalBean portalBean = JSON.parseObject(FileUtils.readFileToString(file, "utf-8"), PortalBean.class);
            return portalBean;
        }

        private ImageMateUtils.MateImage readImg(File imgFile) {
            ImageMateUtils.MateImage mateImage = null;
            try {
                mateImage = ImageMateUtils.getImageMate(imgFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mateImage;
        }
    }


}
