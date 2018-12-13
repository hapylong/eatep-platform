package com.iqb.eatep.crm.depositrate.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.iqb.eatep.crm.base.CrmBaseBiz;
import com.iqb.eatep.crm.depositrate.bean.DepositRateBean;
import com.iqb.eatep.crm.depositrate.dao.CustomerDepositRateDao;

/**
 * 
 * Description: 客户保证金费率biz服务
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
@Component
public class CustomerDepositRateBiz extends CrmBaseBiz{

    /**
     * 注入dao服务
     */
    @Autowired
    private CustomerDepositRateDao customerDepositRateDao;
    
    /**
     * 
     * Description: 查询保证金费率列表
     * @param
     * @return List<CustomerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年10月31日 下午2:45:33
     */
    public List<DepositRateBean> queryCustomerDepositRateList(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        PageHelper.startPage(getPagePara(objs));
        return this.customerDepositRateDao.queryCustomerDepositRateList(objs);
    }

    /**
     * 
     * Description: 根据业务大类获取业务子类
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 上午11:30:19
     */
    public List<Map<String, Object>> getBusinessDetailListByBusinessClass(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.customerDepositRateDao.getBusinessDetailListByBusinessClass(objs);
    }

    /**
     * 
     * Description: 插入客户信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午2:58:08
     */
    public Integer insertCustomerDepositRate(Map m) {
        super.setDb(0, super.MASTER);
        return this.customerDepositRateDao.insertCustomerDepositRate(m);
    }

    /**
     * 
     * Description: 根据id获取客户渠道信息
     * @param
     * @return DepositRateBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:06:56
     */
    public DepositRateBean getCustomerDepositRateById(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.customerDepositRateDao.getCustomerDepositRateById(objs);
    }

    /**
     * 
     * Description: 根据id删除客户渠道信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:13:41
     */
    public Integer deleteCustomerDepositRateById(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return this.customerDepositRateDao.deleteCustomerDepositRateById(objs);
    }

    /**
     * 
     * Description: 根据id更新客户渠道信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:18:21
     */
    public Integer updateCustomerDepositRateById(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return this.customerDepositRateDao.updateCustomerDepositRateById(objs);
    }

    /**
     * 
     * Description: 查询渠道列表
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月1日 下午4:57:47
     */
    public List<Map<String, Object>> queryChannelListForSelect() {
        super.setDb(0, super.SLAVE);
        return this.customerDepositRateDao.queryChannelListForSelect();
    }

    /**
     * 
     * Description: 根据传入参数获取保证金信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月2日 下午1:22:35
     */
    public DepositRateBean getCustomerDepositRateInfo(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.customerDepositRateDao.getCustomerDepositRateInfo(objs);
    }

    /**
     * 
     * Description: 根据条件获取渠道费率信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月2日 下午2:50:22
     */
    public Object getCustomerDepositRateInfoByCons(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.customerDepositRateDao.getCustomerDepositRateInfoByCons(objs);
    }

}
