package com.iqb.eatep.crm.depositrate.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 客户保证金费率服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年10月31日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("rawtypes")
public interface ICustomerDepositRateService{

    /**
     * 
     * Description: 查询保证金费率列表
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年10月31日 下午2:36:23
     */
    public PageInfo queryCustomerDepositRateList(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 根据业务大类获取业务子类
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 上午11:27:18
     */
    public List<Map<String, Object>> getBusinessDetailListByBusinessClass(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 插入客户信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午2:54:09
     */
    public void insertCustomerDepositRate(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 根据id获取客户渠道信息
     * @param
     * @return Map
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午3:56:54
     */
    public Object getCustomerDepositRateById(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 根据id删除客户渠道信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:11:37
     */
    public void deleteCustomerDepositRateById(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 根据id更新客户渠道信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:16:57
     */
    public void updateCustomerDepositRateById(JSONObject objs) throws IqbException;

    /**
     * 
     * Description:  查询渠道列表
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:54:41
     */
    public List<Map<String, Object>> queryChannelListForSelect() throws IqbException;

    /**
     * 
     * Description: 根据传入参数获取保证金信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月2日 上午11:56:48
     */
    public Object getCustomerDepositRateInfo(JSONObject objs) throws IqbException;
    
    

}
