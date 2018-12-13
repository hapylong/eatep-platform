package com.iqb.eatep.ec.bizconfig.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.bizconfig.bean.BizConfigTemplateBean;
import com.iqb.eatep.ec.bizconfig.biz.BizConfigTemplateBiz;
import com.iqb.eatep.ec.bizconfig.service.BizConfigTemplateService;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateBean;
import com.iqb.eatep.ec.contract.template.service.ContractTemplateService;
import com.iqb.eatep.ec.util.CommonUtil;
import com.iqb.etep.common.exception.IqbException;

/**
 * Description: 业务模板-合同模板服务接口实现类
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
@Service
public class BizConfigTemplateServiceImpl implements BizConfigTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(BizConfigTemplateServiceImpl.class);

    @Autowired
    private BizConfigTemplateBiz bizConfigTemplateBiz;
    
    @Autowired
    private ContractTemplateService contractTemplateServiceImpl;

	@Override
	public int deleteByPrimaryKey(Integer id) throws IqbException {
		logger.info("业务模板-合同模板删除参数：{}", JSONObject.toJSONString(id));
        if (CommonUtil.isEmpty(id)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.bizConfigTemplateBiz.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(BizConfigTemplateBean record) throws IqbException {
		logger.info("业务模板-合同模板添加参数：{}", JSONObject.toJSONString(record));
        if (CommonUtil.isEmpty(record)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.bizConfigTemplateBiz.insert(record);
	}

	@Override
	public BizConfigTemplateBean selectByPrimaryKey(Integer id) throws IqbException {
		logger.info("业务模板-合同模板主键查询参数：{}", JSONObject.toJSONString(id));
        if (CommonUtil.isEmpty(id)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
		return this.bizConfigTemplateBiz.selectByPrimaryKey(id);
	}

	@Override
	public synchronized int updateByPrimaryKey(BizConfigTemplateBean record) throws IqbException {
		 logger.info("业务模板-合同模板修改参数：{}", JSONObject.toJSONString(record));
        if (CommonUtil.isEmpty(record)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.bizConfigTemplateBiz.updateByPrimaryKeySelective(record);
	}

	@Override
	public PageInfo<Map<String, Object>> selectToListOfMap(
			BizConfigTemplateBean record) throws IqbException {
		logger.info("业务模板-合同模板分页查询参数：{}", JSONObject.toJSONString(record));
        if (CommonUtil.isEmpty(record)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return new PageInfo<Map<String, Object>> (this.bizConfigTemplateBiz.selectToListOfMap(record));
	}

	@Override
	public List<Map<String, Object>> selectContractTemplateToListOfMap(
			ContractTemplateBean record) throws IqbException, Exception {
		return this.contractTemplateServiceImpl.selectToListOfMap(record);
	}
}
