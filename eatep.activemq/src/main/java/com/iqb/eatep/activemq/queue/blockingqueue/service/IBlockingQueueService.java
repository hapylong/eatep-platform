package com.iqb.eatep.activemq.queue.blockingqueue.service;

import com.iqb.eatep.activemq.domain.NotifyRecord;
import com.iqb.etep.common.exception.IqbException;


/**
 * 
 * Description: 延迟队列服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月17日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IBlockingQueueService {

    /**
     * 
     * Description: 插入消息
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月20日 下午1:49:21
     */
    public void addMsg(Object map) throws IqbException;
    
    /**
     * 
     * Description: 加入队列
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 上午10:42:59
     */
    public void addQueue(NotifyRecord notifyRecord) throws IqbException;

}
