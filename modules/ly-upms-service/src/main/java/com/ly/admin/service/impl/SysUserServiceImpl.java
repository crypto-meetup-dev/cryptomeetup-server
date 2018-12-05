package com.ly.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.common.config.DefaultPropertiesConfig;
import com.ly.admin.mapper.SysUserMapper;
import com.ly.admin.mapper.SysUserRoleMapper;
import com.ly.admin.mapper.SysUserSessionMapper;
import com.ly.admin.mapper.SysUserWalletMapper;
import com.ly.admin.model.dto.UserDTO;
import com.ly.admin.model.dto.UserInfo;
import com.ly.admin.model.entity.SysDeptRelation;
import com.ly.admin.model.entity.SysUser;
import com.ly.admin.model.entity.SysUserRole;
import com.ly.admin.model.entity.SysUserWallet;
import com.ly.admin.service.SysDeptRelationService;
import com.ly.admin.service.SysMenuService;
import com.ly.admin.service.SysUserService;
import com.ly.common.bean.interceptor.DataScope;
import com.ly.common.constant.CommonConstant;
import com.ly.common.constant.MqQueueConstant;
import com.ly.common.constant.SecurityConstants;
import com.ly.common.constant.enums.EnumSmsChannelTemplate;
import com.ly.common.entity.SysUserSession;
import com.ly.common.util.Query;
import com.ly.common.util.R;
import com.ly.common.util.exception.ParamsErrorException;
import com.ly.common.util.template.MobileMsgTemplate;
import com.ly.common.vo.MenuVO;
import com.ly.common.vo.SysRole;
import com.ly.common.vo.UserVO;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author liyang
 * @Create 2018/4/31
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserSessionMapper userSessionMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SysUserRoleMapper sysUserRoleService;
    @Autowired
    private SysDeptRelationService sysDeptRelationService;

    @Autowired
    private DefaultPropertiesConfig defaultPropertiesConfig;

    @Autowired
    SysUserWalletMapper userWalletMapper;

    @Override
    public UserInfo findUserInfo(UserVO userVo) {
        SysUser condition = new SysUser();
        condition.setUsername(userVo.getUsername());
        SysUser sysUser = this.selectOne(new EntityWrapper<>(condition));

        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表
        List<SysRole> roleList = userVo.getRoleList();
        List<String> roleNames = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roleList)) {
            for (SysRole sysRole : roleList) {
                if (!StrUtil.equals(SecurityConstants.BASE_ROLE, sysRole.getRoleName())) {
                    roleNames.add(sysRole.getRoleName());
                }
            }
        }
        String[] roles = roleNames.toArray(new String[roleNames.size()]);
        userInfo.setRoles(roles);

        //设置权限列表（menu.permission）
        Set<MenuVO> menuVoSet = new HashSet<>();
        for (String role : roles) {
            List<MenuVO> menuVos = sysMenuService.findMenuByRoleName(role);
            menuVoSet.addAll(menuVos);
        }
        Set<String> permissions = new HashSet<>();
        for (MenuVO menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        userInfo.setPermissions(permissions.toArray(new String[permissions.size()]));
        return userInfo;
    }

    @Override
    @Cacheable(value = "user_details", key = "#username")
    public UserVO findUserByUsername(String username) {
        return sysUserMapper.selectUserVoByUsername(username);
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    @Cacheable(value = "user_details_mobile", key = "#mobile")
    public UserVO findUserByMobile(String mobile) {
        return sysUserMapper.selectUserVoByMobile(mobile);
    }

    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    @Override
    @Cacheable(value = "user_details_openid", key = "#openId")
    public UserVO findUserByOpenId(String openId) {
        return sysUserMapper.selectUserVoByOpenId(openId);
    }

    @Override
    public Page selectWithRolePage(Query query, UserVO userVO) {
        DataScope dataScope = new DataScope();
        dataScope.setScopeName("deptId");
        dataScope.setIsOnly(true);
        dataScope.setDeptIds(getChildDepts(userVO));
        Object username = query.getCondition().get("username");
        query.setRecords(sysUserMapper.selectUserVoPageDataScope(query, username, dataScope));
        return query;
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO selectUserVoById(Integer id) {
        return sysUserMapper.selectUserVoById(id);
    }

    /**
     * 根据用户的会话信息创建一个用户
     *
     * @param userSession
     */
    @Override
    public UserVO createUserByUserSession(SysUserSession userSession) {

        UserVO userVO = selectUserVoByWxOpenId(userSession.getOpenId());
        if (userVO == null) {
            SysUser sysUser = new SysUser();
            sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
            sysUser.setAppId(userSession.getAppId());
            sysUser.setPassword(ENCODER.encode(userSession.getOpenId()));
            sysUser.setDeptId(defaultPropertiesConfig.getDeptId());
            sysUser.setAvatarUrl(userSession.getAvatarUrl());
            sysUser.setNickName(userSession.getNickName());
            sysUser.setWxOpenId(userSession.getOpenId());
            sysUser.setUsername(userSession.getNickName());

            this.insert(sysUser);

            for (Integer roleId : defaultPropertiesConfig.getUserNormalRoles()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getUserId());
                userRole.setRoleId(roleId);
                userRole.insert();
            }

            userVO = selectUserVoById(sysUser.getUserId());
        }

        userSession.setUserId(userVO.getUserId());
        userSessionMapper.updateById(userSession);

        return userVO;
    }


    @Override
    public UserVO createUserByAccount(String account, Integer appId) {

        UserVO user = findUserByUsername(account);
        if (user != null) {
            throw new ParamsErrorException("该账号已经存在.");
        }

        SysUser sysUser = new SysUser();
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setAppId(appId);
        sysUser.setPassword(ENCODER.encode(account));
        sysUser.setDeptId(defaultPropertiesConfig.getDeptId());
        sysUser.setNickName(account);
        sysUser.setWxOpenId(account);
        sysUser.setUsername(account);

        this.insert(sysUser);

        for (Integer roleId : defaultPropertiesConfig.getUserNormalRoles()) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        }
        return selectUserVoById(sysUser.getUserId());
    }

    @Override
    public UserVO selectUserVoByWxOpenId(String wxOpenId) {
        return sysUserMapper.selectUserVoByWxOpenId(wxOpenId);
    }

    /**
     * 修改用户信息
     *
     * @param sysUser
     */
    @Override
    public void updateUserNormalService(SysUser sysUser) {
        sysUser.setUsername(sysUser.getNickName());
        sysUser.setUpdateTime(new Date());
        sysUserMapper.updateUserNormal(sysUser);
        userSessionMapper.updateAvatarAndNickName(sysUser);
    }

    /**
     * 修改手机号码
     *
     * @param user
     */
    @Override
    public void updateUserMobileService(SysUser user) {
        user.setUpdateTime(new Date());
        sysUserMapper.updateMobile(user);
    }

    @Override
    public SysUser selectUserBaseByIdService(Integer userId) {
        return sysUserMapper.selectUserBaseById(userId);
    }

    /**
     * 插入信息
     *
     * @param entity
     * @return
     */
    @Override
    public boolean insert(SysUser entity) {
        //随机数
        entity.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        boolean f = super.insert(entity);
        if (f) {
            SysUserWallet wallet = new SysUserWallet();
            wallet.setUserOpenId(entity.getSalt());
            wallet.setCreateTime(new Date());
            wallet.setUserAmount(new BigDecimal(0));
            userWalletMapper.insert(wallet);
        }
        return f;
    }

    /**
     * 保存用户验证码，和randomStr绑定
     *
     * @param randomStr 客户端生成
     * @param imageCode 验证码信息
     */
    @Override
    public void saveImageCode(String randomStr, String imageCode) {
        redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + randomStr, imageCode, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 发送验证码
     * <p>
     * 1. 先去redis 查询是否 60S内已经发送
     * 2. 未发送： 判断手机号是否存 ? false :产生4位数字  手机号-验证码
     * 3. 发往消息中心-》发送信息
     * 4. 保存redis
     *
     * @param mobile 手机号
     * @return true、false
     */
    @Override
    public R<Boolean> sendSmsCode(String mobile) {
        Object tempCode = redisTemplate.opsForValue().get(SecurityConstants.DEFAULT_CODE_KEY + mobile);
        if (tempCode != null) {
            log.error("用户:{}验证码未失效{}", mobile, tempCode);
            return new R<>(false, "验证码未失效，请失效后再次申请");
        }

        SysUser params = new SysUser();
        params.setPhone(mobile);
        List<SysUser> userList = this.selectList(new EntityWrapper<>(params));

        if (CollectionUtil.isEmpty(userList)) {
            log.error("根据用户手机号{}查询用户为空", mobile);
            return new R<>(false, "手机号不存在");
        }

        String code = RandomUtil.randomNumbers(4);
        JSONObject contextJson = new JSONObject();
        contextJson.put("1", code);
//        contextJson.put("code", code);
//        contextJson.put("product", "Pig4Cloud");
        log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
//        rabbitTemplate.convertAndSend(MqQueueConstant.MOBILE_CODE_QUEUE,
//                new MobileMsgTemplate(
//                        mobile,
//                        contextJson.toJSONString(),
//                        CommonConstant.ALIYUN_SMS,
//                        EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getSignName(),
//                        EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getTemplate()
//                ));


        rabbitTemplate.convertAndSend(MqQueueConstant.MOBILE_CODE_QUEUE,
                new MobileMsgTemplate(
                        mobile,
                        contextJson.toJSONString(),
                        CommonConstant.TENCENT_SMS,
                        EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getSignName(),
                        EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getTemplate()
                ));
        redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + mobile, code, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
        return new R<>(true);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = "user_details", key = "#sysUser.username")
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.deleteById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = "user_details", key = "#username")
    public R<Boolean> updateUserInfo(UserDTO userDto, String username) {
        UserVO userVo = this.findUserByUsername(username);
        SysUser sysUser = new SysUser();
        if (StrUtil.isNotBlank(userDto.getPassword())
                && StrUtil.isNotBlank(userDto.getNewpassword1())) {
            if (ENCODER.matches(userDto.getPassword(), userVo.getPassword())) {
                sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
            } else {
                log.warn("原密码错误，修改密码失败:{}", username);
                return new R<>(Boolean.FALSE, "原密码错误，修改失败");
            }
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVo.getUserId());
        sysUser.setAvatarUrl(userDto.getAvatarUrl());
        return new R<>(this.updateById(sysUser));
    }

    @Override
    @CacheEvict(value = "user_details", key = "#username")
    public Boolean updateUser(UserDTO userDto, String username) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(new Date());
        this.updateById(sysUser);

        SysUserRole condition = new SysUserRole();
        condition.setUserId(userDto.getUserId());
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }

    /**
     * 获取当前用户的子部门信息
     *
     * @param userVO 用户信息
     * @return 子部门列表
     */
    private List<Integer> getChildDepts(UserVO userVO) {
        UserVO userVo = findUserByUsername(userVO.getUsername());
        Integer deptId = userVo.getDeptId();

        //获取当前部门的子部门
        SysDeptRelation deptRelation = new SysDeptRelation();
        deptRelation.setAncestor(deptId);
        List<SysDeptRelation> deptRelationList = sysDeptRelationService.selectList(new EntityWrapper<>(deptRelation));
        List<Integer> deptIds = new ArrayList<>();
        for (SysDeptRelation sysDeptRelation : deptRelationList) {
            deptIds.add(sysDeptRelation.getDescendant());
        }
        return deptIds;
    }

}
