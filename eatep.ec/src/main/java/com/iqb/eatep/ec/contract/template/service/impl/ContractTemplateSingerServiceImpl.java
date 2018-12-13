package com.iqb.eatep.ec.contract.template.service.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateSignerBean;
import com.iqb.eatep.ec.contract.template.biz.ContractTemplateSignerBiz;
import com.iqb.eatep.ec.contract.template.service.ContractTemplateSingerService;
import com.iqb.eatep.ec.util.CommonUtil;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.sysmanage.dict.service.ISysDictService;

/**
 * Description: 合同模板服务接口实现类
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
@Service
public class ContractTemplateSingerServiceImpl implements ContractTemplateSingerService {
	
    private static final Logger logger = LoggerFactory.getLogger(ContractTemplateSingerServiceImpl.class);
    private static final String dictTypeCode = "EC_SIGNER_TYPE";
    
    @Autowired
    private ContractTemplateSignerBiz contractTemplateSignerBiz;
    
    @Autowired
    private ISysDictService sysDictService;
    
	@Override
	public int deleteByPrimaryKey(Integer id) throws IqbException {
		logger.info("合同模板-签署方删除参数：{}", JSONObject.toJSONString(id));
		if (CommonUtil.isEmpty(id)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return this.contractTemplateSignerBiz.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ContractTemplateSignerBean record) throws IqbException {
		logger.info("合同模板-签署方添加参数：{}", JSONObject.toJSONString(record));
		if (CommonUtil.isEmpty(record)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return this.contractTemplateSignerBiz.insert(record);
	}

	@Override
	public ContractTemplateSignerBean selectByPrimaryKey(Integer id)
			throws IqbException {
		logger.info("合同模板-签署方主键查询参数：{}", JSONObject.toJSONString(id));
		if (CommonUtil.isEmpty(id)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return this.contractTemplateSignerBiz.selectByPrimaryKey(id);
	}

	@Override
	public synchronized int updateByPrimaryKey(ContractTemplateSignerBean record)
			throws IqbException {
		logger.info("合同模板-签署方修改参数：{}", JSONObject.toJSONString(record));
		if (CommonUtil.isEmpty(record)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return this.contractTemplateSignerBiz.updateByPrimaryKeySelective(record);
	}

	@Override
	public PageInfo<ContractTemplateSignerBean> selectToListOfBean(
			ContractTemplateSignerBean record) throws IqbException {
		logger.info("合同模板-签署方分页查询参数：{}", JSONObject.toJSONString(record));
		if (CommonUtil.isEmpty(record)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return new PageInfo<ContractTemplateSignerBean> (this.contractTemplateSignerBiz.selectToListOfBean(record));
	}
	
	@Override
	public List<Map<String, Object>> selectSignerToListOfMap(JSONObject objs)
			throws IqbException {
		objs.put("dictTypeCode", dictTypeCode);
		return this.sysDictService.selectSysDictTypeToListOfMapFormRedis(objs);
	}
}
