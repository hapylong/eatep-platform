package com.iqb.eatep.activemq.queue.persist.service;

import com.iqb.eatep.activemq.domain.NotifyRecord;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 队列持久化服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月20日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IQueuePersistService {
    
    /**
     * 
     * Description: 保存mq通知记录
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月20日 下午6:30:10
     */
    public Long saveNotifyRecord(NotifyRecord notifyRecord) throws IqbException;
    
    /**
     * 
     * Description: 更新mq通知记录
     * @param
     * @return Long
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 上午11:53:32
     */
    public Integer updateNotifyRecord(NotifyRecord notifyRecord);

}
