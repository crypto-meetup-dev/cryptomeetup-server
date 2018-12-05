package com.ly.common.util;

import com.ly.common.constant.CommonConstant;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Optional;

/**
 * @Author liyang
 * @Create 2018/5/22
 */
public class UserUtilsTest {
    @Test
    public void getToken() throws Exception {
        String authorization = null;
        System.out.println(StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT));
    }

    @Test
    public void optionalTest() {
        Optional<String> optional = Optional.ofNullable("");
        System.out.println(optional.isPresent());
    }

}