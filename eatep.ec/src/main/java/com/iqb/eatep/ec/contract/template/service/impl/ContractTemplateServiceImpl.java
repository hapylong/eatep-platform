package com.iqb.eatep.ec.contract.template.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateBean;
import com.iqb.eatep.ec.contract.template.biz.ContractTemplateBiz;
import com.iqb.eatep.ec.contract.template.service.ContractTemplateService;
import com.iqb.eatep.ec.util.CommonUtil;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.Attr.CommonConst;
import com.iqb.etep.common.utils.SysUserSession;
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
public class ContractTemplateServiceImpl implements ContractTemplateService {
	
    private static final Logger logger = LoggerFactory.getLogger(ContractTemplateServiceImpl.class);
    private static final String dictTypeCode = "EC_TEMPLATE_TYPE";
    
    @Autowired
    private ContractTemplateBiz contractTemplateBiz;
    
    @Autowired
    private ISysDictService sysDictService;
    
    @Autowired
    private SysUserSession session;

	@Override
	public int deleteByPrimaryKey(Integer id) throws IqbException {
		logger.info("合同模板删除参数：{}", JSONObject.toJSONString(id));
		if (CommonUtil.isEmpty(id)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return this.contractTemplateBiz.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ContractTemplateBean record) throws IqbException {
		logger.info("合同模板添加参数：{}", JSONObject.toJSONString(record));
		if (CommonUtil.isEmpty(record)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		
		long now_second = System.currentTimeMillis() / 1000;
        record.setCreateTime((int)(now_second));
        record.setUpdateTime((int)(now_second));
        record.setCreater(session.getUserCode());
        record.setUpdater(session.getUserCode());
        record.setDeleteFlag("0");// 未删除状态
        record.setVersion(Integer.parseInt(CommonConst.Version)); // 版本
        
		return this.contractTemplateBiz.insert(record);
	}

	@Override
	public ContractTemplateBean selectByPrimaryKey(Integer id) throws IqbException {
		logger.info("合同模板主键查询参数：{}", JSONObject.toJSONString(id));
		if (CommonUtil.isEmpty(id)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return this.contractTemplateBiz.selectByPrimaryKey(id);
	}

	@Override
	public synchronized int updateByPrimaryKey(ContractTemplateBean record) throws IqbException {
		logger.info("合同模板修改参数：{}", JSONObject.toJSONString(record));
		if (CommonUtil.isEmpty(record)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		
		long now_second = System.currentTimeMillis() / 1000;
        record.setUpdateTime((int)(now_second));
        record.setUpdater(session.getUserCode());
        
		return this.contractTemplateBiz.updateByPrimaryKeySelective(record);
	}

	@Override
	public PageInfo<ContractTemplateBean> selectToListOfBean(ContractTemplateBean record) throws IqbException {
		logger.info("合同模板分页查询参数：{}", JSONObject.toJSONString(record));
		if (CommonUtil.isEmpty(record)) {
			throw new IqbException(EcReturnInfo.EC_COMMON_01000001); 
		}
		return new PageInfo<ContractTemplateBean> (this.contractTemplateBiz.selectToListOfBean(record));
	}
	
	public static ContractTemplateBean setEcTplContentDataBlob(ContractTemplateBean record, MultipartFile file) throws IOException {
		if (CommonUtil.isEmpty(record)) {
			return null;
		}
		if (CommonUtil.isEmpty(file)) {
			return record;
		}
		record.setEcTplStorageForm(1);// 数据库存储形式(字节流数组)
		record.setEcTplContentDataBlob(file.getBytes());
		return record;
	}

	@Override
	public List<Map<String, Object>> selectToListOfMap(
			ContractTemplateBean record) throws IqbException {
		logger.info("合同模板下拉查询参数：{}", JSONObject.toJSONString(record));
		if (CommonUtil.isEmpty(record)) {
			record = new ContractTemplateBean();
		}
		return this.contractTemplateBiz.selectToListOfMap(record);
	}

	@Override
	public List<Map<String, Object>> selectEcTypeToListOfMap(JSONObject objs)
			throws IqbException {
		objs.put("dictTypeCode", dictTypeCode);
		return this.sysDictService.selectSysDictTypeToListOfMapFormRedis(objs);
	}

}
