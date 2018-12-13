package com.iqb.eatep.ec.bizconfig.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.bizconfig.bean.BizConfigTemplateBean;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateBean;
import com.iqb.etep.common.exception.IqbException;


/**
 * Description: 业务模板-合同模板服务接口
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
public interface BizConfigTemplateService {

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
    int insert(BizConfigTemplateBean record) throws IqbException;

    /**
     * 根据主键查询
     * 
     * @return
     * @param id(Y)
     * @throws IqbException
     * */
    BizConfigTemplateBean selectByPrimaryKey(Integer id) throws IqbException;

    /**
     * 修改
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * */
    int updateByPrimaryKey(BizConfigTemplateBean record) throws IqbException;
    
    /**
     * 根据条件查询(适用于分页)
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * */
    PageInfo<Map<String, Object>> selectToListOfMap(BizConfigTemplateBean record) throws IqbException;
    
    /**
     * 查询合同模板
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * @thorws Exception
     * */
    List<Map<String, Object>> selectContractTemplateToListOfMap(ContractTemplateBean record) throws IqbException, Exception;
}
