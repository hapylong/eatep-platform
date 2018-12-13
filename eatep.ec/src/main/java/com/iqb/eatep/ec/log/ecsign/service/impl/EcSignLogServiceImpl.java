package com.iqb.eatep.ec.log.ecsign.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.log.ecsign.bean.EcSignLogBean;
import com.iqb.eatep.ec.log.ecsign.biz.EcSignLogBiz;
import com.iqb.eatep.ec.log.ecsign.service.IEcSignLogService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.BeanUtil;
import com.iqb.etep.common.utils.StringUtil;

/**
 * 
 * Description: ec合同签署服务实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcSignLogServiceImpl implements IEcSignLogService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(EcSignLogServiceImpl.class);
    
    @Autowired
    private EcSignLogBiz ecSignLogBiz;

    @Override
    public Object addLog(Object objs) throws IqbException {
        if(objs == null){
            logger.error("ec签署日志传入信息为null");
        }
        logger.info("ec签署日志传入信息{}", JSONObject.toJSONString(objs));
        if(objs instanceof String){
            this.addLog(objs);
        }
        try {
            return this.addLog(JSONObject.toJSONString(objs));
        } catch (Exception e) {
            logger.error("ec签署日志传入信息为null");
        }
        return null;
    }
    
    /**
     * 
     * Description: 添加日志
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午6:38:46
     */
    private Object addLog(String logStr) throws IqbException {
        logger.info("ec签署日志传入信息{}", logStr);
        if(StringUtil.isEmpty(logStr)){
            throw new IqbException(EcReturnInfo.EC_LOG_01020001);
        }
        
        /** bean转换  **/
        EcSignLogBean ecSignLogBean = BeanUtil.toJavaObject(logStr, EcSignLogBean.class);
        
        Integer i = this.ecSignLogBiz.addLog(ecSignLogBean);
        
        return i;
    }

}
