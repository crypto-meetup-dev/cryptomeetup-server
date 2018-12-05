package com.ly.daemon.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ly.daemon.fegin.ShopOrderTotalService;
import com.zen.elasticjob.spring.boot.annotation.ElasticJobConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author liyang
 * @Create 2018/2/8
 */
@ElasticJobConfig(cron = "0 0/1 0/1 * * ?", shardingTotalCount = 3, shardingItemParameters = "0=0,1=1,2=2")
public class ShopOrderTotalDataFlowJob implements DataflowJob<Integer> {


    @Autowired
    ShopOrderTotalService orderTotalService;

    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Integer> fetchData(ShardingContext shardingContext) {
        logger.info("执行商店订单归档统计");
        List<Integer> list = orderTotalService.totalTodayShops(shardingContext.getShardingItem(),shardingContext.getShardingTotalCount());
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Integer> list) {

        logger.info(String.format("ShopOrderTotalDataFlowJob------>Thread ID: %s, " +
                        ",任务总片数: %s,当前需要执的数据共: %s条, " +
                        "当前分片项: %s.当前参数: %s"+
                        "当前任务名称: %s.当前任务参数: %s"
                ,
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                (list==null?0:list.size()),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));

        for(int id : list){
            boolean result = orderTotalService.executeShop(id);
        }
    }
}
