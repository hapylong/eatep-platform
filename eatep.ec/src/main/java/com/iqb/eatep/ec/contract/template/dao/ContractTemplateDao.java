package com.iqb.eatep.ec.contract.template.dao;

import java.util.List;
import java.util.Map;

import com.iqb.eatep.ec.contract.template.bean.ContractTemplateBean;

/**
 * Description: 合同模板数据接口
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
public interface ContractTemplateDao {	
	int deleteByPrimaryKey(Integer id);

    int insert(ContractTemplateBean record);

    ContractTemplateBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContractTemplateBean record);

    int updateByPrimaryKey(ContractTemplateBean record);
    
    List<ContractTemplateBean> selectToListOfBean(ContractTemplateBean record);
    
    List<Map<String, Object>> selectToListOfMap(ContractTemplateBean record);
}