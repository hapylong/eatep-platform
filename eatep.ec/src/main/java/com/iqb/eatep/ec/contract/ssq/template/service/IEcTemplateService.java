package com.iqb.eatep.ec.contract.ssq.template.service;

import java.util.Map;

import com.iqb.eatep.ec.contract.bizretbean.GenerateContractRetBean;
import com.iqb.eatep.ec.contract.ssq.bean.EcTemplateBean;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: ec模板服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IEcTemplateService {
    
    /**
     * 
     * Description: 通过id获取ec模板
     * @param
     * @return EcTemplateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:26:01
     */
    public EcTemplateBean getEcTemplateById(String id) throws IqbException;

    /**
     * 
     * Description: 通过模板代码获取ec模板
     * @param
     * @return EcTemplateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:28:18
     */
    public EcTemplateBean getEcTemplateByTemplateCode(String templateCode) throws IqbException;
    
    /**
     * 
     * Description: 生成ec合同
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月24日 上午10:29:07
     */
    public GenerateContractRetBean generateEc(Map<String, Object> reqObjs) throws IqbException;
    
}
