package com.iqb.eatep.ec.contract.template.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.eatep.ec.contract.template.bean.ContractTemplateSignerBean;
import com.iqb.eatep.ec.contract.template.dao.ContractTemplateSignerDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * Description: 合同模板-签署方业务接口
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
@Component
public class ContractTemplateSignerBiz extends BaseBiz {
	
	@Autowired
	private ContractTemplateSignerDao contractTemplateSignerDao;
	
	public int deleteByPrimaryKey(Integer id) {
		setDb(0, super.MASTER);
		return this.contractTemplateSignerDao.deleteByPrimaryKey(id);
	};

    public int insert(ContractTemplateSignerBean record) {
    	setDb(0, super.MASTER);
    	return this.contractTemplateSignerDao.insert(record);
    };

    public ContractTemplateSignerBean selectByPrimaryKey(Integer id) {
    	setDb(0, super.SLAVE);
    	return this.contractTemplateSignerDao.selectByPrimaryKey(id);
    };

    public int updateByPrimaryKeySelective(ContractTemplateSignerBean record) {
    	setDb(0, super.MASTER);
    	return this.contractTemplateSignerDao.updateByPrimaryKeySelective(record);
    };

    public int updateByPrimaryKey(ContractTemplateSignerBean record) {
    	setDb(0, super.MASTER);
    	return this.contractTemplateSignerDao.updateByPrimaryKey(record);
    };
    
    public List<ContractTemplateSignerBean> selectToListOfBean(ContractTemplateSignerBean record) {
    	setDb(0, super.SLAVE);
    	return this.contractTemplateSignerDao.selectToListOfBean(record);
    };

}
