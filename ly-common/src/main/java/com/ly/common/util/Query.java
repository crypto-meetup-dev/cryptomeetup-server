package com.ly.common.util;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @Author liyang
 * @Create 2018/5/10
 */
public class Query<T> extends Page<T> {
    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String ORDER_BY_FIELD = "orderByField";
    private static final String IS_ASC = "isAsc";

    public Query(Map<String, Object> params) {
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));

        String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
        if (StringUtils.isNotEmpty(orderByField)) {
            this.setOrderByField(orderByField);
        }

        Boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
        this.setAsc(isAsc);

        params.remove(PAGE);
        params.remove(LIMIT);
        params.remove(ORDER_BY_FIELD);
        params.remove(IS_ASC);
        params = mapHump(params);
        this.setCondition(params);
    }

    /**
     * 驼峰转换
     * @param params
     * @return
     */
    private Map<String,Object> mapHump(Map<String, Object> params){
        Map<String, Object> pms = new HashedMap();

        for(Map.Entry<String,Object> pm : params.entrySet()){
            pms.put(BeanHumpUtils.camelToUnderline(pm.getKey()),pm.getValue());
        }
        return pms;
    }
}
