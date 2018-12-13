package com.iqb.eatep.ec.contract.entry.service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 第三方Ec服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IThirdEcService {
    
    /**
     * 
     * Description: 处理上上签手动签署通知接口
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 上午11:50:54
     */
    public Object dealSsqManualSignNotify(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 上上签同步接口返回
     * @param
     * @return Object
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月23日 下午1:32:26
     */
    public Object dealSsqManualSignReturnUrl(JSONObject objs) throws IqbException;
    
}
