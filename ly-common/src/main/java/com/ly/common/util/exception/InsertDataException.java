package com.ly.common.util.exception;

/**
 * 插入数据时候的异常
 *
 * @auther Administrator liyang
 * @Create 2018/8/27
 */
public class InsertDataException extends Exception {

    public InsertDataException() {
        super("数据插入失败！");
    }

    public InsertDataException(String table) {
        super(String.format("%s : 数据插入失败！",table));
    }
}
