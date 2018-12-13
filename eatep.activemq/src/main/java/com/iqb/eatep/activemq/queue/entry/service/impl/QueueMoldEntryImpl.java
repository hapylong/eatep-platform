package com.iqb.eatep.activemq.queue.entry.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iqb.eatep.activemq.base.ActiveMqAttr.MqMoldConst;
import com.iqb.eatep.activemq.base.ActiveMqReturnInfo;
import com.iqb.eatep.activemq.queue.entry.service.IQueueMoldEntry;
import com.iqb.eatep.activemq.queue.entry.service.IQueueMoldService;
import com.iqb.eatep.activemq.util.ActMQSpringUtil;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 队列类型服务实现
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
public class QueueMoldEntryImpl implements IQueueMoldEntry {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(QueueMoldEntryImpl.class);
    
    /** 存储生成的q服务  **/
    private static final ThreadLocal<IQueueMoldService> qService4Thread = new ThreadLocal<IQueueMoldService>();

    @Override
    public IQueueMoldService getQueueService() throws IqbException {
        return this.getQueueService(null);
    }
    
    /**
     * 
     * Description: 路由mq服务
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月17日 下午2:16:25
     */
    private void routeMqService(Object objs, String queueMold) throws IqbException{
        logger.info("mq路由队列类型：{},{}", objs == null ? null : objs.toString(), queueMold);
        IQueueMoldService qMoldService;
        switch (queueMold) {
            case MqMoldConst.BLOCKING_QUEUE:
                qMoldService = ActMQSpringUtil.getBean(BlockingQueueServiceImpl.class);
                break;

            default:
                qMoldService = ActMQSpringUtil.getBean(BlockingQueueServiceImpl.class);
                break;
        }
        if (qMoldService == null) {
            throw new IqbException(ActiveMqReturnInfo.MQ_ROUTE_01010001);
        }
        qService4Thread.set(qMoldService);
    }

    @Override
    public IQueueMoldService getQueueService(String queueMold) throws IqbException {
        IQueueMoldService qMoldService = qService4Thread.get();
        if(qMoldService == null){
            this.routeMqService(null, queueMold);
        }
        return qService4Thread.get();
    }

}
