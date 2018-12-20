package com.ly.bt.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ly.admin.service.SysUserService;
import com.ly.admin.utils.ImageMateUtils;
import com.ly.bt.controller.customer.BtCustomerFileController;
import com.ly.bt.model.bean.PortalBean;
import com.ly.bt.model.entity.BtPointGit;
import com.ly.bt.model.entity.BtPointInfo;
import com.ly.bt.service.BtPointGitService;
import com.ly.bt.service.BtPointInfoService;
import com.ly.common.bean.config.AliyunOssPropertiesConfig;
import com.ly.common.bean.config.GitPortalPullPropertiesConfig;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Query;
import com.ly.common.util.R;
import com.ly.common.util.exception.ParamsErrorException;
import com.ly.common.vo.UserVO;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 客户文件管理
 *
 * @auther Administrator liyang
 * @create 2018/11/17 1:28
 */
@Api(value = "管理员管理文件controller", tags = {"管理员管理文件-->管理员接口"})
@RestController
@RequestMapping("/bt/admin/generate")
public class BtAdminGeneratePointController extends BtCustomerFileController {


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

    @Autowired
    SysUserService userService;


    /**
     * portal list git pull
     * @return
     */
    @GetMapping(value = "/portal/git/pull")
    public R<String> portalGtiPull() {

        if (!redisTemplate.hasKey(CommonConstant.PORTAL_GIT_PULL)) {
            redisTemplate.opsForValue().set(CommonConstant.PORTAL_GIT_PULL, "1", CommonConstant.EXPIRE_PORTAL_GIT_PULL, TimeUnit.MINUTES);
            //run git pull
            new Thread(new GitPortalPull()).start();
            return new R(R.SUCCESS, "坐标仓库开始更新...");

        }else {
            return new R(R.SUCCESS, "坐标仓库正在更新中…");

        }

    }


    /**
     * git portal join system
     *
     * @param id
     * @param appId
     * @return
     */
    @PostMapping("/portal/join")
    public R<String> portalJoin(@RequestParam("id") Integer id, @RequestHeader("appId") Integer appId) {
        BtPointGit pointGit = gitService.selectById(id);
        if (pointGit == null) {
            throw new ParamsErrorException();
        }
        if (BtPointGit.STATUS_JOIN.equals(pointGit.getStatus())) {
            throw new ParamsErrorException("has joined!");
        }


        UserVO userV = userService.findUserByUsername(pointGit.getAccount());
        if (userV == null) {
            userV = userService.createUserByAccount(pointGit.getAccount(), appId);
        }


        BtPointInfo info = new BtPointInfo();
        info.setUserId(userV.getUserId());
        info.setLongitude(pointGit.getLongitude());
        info.setLatitude(pointGit.getLatitude());

        info.setStatus(BtPointInfo.STATUS_SUCCESS);
        info.setDes(pointGit.getDes());
        info.setTitle(pointGit.getTitle());
        info.setImages(pointGit.getImages());
        info.setDappId(pointGit.getDappId());

        infoService.createPointService(info, pointGit.getLatitude(), pointGit.getLongitude());

        pointGit.setStatus(BtPointGit.STATUS_JOIN);
        gitService.updateStatusService(pointGit);


        return new R("join success");
    }


    /**
     * sync git portal
     *
     * @return
     */
    @GetMapping(value = "/portal/sync")
    public R<String> portalSync() {

        if (!redisTemplate.hasKey(CommonConstant.PORTAL_SYNC)) {

            File file = new File(portalPullPropertiesConfig.getLocalPath() + portalPullPropertiesConfig.getObjectPath());
//            SyncGitThread syncGitThread = new SyncGitThread(file);

            redisTemplate.opsForValue().set(CommonConstant.PORTAL_SYNC, "1", CommonConstant.EXPIRE_PORTAL_SYNC, TimeUnit.MINUTES);

            //run sync
            new Thread(new SyncGitThread(file)).start();

        }

        return new R(R.SUCCESS, "坐标同步中.");
    }


    /**
     * @return
     */
    @GetMapping(value = "/portal/list")
    public R gitPortalList(@RequestParam Map params) {
        Query query = new Query(params);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.orderBy("id", false);

        Page<BtPointGit> page = gitService.selectPage(query, entityWrapper);

        return new R(page);
    }

    /**
     * git pull portal
     */
    class GitPortalPull implements Runnable{
        @Override
        public void run() {
            FileRepository repository = null;
            try {
                repository = new FileRepository(new File(portalPullPropertiesConfig.getLocalPath()+"/.git"));
                Git git = new Git(repository);
                git.pull().setRemote("origin").call();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RefNotAdvertisedException e) {
                e.printStackTrace();
            } catch (NoHeadException e) {
                e.printStackTrace();
            } catch (TransportException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } catch (InvalidRemoteException e) {
                e.printStackTrace();
            } catch (CanceledException e) {
                e.printStackTrace();
            } catch (WrongRepositoryStateException e) {
                e.printStackTrace();
            } catch (RefNotFoundException e) {
                e.printStackTrace();
            } catch (GitAPIException e) {
                e.printStackTrace();
            }

            //remove
            redisTemplate.delete(CommonConstant.PORTAL_GIT_PULL);
        }
    }


    class SyncGitThread implements Runnable {

        File file;

        SyncGitThread(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            try {
                readPortal(file.listFiles());

                //remove
                redisTemplate.delete(CommonConstant.PORTAL_SYNC);
            } catch (IOException e) {
                e.printStackTrace();
            }
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


                EntityWrapper entityWrapper = new EntityWrapper();
                entityWrapper.eq("path", getImgPath(img));

                List list = gitService.selectList(entityWrapper);
                //not insert
                if (list.size() == 0) {

                    PortalBean portalBean = readMeta(meta);

                    BtPointGit btPointGit = gitService.findBtInfoByPortalService(portalBean);
                    //if bt point git is null ,upload img
                    if (btPointGit == null) {

                        R uploadResult = upload(new FileInputStream(img));
                        if (R.SUCCESS == uploadResult.getCode()) {

                            btPointGit = new BtPointGit();
                            btPointGit.setCreateTime(new Date());
                            btPointGit.setDappId(portalBean.getId());
                            btPointGit.setAccount(portalBean.getCreator());
                            btPointGit.setImages(String.format("[{\"path\":\"%s\"}]", uploadResult.getData()));

                            //get point name
                            String parentName = img.getParentFile().getName();
                            String pointTitle = parentName.substring(parentName.indexOf(".") + 1, parentName.length() - 1);

                            btPointGit.setTitle(pointTitle);
                            btPointGit.setDes(pointTitle);

                            btPointGit.setK(portalBean.getK());
                            btPointGit.setSt(portalBean.getSt());
                            btPointGit.setPrice(portalBean.getPrice());
                            btPointGit.setCreatorFee(portalBean.getCreator_fee());
                            btPointGit.setRefFee(portalBean.getRef_fee());


                            btPointGit.setLatitude("");
                            btPointGit.setLongitude("");
                            btPointGit.setStatus(BtPointGit.STATUS_NONE);

                            btPointGit.setPath(getImgPath(img));


                            if (StringUtils.isNotEmpty(portalBean.getLatitude())
                                    && StringUtils.isNotEmpty(portalBean.getLongitude())) {

                                btPointGit.setLatitude(portalBean.getLatitude());
                                btPointGit.setLongitude(portalBean.getLongitude());
                            } else {
                                ImageMateUtils.MateImage mateImage = readImg(img);
                                if (mateImage != null) {
                                    btPointGit.setLatitude(StringUtils.isEmpty(mateImage.getLatitude()) ? "0" : mateImage.getLatitude());
                                    btPointGit.setLongitude(StringUtils.isEmpty(mateImage.getLongitude()) ? "0" : mateImage.getLongitude());
                                }
                            }

                            boolean r = gitService.insert(btPointGit);

                            logger.info(btPointGit.toString());


                        }
                    }

                }

            }

        }

        private String getImgPath(File img) {
            return img.getParentFile().getAbsolutePath().replace(portalPullPropertiesConfig.getLocalPath(), "");
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
