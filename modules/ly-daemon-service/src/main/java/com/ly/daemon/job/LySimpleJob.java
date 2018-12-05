package com.ly.daemon.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zen.elasticjob.spring.boot.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author liyang
 * @Create 2018/2/7
 * 测试Job
 */
@Slf4j
@ElasticJobConfig(cron = "0 0 0/1 * * ?", shardingTotalCount = 3,
        shardingItemParameters = "0=0,1=1,2=2",
        startedTimeoutMilliseconds = 5000L,
        completedTimeoutMilliseconds = 10000L,
        eventTraceRdbDataSource = "dataSource",
        description = "测试Job")
public class LySimpleJob implements SimpleJob {
    /**
     * 业务执行逻辑
     *
     * @param shardingContext 分片信息
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("------Thread ID: %s, 任务总片数: %s, " +
                        "当前分片项: %s.当前参数: %s,"+
                        "当前任务名称: %s.当前任务参数: %s"
                ,
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));
    }
}
