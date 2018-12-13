package com.iqb.eatep.ec.contract.template.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateBean;
import com.iqb.eatep.ec.contract.template.dao.ContractTemplateDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * Description: 合同模板业务接口
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
public class ContractTemplateBiz extends BaseBiz {
	
	@Autowired
    private ContractTemplateDao contractTemplateDao;
	
	public int deleteByPrimaryKey(Integer id) {
		setDb(0, super.MASTER);
		return this.contractTemplateDao.deleteByPrimaryKey(id);
	};

	public int insert(ContractTemplateBean record) {
		setDb(0, super.MASTER);
		return this.contractTemplateDao.insert(record);
	};

    public ContractTemplateBean selectByPrimaryKey(Integer id) {
    	setDb(0, super.SLAVE);
		return this.contractTemplateDao.selectByPrimaryKey(id);
    };

    public int updateByPrimaryKeySelective(ContractTemplateBean record) {
    	setDb(0, super.MASTER);
		return this.contractTemplateDao.updateByPrimaryKeySelective(record);
    };

    public int updateByPrimaryKey(ContractTemplateBean record) {
    	setDb(0, super.MASTER);
		return this.contractTemplateDao.updateByPrimaryKey(record);
    };
    
    public List<ContractTemplateBean> selectToListOfBean(ContractTemplateBean record) {
    	setDb(0, super.SLAVE);
    	PageHelper.startPage(record);
		return this.contractTemplateDao.selectToListOfBean(record);
    };
    
    public List<Map<String, Object>> selectToListOfMap(ContractTemplateBean record) {
    	setDb(0, super.SLAVE);
		return this.contractTemplateDao.selectToListOfMap(record);
    };

}
