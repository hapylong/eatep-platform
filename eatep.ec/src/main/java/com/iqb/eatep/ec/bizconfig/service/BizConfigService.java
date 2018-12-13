package com.iqb.eatep.ec.bizconfig.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.bizconfig.bean.BizConfigBean;
import com.iqb.etep.common.exception.IqbException;

/**
 * Description: 业务模板服务接口
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
public interface BizConfigService {

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
    int insert(BizConfigBean record) throws IqbException;

    /**
     * 根据主键查询
     * 
     * @return
     * @param id(Y)
     * @throws IqbException
     * */
    BizConfigBean selectByPrimaryKey(Integer id) throws IqbException;

    /**
     * 修改
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * */
    int updateByPrimaryKey(BizConfigBean record) throws IqbException;

    /**
     * 根据条件查询(适用于分页)
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * */
    PageInfo<BizConfigBean> selectToListOfBean(BizConfigBean record) throws IqbException;

    /**
     * 根据条件查询(对外接口)
     * 
     * @return
     * @param map(N)
     * @throws IqbException
     * */
    List<BizConfigBean> selectToListOfBeanByParameterMap(Map<String, Object> map) throws IqbException;
    
    /**
     * 根据条件查询(对外接口)
     * 
     * @return
     * @param map(N)
     * @throws IqbException
     * */
    List<BizConfigBean> selectToListOfUnionBeanByParameterMap(Map<String, Object> map) throws IqbException;
    
    /**
     * 查询机构
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * */
    List<Map<String, Object>> selectOrgToListOfMap(JSONObject objs) throws IqbException;
    
    /**
     * 查询业务类型
     * 
     * @return
     * @param record(Y)
     * @throws IqbException
     * */
    List<Map<String, Object>> selectBizToListOfMap(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 判断是否需要签署电子合同
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月24日 上午11:04:57
     */
    public Object judgeSignContract(JSONObject objs) throws IqbException;
    
}
