package com.iqb.eatep.activemq;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.activemq.base.ActiveMqReturnInfo;
import com.iqb.eatep.activemq.domain.NotifyRecord;
import com.iqb.eatep.activemq.domain.NotifyStatusEnum;
import com.iqb.eatep.activemq.queue.blockingqueue.service.IBlockingQueueService;
import com.iqb.eatep.activemq.queue.entry.service.IQueueMoldEntry;
import com.iqb.eatep.activemq.queue.entry.service.IQueueMoldService;
import com.iqb.eatep.activemq.queue.persist.service.IQueuePersistService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.BeanUtil;

/**
 * 
 * Description: activemq服务（模块入口）
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
public class ActiveMqService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(ActiveMqService.class);
    
    /** mq任务 **/
    private static final ThreadLocal<Class<?>> mqCommonTask4Thread = new ThreadLocal<Class<?>>();
    
    @Autowired
    private IQueueMoldEntry queueMoldEntryImpl;
    
    @Autowired
    private IQueuePersistService queuePersistServiceImpl;
    
    /**
     * 
     * Description: 创建消息
     * @param
     * @return Object
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月17日 下午5:07:02
     */
    public void addMsg(Object objs) throws IqbException{
        if(objs == null){
            logger.error("添加activemq,信息传入数据为空");
            throw new IqbException(ActiveMqReturnInfo.MQ_COMMON_01000001);
        }
        if(objs instanceof String){
            logger.info("添加activemq,信息传入参数：{}", objs);
            JSONObject bizJsonObjs;
            try {
                bizJsonObjs = JSONObject.parseObject((String) objs);
            } catch (Exception e) {
                throw new IqbException(ActiveMqReturnInfo.MQ_COMMON_01000002);
            }
            this.addMsg(bizJsonObjs);
            return;
        }
        logger.info("添加activemq,信息传入参数：{}", JSONObject.toJSONString(objs));
        this.addMsg(JSONObject.toJSONString(objs));
    }
    
    /**
     * 
     * Description: 创建消息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月20日 上午11:27:13
     */
    private void addMsg(Map<String, Object> map) throws IqbException {
        logger.info("加入队列，信息传入参数：{}", JSONObject.toJSONString(map));
        if(map == null){
            throw new IqbException(ActiveMqReturnInfo.MQ_COMMON_01000001);
        }
        Object objService = this.routeMqService(map);
        
        /** 封装通知bean **/
        NotifyRecord notifyRecord = BeanUtil.mapToBean(map, NotifyRecord.class);
        notifyRecord.setActiveMqTask(mqCommonTask4Thread.get());
        
        /** 判断返回服务类型  **/
        if(objService instanceof IBlockingQueueService){
            
            IBlockingQueueService ib = (IBlockingQueueService) objService;
            ib.addMsg(notifyRecord);
            
        }
        
    }
    
    /**
     * 
     * Description: 加入队列
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 上午10:28:12
     */
    public void addQueue(String ms) throws IqbException{
        logger.info("加入队列消息{}", ms);
        NotifyRecord notifyRecord = JSONObject.parseObject(ms, NotifyRecord.class);
        if(notifyRecord == null){
            logger.info("加入队列消息为空{}", ms);
            return;
        }
        /** 通知信息持久化  **/
        if(notifyRecord.getId() == null){
            notifyRecord.setStatus(NotifyStatusEnum.CREATED.getValue());
            this.queuePersistServiceImpl.saveNotifyRecord(notifyRecord);
        }
        Object objService = this.routeMqService(notifyRecord);
        
        /** 判断返回服务类型  **/
        if(objService instanceof IBlockingQueueService){
            
            IBlockingQueueService ib = (IBlockingQueueService) objService;
            ib.addQueue(notifyRecord);
            
        }
        
    }
    
    /**
     * 
     * Description: 路由mq服务
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月20日 上午11:45:19
     */
    private Object routeMqService(Map<String, Object> map) throws IqbException {
        /** 通知记录  **/
        NotifyRecord notifyRecord = BeanUtil.mapToBean(map, NotifyRecord.class);
        IQueueMoldService queueMoldService = this.queueMoldEntryImpl.getQueueService(notifyRecord.getMqMold());
        Object obj = queueMoldService.getQueueTypeService(notifyRecord.getMqMoldType());
        return obj;
    }
    
    /**
     * 
     * Description: 路由mq服务
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月21日 上午10:39:44
     */
    private Object routeMqService(NotifyRecord notifyRecord) throws IqbException {
        IQueueMoldService queueMoldService = this.queueMoldEntryImpl.getQueueService(notifyRecord.getMqMold());
        Object obj = queueMoldService.getQueueTypeService(notifyRecord.getMqMoldType());
        return obj;
    }
    
    /**
     * 
     * Description: 设置mq任务
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月17日 下午5:20:38
     */
    public void setMqCommonTask(Class<?> clazz){
        mqCommonTask4Thread.set(clazz);
    }
    
}
