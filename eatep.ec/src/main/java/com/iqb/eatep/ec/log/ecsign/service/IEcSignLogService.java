package com.iqb.eatep.ec.log.ecsign.service;

import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: ec合同签署日志服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IEcSignLogService {
    
    /**
     * 
     * Description: 添加日志
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午6:37:28
     */
    public Object addLog(Object objs) throws IqbException;

}
