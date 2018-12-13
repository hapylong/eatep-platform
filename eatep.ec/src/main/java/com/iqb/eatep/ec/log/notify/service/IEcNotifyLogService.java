package com.iqb.eatep.ec.log.notify.service;

import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: ec通知服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IEcNotifyLogService {

    /**
     * 
     * Description: 记录日志
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午9:51:50
     */
    public Object addLog(Object objs) throws IqbException;
    
}
