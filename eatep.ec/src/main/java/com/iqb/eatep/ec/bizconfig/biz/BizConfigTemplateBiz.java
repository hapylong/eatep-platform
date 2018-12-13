package com.iqb.eatep.ec.bizconfig.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.eatep.ec.bizconfig.bean.BizConfigTemplateBean;
import com.iqb.eatep.ec.bizconfig.dao.BizConfigTemplateDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * Description: 业务模板-合同模板业务接口
 * 
 * @author baiaypeng
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    baiaypeng       1.0        1.0 Version 
 * </pre>
 */
@Component
public class BizConfigTemplateBiz extends BaseBiz {

	@Autowired
    private BizConfigTemplateDao bizConfigTemplateDao;
	
	public int deleteByPrimaryKey(Integer id) {
		setDb(0, super.MASTER);
		return this.bizConfigTemplateDao.deleteByPrimaryKey(id);
	};

    public int insert(BizConfigTemplateBean record) {
    	setDb(0, super.MASTER);
    	return this.bizConfigTemplateDao.insert(record);
    };

    public BizConfigTemplateBean selectByPrimaryKey(Integer id) {
    	setDb(0, super.SLAVE);
    	return this.bizConfigTemplateDao.selectByPrimaryKey(id);
    };

    public int updateByPrimaryKeySelective(BizConfigTemplateBean record) {
    	setDb(0, super.MASTER);
    	return this.bizConfigTemplateDao.updateByPrimaryKeySelective(record);
    };

    public int updateByPrimaryKey(BizConfigTemplateBean record) {
    	setDb(0, super.MASTER);
    	return this.bizConfigTemplateDao.updateByPrimaryKey(record);
    };

    public List<Map<String, Object>> selectToListOfMap(BizConfigTemplateBean record) {
    	setDb(0, super.SLAVE);
    	return this.bizConfigTemplateDao.selectToListOfMap(record);
    };
}
