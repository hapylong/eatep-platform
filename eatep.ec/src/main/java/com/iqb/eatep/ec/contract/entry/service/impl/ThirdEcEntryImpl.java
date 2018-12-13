package com.iqb.eatep.ec.contract.entry.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iqb.eatep.ec.base.EcAttr.ThirdEcConst;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.contract.entry.service.IThirdEcEntry;
import com.iqb.eatep.ec.contract.entry.service.IThirdEcService;
import com.iqb.eatep.ec.util.EcSpringUtil;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 三方Ec入口
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
public class ThirdEcEntryImpl implements IThirdEcEntry {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(ThirdEcEntryImpl.class);
    
    /** 存储生成的ec服务  **/
    private static final ThreadLocal<IThirdEcService> ecService4Thread = new ThreadLocal<IThirdEcService>();
    
    /**
     * 
     * Description: 路由第三方ec通道
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午1:50:30
     */
    private void routeThirdEcAccess(Object objs, String ecRoute) throws IqbException{
        logger.info("ec路由第三方通道请求信息：{},{}", objs, ecRoute);
        IThirdEcService thirdEcService;
        switch (ecRoute) {
            case ThirdEcConst.SSQ:
                thirdEcService = EcSpringUtil.getBean(SSQEcServiceImpl.class);
                break;

            default:
                thirdEcService = EcSpringUtil.getBean(SSQEcServiceImpl.class);
                break;
        }
        if (thirdEcService == null) {
            throw new IqbException(EcReturnInfo.EC_POI_01010003);
        }
        ecService4Thread.set(thirdEcService);
    }

    @Override
    public IThirdEcService getThirdEcService() throws IqbException {
        return this.getThirdEcService(ThirdEcConst.SSQ);
    }
    
    /**
     * 
     * Description: 获取指定第三方ec服务
     * @param
     * @return IThirdEcService
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月9日 上午10:24:05
     */
    @Override
    public IThirdEcService getThirdEcService(String ecRoute) throws IqbException {
        IThirdEcService thirdEcService = ecService4Thread.get();
        if(thirdEcService == null){
            this.routeThirdEcAccess(null, ecRoute);
        }
        return ecService4Thread.get();
    }
    
}
