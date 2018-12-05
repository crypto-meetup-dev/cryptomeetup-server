package com.ly.common.util.exception;

/**
 * 用户没有权限修改一些数据
 *
 * @auther Administrator liyang
 * @Create 2018/9/14
 */
public class UserUpdateAuthorityException  extends RuntimeException {


    public UserUpdateAuthorityException() {
        super("没有权限操作该数据!");
    }
}
