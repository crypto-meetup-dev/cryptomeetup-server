package com.ly.common.vo;

import com.ly.common.entity.SysLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author liyang
 * @Create 2018/2/20
 */
@Data
public class LogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SysLog sysLog;
    private String username;
}
