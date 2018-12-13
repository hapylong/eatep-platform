package com.iqb.eatep.ec.contract.entry.service;

import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 第三方ec入口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IThirdEcEntry {
    
    /**
     * 
     * Description: 获取ec第三方服务
     * @param
     * @return IThirdEcService
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午10:14:54
     */
    public IThirdEcService getThirdEcService() throws IqbException;

    /**
     * 
     * Description: 获取指定ec第三方服务
     * @param
     * @return IThirdEcService
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午10:25:02
     */
    public IThirdEcService getThirdEcService(String ecRoute) throws IqbException;

}
