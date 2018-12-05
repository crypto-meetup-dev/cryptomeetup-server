package com.ly.common.util;

/**
 * @auther Administrator liyang
 * @Create 2018/9/14
 * 常见result API 返回码
 */
public enum ResultCodeEnum {

    verifyCodeError(478,"验证码错误,请重新输入"),
    previewError(479,"演示环境，没有权限操作"),
    appIdError(480,"商户ID错误,没有操作权限."),
    authError(401,"当前操作没有权限."),
    inAuthError(403,"授权拒绝."),
    accountOrUpasswordError(400,"用户名不存在或密码错误."),
    error(-1,"系统未知错误,请反馈给管理员."),
    paramsError(481,"参数错误"),
    success(0,"请求成功.");

    private String msg;
    private int code;

    ResultCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public R createResultR(){
        return new R<>(this.code,this.msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
