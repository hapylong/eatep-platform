package com.iqb.eatep.crm.depositrate.dao;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.crm.depositrate.bean.DepositRateBean;

/**
 * 
 * Description: 数据dao层
 * 
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
public interface CustomerDepositRateDao{

    /**
     * 
     * Description: 查询渠道保证金列表
     * @param
     * @return List<DepositRateBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年10月31日 下午2:57:20
     */
    public List<DepositRateBean> queryCustomerDepositRateList(JSONObject objs);

    /**
     * 
     * Description: 根据业务大类获取业务子类
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 上午11:35:40
     */
    public List<Map<String, Object>> getBusinessDetailListByBusinessClass(JSONObject objs);

    /**
     * 
     * Description: 插入客户信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午2:59:43
     */
    public Integer insertCustomerDepositRate(Map m);

    /**
     * 
     * Description: 根据id获取客户渠道信息
     * @param
     * @return DepositRateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:07:36
     */
    public DepositRateBean getCustomerDepositRateById(JSONObject objs);

    /**
     * 
     * Description: 根据id删除客户渠道信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:14:17
     */
    public Integer deleteCustomerDepositRateById(JSONObject objs);

    /**
     * 
     * Description: 根据id更新客户渠道信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:18:47
     */
    public Integer updateCustomerDepositRateById(JSONObject objs);

    /**
     * 
     * Description: 查询渠道列表
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:58:42
     */
    public List<Map<String, Object>> queryChannelListForSelect();

    /**
     * 
     * Description: 根据传入参数获取保证金信息
     * @param
     * @return DepositRateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月2日 下午1:23:31
     */
    public DepositRateBean getCustomerDepositRateInfo(JSONObject objs);

    /**
     * 
     * Description: 根据条件获取渠道费率信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月2日 下午2:50:54
     */
    public Object getCustomerDepositRateInfoByCons(JSONObject objs);
    
}
