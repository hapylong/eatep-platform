package com.iqb.eatep.ec.contract.template.dao;

import java.util.List;

import com.iqb.eatep.ec.contract.template.bean.ContractTemplateSignerBean;

/**
 * Description: 合同模板-签署方数据接口
 * 
 * @author baiaypeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    baiaypeng       1.0        1.0 Version 
 * </pre>
 */
public interface ContractTemplateSignerDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ContractTemplateSignerBean record);

    ContractTemplateSignerBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContractTemplateSignerBean record);

    int updateByPrimaryKey(ContractTemplateSignerBean record);
    
    List<ContractTemplateSignerBean> selectToListOfBean(ContractTemplateSignerBean record);
}