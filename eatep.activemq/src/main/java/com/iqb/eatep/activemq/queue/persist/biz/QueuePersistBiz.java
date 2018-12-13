package com.iqb.eatep.activemq.queue.persist.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.eatep.activemq.domain.NotifyRecord;
import com.iqb.eatep.activemq.queue.persist.dao.QueuePersistDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * 
 * Description: 队列持久化biz服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月20日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class QueuePersistBiz extends BaseBiz {
    
    @Autowired
    private QueuePersistDao queuePersistDao;

    /**
     * 
     * Description: 保存mq通知记录
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月20日 下午6:31:43
     */
    public Long saveNotifyRecord(NotifyRecord notifyRecord) {
        this.setDb(0, super.MASTER);
        return this.queuePersistDao.saveNotifyRecord(notifyRecord);
    }

    /**
     * 
     * Description: 更新mq通知记录
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 上午11:54:38
     */
    public Integer updateNotifyRecord(NotifyRecord notifyRecord) {
        this.setDb(0, super.MASTER);
        return this.queuePersistDao.updateNotifyRecord(notifyRecord);
    }

}
