package com.iqb.eatep.ec.log.notify.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.eatep.ec.log.notify.bean.EcNotifyBean;
import com.iqb.eatep.ec.log.notify.dao.EcNotifyLogDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * 
 * Description: Ec异步通知日志biz服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class EcNotifyLogBiz extends BaseBiz {
    
    @Autowired
    private EcNotifyLogDao ecNotifyLogDao;

    /**
     * 
     * Description: 记录日志
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午9:55:04
     */
    public Integer addLog(EcNotifyBean ecNotifyBean) {
        super.setDb(0, super.MASTER);
        return this.ecNotifyLogDao.addLog(ecNotifyBean);
    }

}
