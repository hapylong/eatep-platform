package com.iqb.eatep.activemq.queue.entry.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iqb.eatep.activemq.base.ActiveMqAttr.MqMoldConst.BlockingQueue;
import com.iqb.eatep.activemq.base.ActiveMqReturnInfo;
import com.iqb.eatep.activemq.queue.blockingqueue.service.IBlockingQueueService;
import com.iqb.eatep.activemq.queue.blockingqueue.service.impl.DelayQueueServiceImpl;
import com.iqb.eatep.activemq.queue.entry.service.IQueueMoldService;
import com.iqb.eatep.activemq.util.ActMQSpringUtil;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 阻塞队列
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
public class BlockingQueueServiceImpl implements IQueueMoldService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(QueueMoldEntryImpl.class);
    
    /** 存储生成的q服务  **/
    private static final ThreadLocal<IBlockingQueueService> qService4Thread = new ThreadLocal<IBlockingQueueService>();

    @Override
    public IBlockingQueueService getQueueTypeService() throws IqbException {
        return this.getQueueTypeService(null);
    }
    
    /**
     * 
     * Description: 路由mq服务类型
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月17日 下午2:17:55
     */
    private void routeMqTypeService(Object objs, String queueType) throws IqbException{
        logger.info("mq路由队列类型：{},{}", objs == null ? null : objs.toString(), queueType);
        IBlockingQueueService bQueueService;
        switch (queueType) {
            case BlockingQueue.DELAY_QUEUE:
                bQueueService = ActMQSpringUtil.getBean(DelayQueueServiceImpl.class);
                break;

            default:
                bQueueService = ActMQSpringUtil.getBean(DelayQueueServiceImpl.class);
                break;
        }
        if (bQueueService == null) {
            throw new IqbException(ActiveMqReturnInfo.MQ_ROUTE_01010001);
        }
        qService4Thread.set(bQueueService);
    }

    @Override
    public IBlockingQueueService getQueueTypeService(String queueType) throws IqbException {
        IBlockingQueueService bQueueService = qService4Thread.get();
        if(bQueueService == null){
            this.routeMqTypeService(null, queueType);
        }
        return qService4Thread.get();
    }

}
