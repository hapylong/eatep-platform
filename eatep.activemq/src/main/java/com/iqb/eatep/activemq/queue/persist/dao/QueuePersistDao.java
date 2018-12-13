package com.iqb.eatep.activemq.queue.persist.dao;

import com.iqb.eatep.activemq.domain.NotifyRecord;

/**
 * 
 * Description: 队列持久化dao服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月20日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface QueuePersistDao {

    /**
     * 
     * Description: 保存mq通知记录
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月20日 下午6:33:18
     */
    public Long saveNotifyRecord(NotifyRecord notifyRecord);

    /**
     * 
     * Description: 
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 下午1:17:26
     */
    public Integer updateNotifyRecord(NotifyRecord notifyRecord);

}
