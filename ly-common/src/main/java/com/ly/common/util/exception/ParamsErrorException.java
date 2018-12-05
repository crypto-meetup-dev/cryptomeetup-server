package com.ly.common.util.exception;

/**
 * 用户没有权限修改一些数据
 *
 * @auther Administrator liyang
 * @Create 2018/9/14 10:28
 */
public class ParamsErrorException extends RuntimeException {


    public ParamsErrorException() {
        super("参数错误!");
    }

    public ParamsErrorException(String msg) {
        super(msg);
    }
}
