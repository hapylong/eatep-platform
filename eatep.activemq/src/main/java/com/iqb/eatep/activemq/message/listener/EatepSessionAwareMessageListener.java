/**
 * @Copyright (c) http://www.iqianbang.com/ All rights reserved.
 * @Description: TODO
 * @date 2016年7月1日 上午11:25:33
 * @version V1.0
 */

package com.iqb.eatep.activemq.message.listener;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import com.iqb.eatep.activemq.ActiveMqService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.StringUtil;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class EatepSessionAwareMessageListener implements SessionAwareMessageListener<Message> {

    /** 日志  **/
    protected static final Logger logger = LoggerFactory.getLogger(EatepSessionAwareMessageListener.class);

    @Resource
    private JmsTemplate jmsTemplate;
    
    @Resource
    private Destination sessionAwareQueue;
    
    @Resource
    private ActiveMqService activeMqService;
    
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        /** 接收msg **/
        ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
        String ms = msg.getText();
        if(StringUtil.isEmpty(ms)){
            logger.error("activemq监听到的数据text为空");
            return;
        }
        try {
            activeMqService.addQueue(ms);
        } catch (IqbException e) {
            e.printStackTrace();
            logger.error("加入队列发生异常：{}", e);
            logger.error("加入队列发生异常,异常信息为：{}", ms);
        }
    }

}
