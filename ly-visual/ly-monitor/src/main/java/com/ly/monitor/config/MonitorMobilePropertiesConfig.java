package com.ly.monitor.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liyang
 * @Create 2018/4/24
 */
@Data
@ConditionalOnExpression("!'${mobiles}'.isEmpty()")
public class MonitorMobilePropertiesConfig {
    private Boolean enabled;
    private List<String> mobiles = new ArrayList<>();
}
