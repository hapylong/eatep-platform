package com.iqb.eatep.ec.log.ecsign.biz;

import org.springframework.beans.factory.annotation.Autowired;

import com.iqb.eatep.ec.log.ecsign.bean.EcSignLogBean;
import com.iqb.eatep.ec.log.ecsign.dao.EcSignLogDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * 
 * Description: ec合同签署biz服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcSignLogBiz extends BaseBiz{
    
    @Autowired
    private EcSignLogDao ecSignLogDao;
    
    /**
     * 
     * Description: 添加日志
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午7:14:57
     */
    public Integer addLog(EcSignLogBean ecSignLogBean) {
        super.setDb(0, super.MASTER);
        return this.ecSignLogDao.addLog(ecSignLogBean);
    }

}
