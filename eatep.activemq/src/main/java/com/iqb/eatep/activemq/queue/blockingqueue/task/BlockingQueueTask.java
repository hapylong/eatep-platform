package com.iqb.eatep.activemq.queue.blockingqueue.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iqb.eatep.activemq.IActiveMqTask;
import com.iqb.eatep.activemq.domain.NotifyParam;
import com.iqb.eatep.activemq.domain.NotifyRecord;
import com.iqb.eatep.activemq.domain.NotifyStatusEnum;
import com.iqb.eatep.activemq.queue.persist.service.IQueuePersistService;
import com.iqb.eatep.activemq.util.ActMQSpringUtil;

/**
 * 
 * Description: 阻塞队列任务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class BlockingQueueTask implements Delayed {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(BlockingQueueTask.class);
    
    private long executeTime;
    
    private NotifyRecord notifyRecord;
    
    private NotifyParam notifyParam;
    
    private IQueuePersistService queuePersistService;
    
    public BlockingQueueTask() {
        
    }
    
    public BlockingQueueTask(NotifyRecord notifyRecord, NotifyParam notifyParam, IQueuePersistService queuePersistService) {
        this.notifyRecord = notifyRecord;
        this.notifyParam = notifyParam;
        this.queuePersistService = queuePersistService;
        this.executeTime = getExecuteTime(notifyRecord);
    }

    private long getExecuteTime(NotifyRecord record) {
        long lastTime = record.getLastNotifyTime();
        Integer nextNotifyTime = notifyParam.getNotifyParams().get(record.getNotifyTimes());
        return (nextNotifyTime == null ? 0 : nextNotifyTime * 1000) + lastTime;
    }

    @Override
    public int compareTo(Delayed o) {
        BlockingQueueTask task = (BlockingQueueTask) o;
        return executeTime > task.executeTime ? 1 : (executeTime < task.executeTime ? -1 : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.SECONDS);
    }

    /**
     * 
     * Description: 队列任务run方法
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 下午3:51:38
     */
    public void run() {
        try {
            Class<?> className = this.notifyRecord.getActiveMqTask();
            IActiveMqTask activeMqTask = (IActiveMqTask) ActMQSpringUtil.getBean(className);
            Object retObj = activeMqTask.run(this.notifyRecord.getMsg());
            if("succ".equals(ObjectUtils.toString(retObj))){
                notifyRecord.setStatus(NotifyStatusEnum.SUCCESS.getValue());
                queuePersistService.updateNotifyRecord(notifyRecord);
            }
            notifyRecord.setStatus(NotifyStatusEnum.FAILED.getValue());
            queuePersistService.updateNotifyRecord(notifyRecord);
        } catch (Exception e) {
            logger.info("执行task任务异常,{}", e);
            e.printStackTrace();
            notifyRecord.setStatus(NotifyStatusEnum.FAILED.getValue());
            queuePersistService.updateNotifyRecord(notifyRecord);
        }

    }

}
