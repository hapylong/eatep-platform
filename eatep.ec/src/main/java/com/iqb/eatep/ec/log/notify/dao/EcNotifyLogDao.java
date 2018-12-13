package com.iqb.eatep.ec.log.notify.dao;

import com.iqb.eatep.ec.log.notify.bean.EcNotifyBean;

/**
 * 
 * Description: ec异步通知日志到服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月9日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface EcNotifyLogDao {

    /**
     * 
     * Description: 记录日志
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午9:58:24
     */
    public Integer addLog(EcNotifyBean ecNotifyBean);

}
