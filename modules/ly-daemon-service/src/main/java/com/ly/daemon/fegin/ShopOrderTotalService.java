package com.ly.daemon.fegin;

import com.ly.daemon.fegin.fallback.ShopOrderTotalServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 订单统计
 *
 * @auther Administrator liyang
 * @create 2018/10/24 22:23
 */
@FeignClient(name = "ly-upms-service", fallback = ShopOrderTotalServiceImpl.class)
public interface ShopOrderTotalService {

    /**
     * 获取分片任务今天需要统计的订单信息
     * @param job
     * @return
     */
    @GetMapping("/shop/internal/order/total/today/shops")
    List<Integer> totalTodayShops(@RequestParam("job") Integer job,@RequestParam("count") Integer count);

    /**
     * 执行需要统计的订单
     * @return
     */
    @PostMapping("/shop/internal/order/total/execute/shop")
    boolean executeShop(@RequestParam("shopId") Integer shopId);

}
