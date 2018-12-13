package com.iqb.eatep.activemq.queue.persist.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqb.eatep.activemq.base.ActiveMqReturnInfo;
import com.iqb.eatep.activemq.domain.NotifyRecord;
import com.iqb.eatep.activemq.queue.persist.biz.QueuePersistBiz;
import com.iqb.eatep.activemq.queue.persist.service.IQueuePersistService;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 队列持久化service服务实现
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月20日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class QueuePersistServiceImpl implements IQueuePersistService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(QueuePersistServiceImpl.class);
    
    @Autowired
    private QueuePersistBiz queuePersistBiz;

    @Override
    public Long saveNotifyRecord(NotifyRecord notifyRecord) throws IqbException {
        if(notifyRecord == null){
            logger.error("保存通知记录，传入信息为空");
            throw new IqbException(ActiveMqReturnInfo.MQ_COMMON_01000001);
        }
        return this.queuePersistBiz.saveNotifyRecord(notifyRecord);
    }

    @Override
    public Integer updateNotifyRecord(NotifyRecord notifyRecord) {
        if(notifyRecord == null){
            logger.error("更新通知记录，传入信息为空");
            return 0;
        }
        return this.queuePersistBiz.updateNotifyRecord(notifyRecord);
    }

}
