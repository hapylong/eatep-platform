package com.iqb.eatep.activemq.message.creator;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * 
 * Description: eatep消息创建者
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月17日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class EatepSessionMessageCreator {

    @Resource
    private JmsTemplate notifyJmsTemplate;
    @Resource
    private Destination sessionAwareQueue;
    
    /**
     * 
     * Description: 发送消息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月20日 下午7:47:17
     */
    public void createMessage(final String ms) {
        notifyJmsTemplate.send(sessionAwareQueue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(ms);
            }
        });
    }

}
