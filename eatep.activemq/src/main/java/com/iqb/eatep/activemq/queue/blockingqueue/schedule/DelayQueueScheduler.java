package com.iqb.eatep.activemq.queue.blockingqueue.schedule;

import java.util.concurrent.DelayQueue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.iqb.eatep.activemq.queue.blockingqueue.task.BlockingQueueTask;

/**
 * 
 * Description: 延迟队列调度
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class DelayQueueScheduler {


    protected static final Logger logger = LoggerFactory.getLogger(DelayQueueScheduler.class);

    public static DelayQueue<BlockingQueueTask> tasks = new DelayQueue<BlockingQueueTask>();

    @Resource
    private ThreadPoolTaskExecutor threadPool;
    
    @PostConstruct
    public void init() {
        dealDelayQueue();
    }

    private void dealDelayQueue() {
        threadPool.execute(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        // 如果当前活动线程等于最大线程，那么不执行
                        if (threadPool.getActiveCount() < threadPool.getMaxPoolSize()) {
                            final BlockingQueueTask task = tasks.poll();
                            if (task != null) {
                                threadPool.execute(new Runnable() {
                                    public void run() {
                                        tasks.remove(task);
                                        task.run();
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
}
