package com.iqb.eatep.activemq.queue.blockingqueue.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.activemq.base.ActiveMqReturnInfo;
import com.iqb.eatep.activemq.domain.NotifyParam;
import com.iqb.eatep.activemq.domain.NotifyRecord;
import com.iqb.eatep.activemq.domain.NotifyStatusEnum;
import com.iqb.eatep.activemq.message.creator.EatepSessionMessageCreator;
import com.iqb.eatep.activemq.queue.blockingqueue.biz.DelayQueueBiz;
import com.iqb.eatep.activemq.queue.blockingqueue.schedule.DelayQueueScheduler;
import com.iqb.eatep.activemq.queue.blockingqueue.service.IBlockingQueueService;
import com.iqb.eatep.activemq.queue.blockingqueue.task.BlockingQueueTask;
import com.iqb.eatep.activemq.queue.persist.service.IQueuePersistService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.DateUtil;

/**
 * 
 * Description: 延迟队列服务实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月17日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class DelayQueueServiceImpl implements IBlockingQueueService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(DelayQueueServiceImpl.class);
    
    @Autowired
    private DelayQueueBiz delayQueueBiz;
    
    @Autowired
    private IQueuePersistService queuePersistServiceImpl;
    
    @Autowired
    private EatepSessionMessageCreator eatepSessionMessageCreator;
    
    @Autowired
    private NotifyParam notifyParam;

    @Override
    public void addMsg(Object map) throws IqbException {
        if(map == null){
            throw new IqbException(ActiveMqReturnInfo.MQ_COMMON_01000001);
        }
        this.eatepSessionMessageCreator.createMessage(JSONObject.toJSONString(map));
    }

    @Override
    public void addQueue(NotifyRecord notifyRecord) throws IqbException {
        /** 比较通知次数，如果小于最大通知次数，加入队列  **/
        if (!this.judgeExceedMaxNotifyTimes(notifyRecord)) {
            Integer nextNotifyTime = this.getNextNotifyTime(notifyRecord);
            if(nextNotifyTime == Integer.MIN_VALUE){
                logger.info("notifyParam未查询到对应次数，通知次数超过上限？");
                return;
            }
            notifyRecord.setLastNotifyTime(nextNotifyTime);
            DelayQueueScheduler.tasks.put(new BlockingQueueTask(notifyRecord, this.notifyParam, this.queuePersistServiceImpl));
            logger.debug("第{}次往对列放的数据为:{}", notifyRecord.getNotifyTimes(), notifyRecord.toString());
        } else {
            /** 失败通知记录  **/
            notifyRecord.setStatus(NotifyStatusEnum.FAILED.getValue());
            this.queuePersistServiceImpl.updateNotifyRecord(notifyRecord);
        }
    }
    
    /**
     * 
     * Description: 获取下一次通知时间
     * @param
     * @return Long
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 下午1:39:29
     */
    private Integer getNextNotifyTime(NotifyRecord notifyRecord){
        Integer time = notifyRecord.getLastNotifyTime();
        Map<Integer, Integer> timeMap = notifyParam.getNotifyParams();
        Integer notifyTimes = notifyRecord.getNotifyTimes();
        Integer nextKey = notifyTimes + 1;
        Integer next = timeMap.get(nextKey);
        if (next != null) {
            time += 1000 * 60 * next + 1;
            return time;
        }
        return Integer.MIN_VALUE;
    }
    
    /**
     * 
     * Description: 判断是否大于最大通知次数
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 上午10:55:32
     */
    private boolean judgeExceedMaxNotifyTimes(NotifyRecord notifyRecord){
        /** 比较通知次数，如果小于最大通知次数，加入队列  **/
        Integer notifyTimes = notifyRecord.getNotifyTimes();
        Integer maxNotifyTime = this.notifyParam.getMaxNotifyTime();
        if (notifyRecord.getVersion().intValue() == 0) {
            notifyRecord.setLastNotifyTime(DateUtil.getTime(new Date()));
        }
        if (notifyTimes >= maxNotifyTime) {
            return true;
        }
        return false;
    }

}
