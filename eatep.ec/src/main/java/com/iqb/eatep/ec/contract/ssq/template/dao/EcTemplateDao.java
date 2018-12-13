package com.iqb.eatep.ec.contract.ssq.template.dao;

import java.util.List;
import java.util.Map;

import com.iqb.eatep.ec.contract.ssq.bean.EcTemplateBean;

/**
 * 
 * Description: ec模板dao
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface EcTemplateDao {

    /**
     * 
     * Description: 根据条件获取ec模板列表
     * @param
     * @return List<EcTemplateBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午2:40:52
     */
    public List<EcTemplateBean> getEcTemplateListByCons(Map<String, String> reqObjs);

    /**
     * 
     * Description: 通过id获取ec模板信息
     * @param
     * @return EcTemplateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:32:47
     */
    public EcTemplateBean getEcTemplateById(String id);

    /**
     * 
     * Description: 通过模板代码获取模板信息
     * @param
     * @return EcTemplateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:32:55
     */
    public EcTemplateBean getEcTemplateByTemplateCode(String templateCode);

}
