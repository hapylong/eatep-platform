package com.iqb.eatep.ec.log.notify.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.log.notify.bean.EcNotifyBean;
import com.iqb.eatep.ec.log.notify.biz.EcNotifyLogBiz;
import com.iqb.eatep.ec.log.notify.service.IEcNotifyLogService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.BeanUtil;
import com.iqb.etep.common.utils.StringUtil;

/**
 * 
 * Description: ec通知服务实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class EcNotifyLogServiceImpl implements IEcNotifyLogService {

    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(EcNotifyLogServiceImpl.class);
    
    @Autowired
    private EcNotifyLogBiz ecNotifyLogBiz;

    @Override
    public Object addLog(Object objs) throws IqbException {
        if(objs == null){
            logger.error("ec异步通知日志传入信息为null");
        }
        logger.info("ec异步通知日志传入信息{}", JSONObject.toJSONString(objs));
        if(objs instanceof String){
            this.addLog(objs);
        }
        try {
            return this.addLog(JSONObject.toJSONString(objs));
        } catch (Exception e) {
            logger.error("ec异步通知日志传入信息为null");
        }
        return null;
    }
    
    /**
     * 
     * Description: 记录日志
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午9:53:18
     */
    private Object addLog(String logStr) throws IqbException {
        logger.info("ec异步通知日志传入信息{}", logStr);
        if(StringUtil.isEmpty(logStr)){
            throw new IqbException(EcReturnInfo.EC_LOG_01020001);
        }
        
        /** bean转换  **/
        EcNotifyBean ecNotifyBean = BeanUtil.toJavaObject(logStr, EcNotifyBean.class);
        
        Integer i = this.ecNotifyLogBiz.addLog(ecNotifyBean);
        
        return i;
    }

}
