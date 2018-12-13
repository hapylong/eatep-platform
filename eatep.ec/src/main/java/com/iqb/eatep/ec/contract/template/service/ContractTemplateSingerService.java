package com.iqb.eatep.ec.contract.template.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateSignerBean;
import com.iqb.etep.common.exception.IqbException;

/**
 * Description: 合同模板服务接口
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
public interface ContractTemplateSingerService {
	
	/**
	 * 删除
	 * 
	 * @return
	 * @param id(Y)
	 * @throws IqbException
	 * */
	int deleteByPrimaryKey(Integer id) throws IqbException;

	/**
	 * 添加
	 * 
	 * @return
	 * @param record(Y)
	 * @throws IqbException
	 * */
    int insert(ContractTemplateSignerBean record) throws IqbException;

    /**
	 * 根据主键查询
	 * 
	 * @return
	 * @param id(Y)
	 * @throws IqbException
	 * */
    ContractTemplateSignerBean selectByPrimaryKey(Integer id) throws IqbException;

    /**
	 * 修改
	 * 
	 * @return
	 * @param record(Y)
	 * @throws IqbException
	 * */
    int updateByPrimaryKey(ContractTemplateSignerBean record) throws IqbException;
    
    /**
	 * 根据条件查询(适用于分页)
	 * 
	 * @return
	 * @param record(Y)
	 * @throws IqbException
	 * */
    PageInfo<ContractTemplateSignerBean> selectToListOfBean(ContractTemplateSignerBean record) throws IqbException;
    
    /**
     * 查询签署方类型
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * */
    List<Map<String, Object>> selectSignerToListOfMap(JSONObject objs) throws IqbException;
}
