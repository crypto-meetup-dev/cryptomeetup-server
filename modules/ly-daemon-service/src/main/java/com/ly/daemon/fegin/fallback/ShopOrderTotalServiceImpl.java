package com.ly.daemon.fegin.fallback;

import com.ly.daemon.fegin.ShopOrderTotalService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @auther Administrator liyang
 * @create 2018/10/24 22:25
 * 获取统计订单信息
 */
@Service
public class ShopOrderTotalServiceImpl implements ShopOrderTotalService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Integer> totalTodayShops(Integer job, Integer count) {
        logger.error("调用{}异常:{}", "totalTodayShops", job, count);
        return null;
    }

    @Override
    public boolean executeShop(Integer orderId) {
        logger.error("调用{}异常:{}", "executeShop", orderId);
        return false;
    }
}
