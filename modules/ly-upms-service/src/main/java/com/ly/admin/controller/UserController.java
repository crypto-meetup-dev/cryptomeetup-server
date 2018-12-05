package com.ly.admin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.ly.admin.model.dto.UserDTO;
import com.ly.admin.model.dto.UserInfo;
import com.ly.admin.model.entity.SysUser;
import com.ly.admin.model.entity.SysUserRole;
import com.ly.admin.service.SysUserService;
import com.ly.admin.service.SysUserSessionService;
import com.ly.common.bean.config.FdfsPropertiesConfig;
import com.ly.common.constant.CommonConstant;
import com.ly.common.entity.SysUserSession;
import com.ly.common.util.Query;
import com.ly.common.util.R;
import com.ly.common.vo.UserVO;
import com.ly.common.web.BaseController;
import com.xiaoleilu.hutool.io.FileUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author liyang
 * @Create 2018/4/28
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Autowired
    private SysUserService userService;
    @Autowired
    private FdfsPropertiesConfig fdfsPropertiesConfig;
    @Autowired
    private SysUserSessionService userSessionService;

    /**
     * 获取当前用户信息（角色、权限）
     * 并且异步初始化用户部门信息
     *
     * @param userVo 当前用户信息
     * @return 用户名
     */
    @GetMapping("/info")
    public R<UserInfo> user(UserVO userVo) {
        UserInfo userInfo = userService.findUserInfo(userVo);
        return new R<>(userInfo);
    }

    /**
     * 通过ID查询当前用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public UserVO user(@PathVariable Integer id) {
        return userService.selectUserVoById(id);
    }

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @DeleteMapping("/{id}")
    public R<Boolean> userDel(@PathVariable Integer id) {
        SysUser sysUser = userService.selectById(id);
        return new R<>(userService.deleteUserById(sysUser));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping
    public R<Boolean> user(@RequestBody UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        userService.insert(sysUser);

        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return new R<>(Boolean.TRUE);
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping
    public R<Boolean> userUpdate(@RequestBody UserDTO userDto) {
        SysUser user = userService.selectById(userDto.getUserId());
        return new R<>(userService.updateUser(userDto, user.getUsername()));
    }

    /**
     * 通过用户名查询用户及其角色信息
     *
     * @param username 用户名
     * @return UseVo 对象
     */
    @GetMapping("/findUserByUsername/{username}")
    public UserVO findUserByUsername(@PathVariable String username) {
        UserVO  userVO =new UserVO();
        userVO = userService.findUserByUsername(username);
        return userVO;
    }

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号
     * @return UseVo 对象
     */
    @GetMapping("/findUserByMobile/{mobile}")
    public UserVO findUserByMobile(@PathVariable String mobile) {
        return userService.findUserByMobile(mobile);
    }

    /**
     * 通过OpenId查询
     *
     * @param openId openid
     * @return 对象
     */
    @GetMapping("/findUserByOpenId/{openId}")
    public UserVO findUserByOpenId(@PathVariable String openId) {
        return userService.findUserByOpenId(openId);
    }

    /**
     * 分页查询用户
     *
     * @param params 参数集
     * @param userVO 用户信息
     * @return 用户集合
     */
    @RequestMapping("/userPage")
    public Page userPage(@RequestParam Map<String, Object> params, UserVO userVO) {
        return userService.selectWithRolePage(new Query(params), userVO);
    }

    /**
     * 上传用户头像
     * (多机部署有问题，建议使用独立的文件服务器)
     *
     * @param file 资源
     * @return filename map
     */
    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
        String fileExt = FileUtil.extName(file.getOriginalFilename());
        Map<String, String> resultMap = new HashMap<>(1);
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getBytes(), fileExt);
            resultMap.put("filename", fdfsPropertiesConfig.getFileHost() + storePath.getFullPath());
        } catch (IOException e) {
            logger.error("文件上传异常", e);
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @param userVo  登录用户信息
     * @return success/false
     */
    @PutMapping("/editInfo")
    public R<Boolean> editInfo(@RequestBody UserDTO userDto, UserVO userVo) {
        return userService.updateUserInfo(userDto, userVo.getUsername());
    }


    /**
     * 通过userId查询
     *
     * @param userId userId
     * @return 对象
     */
    @GetMapping("/findUserId/{userId}")
    public UserVO findUserByOpenId(@PathVariable Integer userId) {
        return userService.selectUserVoById(userId);
    }


    @GetMapping("/findUserByWxOpenId/{wxOpenId}")
    public UserVO findUserByWxOpenId(@PathVariable String wxOpenId) {
        return userService.selectUserVoByWxOpenId(wxOpenId);
    }

    /**
     * 通过会话信息查询
     *
     * @param sysUserSession sysUserSession
     * @return 对象
     */
    @PostMapping("/findUserSessionByOpenId")
    public SysUserSession findUserSessionByOpenId(@RequestBody SysUserSession sysUserSession) {
        return userSessionService.findChannelAndOpenId(sysUserSession);
    }


    /**
     * 修改个人信息
     *
     * @param userSession 登录用户信息
     * @return success/false
     */
    @PutMapping("/saveUserSession")
    public SysUserSession saveUserSession(@RequestBody SysUserSession userSession) {
        userSession.setUpdateTime(new Date());
        boolean f = userSessionService.insertOrUpdate(userSession);
        if (f) {
            return userSession;
        }
        return null;
    }

    /**
     * 根据用户会话信息创建一个用户
     *
     * @param userSession
     * @return
     */
    @PutMapping("/createUserByUserSession")
    public UserVO createUserByUserSession(@RequestBody SysUserSession userSession) {
        userSession.setCreateTime(new Date());
        UserVO userVO = userService.createUserByUserSession(userSession);
        return userVO;
    }


}
