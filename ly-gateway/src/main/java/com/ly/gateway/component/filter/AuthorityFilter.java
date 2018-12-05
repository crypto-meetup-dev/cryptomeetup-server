package com.ly.gateway.component.filter;

import com.alibaba.fastjson.JSONObject;
import com.ly.common.util.R;
import com.ly.common.util.UserUtils;
import com.ly.common.vo.UserVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 商户系统数据修改权限判断
 *
 * @auther Administrator liyang
 * @Create 2018/9/14
 */
@Slf4j
@Component
public class AuthorityFilter extends ZuulFilter {
    private static final String TOKEN = "token";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext request = RequestContext.getCurrentContext();


        //过滤商场店主管理员的权限
        if(request.getRequest().getRequestURI().startsWith("/shop/boss/")){
            UserVO userVO = UserUtils.getUser(request.getRequest());
            String appId = request.getRequest().getHeader("appId");
            if(StringUtils.isEmpty(appId) || !appId.equals(userVO.getAppId())){
                //如果请求的信息不是自己商场的信息就不让访问
                return true;
            }
        }else if(request.getRequest().getRequestURI().startsWith("/shop/customer/")
                ||request.getRequest().getRequestURI().startsWith("/pdd/customer/")){
            UserVO userVO = UserUtils.getUser(request.getRequest());
            request.addZuulRequestHeader("userId",userVO.getUserId()+"");
            String appId = request.getRequest().getHeader("appId");
            if(StringUtils.isEmpty(appId) || !appId.equals(userVO.getAppId())){
                //如果请求的信息不是自己商场的信息就不让访问
                return true;
            }

        }else if(request.getRequest().getRequestURI().startsWith("/bt/customer/")){
            UserVO userVO = UserUtils.getUser(request.getRequest());
            request.addZuulRequestHeader("userId",userVO.getUserId()+"");
        }

        //ture 为控制 false 不控制
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        R<String> result = new R<>();
        result.setCode(480);
        result.setMsg("商户ID错误.");

        ctx.setResponseStatusCode(480);
        ctx.setSendZuulResponse(false);
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
        ctx.setResponseBody(JSONObject.toJSONString(result));
        return null;
    }
}
