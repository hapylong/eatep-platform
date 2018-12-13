package com.iqb.eatep.ec.contract.ssq.template.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.eatep.ec.contract.ssq.bean.EcTemplateBean;
import com.iqb.eatep.ec.contract.ssq.template.dao.EcTemplateDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * 
 * Description: Ec模板biz服务
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
public class EcTemplateBiz extends BaseBiz{
    
    @Autowired
    private EcTemplateDao ecTemplateDao;
    
    /**
     * 
     * Description: 根据条件获取ec模板列表
     * @param
     * @return List<EcTemplateBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午2:37:08
     */
    public List<EcTemplateBean> getEcTemplateListByCons(Map<String, String> reqObjs){
        super.setDb(0, super.MASTER);
        return this.ecTemplateDao.getEcTemplateListByCons(reqObjs);
    }

    /**
     * 
     * Description: 通过id获取ec模板信息
     * @param
     * @return EcTemplateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:31:55
     */
    public EcTemplateBean getEcTemplateById(String id) {
        super.setDb(0, super.MASTER);
        return this.ecTemplateDao.getEcTemplateById(id);
    }

    /**
     * 
     * Description: 通过模板代码获取模板信息
     * @param
     * @return EcTemplateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:32:13
     */
    public EcTemplateBean getEcTemplateByTemplateCode(String templateCode) {
        super.setDb(0, super.MASTER);
        return this.ecTemplateDao.getEcTemplateByTemplateCode(templateCode);
    }

}
