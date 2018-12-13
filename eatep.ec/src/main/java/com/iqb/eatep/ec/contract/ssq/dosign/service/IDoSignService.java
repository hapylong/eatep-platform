package com.iqb.eatep.ec.contract.ssq.dosign.service;

import java.util.Map;

import com.iqb.eatep.ec.contract.bizretbean.SubmitEcRetBean;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 完成签署流程服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IDoSignService {
    
    /**
     * 
     * Description: 提交电子合同
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午1:53:32
     */
    public SubmitEcRetBean submitEc(Map<String, Object> reqObjs) throws IqbException;

}
