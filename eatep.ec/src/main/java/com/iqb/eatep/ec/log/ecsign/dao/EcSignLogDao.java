package com.iqb.eatep.ec.log.ecsign.dao;

import com.iqb.eatep.ec.log.ecsign.bean.EcSignLogBean;

/**
 * 
 * Description: ec签署日志dao服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月9日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface EcSignLogDao {

    /**
     * 
     * Description: 增加日志
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午9:41:54
     */
    public Integer addLog(EcSignLogBean ecSignLogBean);

}
