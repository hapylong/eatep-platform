package com.iqb.eatep.crm.depositrate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.crm.base.CrmReturnInfo;
import com.iqb.eatep.crm.constant.CrmAttr.DepositRateAttr;
import com.iqb.eatep.crm.depositrate.bean.DepositRateBean;
import com.iqb.eatep.crm.depositrate.biz.CustomerDepositRateBiz;
import com.iqb.eatep.crm.depositrate.service.ICustomerDepositRateService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.BeanUtil;
import com.iqb.etep.common.utils.StringUtil;

/**
 * 
 * Description: 客户保证金费率服务实现类
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年10月31日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Service("customerDepositRateService")
public class CustomerDepositRateServiceImpl implements ICustomerDepositRateService{
    
    /**
     * 获取日志集合
     */
    private Logger logger = LoggerFactory.getLogger(CustomerDepositRateServiceImpl.class);
    
    /**
     * 客户保证金费率biz服务
     */
    @Autowired
    private CustomerDepositRateBiz customerDepositRateBiz;

    @Override
    public PageInfo queryCustomerDepositRateList(JSONObject objs) throws IqbException {
        return new PageInfo<DepositRateBean>(this.customerDepositRateBiz.queryCustomerDepositRateList(objs));
    }

    @Override
    public List<Map<String, Object>> getBusinessDetailListByBusinessClass(JSONObject objs) throws IqbException {
        /** 校验传递信息是否为空 **/
        if (CollectionUtils.isEmpty(objs)) {
            logger.error("根据业务大类获取业务子类：传入查询信息为空");
            return new ArrayList<Map<String, Object>>();
        }
        if (StringUtil.isEmpty(objs.getString("businessClass"))) {
            logger.error("根据业务大类获取业务子类：传入查询信息客户编码为空");
            return new ArrayList<Map<String, Object>>();
        }
        List<Map<String, Object>> businessDetailList = this.customerDepositRateBiz.getBusinessDetailListByBusinessClass(objs);
        return businessDetailList;
    }

    @Transactional
    @Override
    public void insertCustomerDepositRate(JSONObject objs) throws IqbException {
        /** 校验传递信息是否为空 **/
        if (CollectionUtils.isEmpty(objs)) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040001);
        }
        Object obj = this.customerDepositRateBiz.getCustomerDepositRateInfoByCons(objs);
        if(obj != null){
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_CHANNEL_01060002);
        }
        Map m = this.clearEmptyFromMap(objs);
        this.customerDepositRateBiz.insertCustomerDepositRate(m);
    }
    

    /**
     * 
     * Description: 清空map中的空值
     * 
     * @param
     * @return Map
     * @throws
     * @Author wangxinbang Create Date: 2016年9月25日 下午3:13:39
     */
    private Map clearEmptyFromMap(Map m) {
        Map retMap = new HashMap();
        Set<String> keySet = m.keySet();
        for (String key : keySet) {
            if (m.get(key) != null && StringUtil.isNotEmpty((String) m.get(key))) {
                retMap.put(key, m.get(key));
            }
        }
        return retMap;
    }

    @Override
    public Object getCustomerDepositRateById(JSONObject objs) throws IqbException {
        /** 校验传递信息是否为空 **/
        if (CollectionUtils.isEmpty(objs)) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040003);
        }
        if (StringUtil.isEmpty(objs.getString("id"))) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040003);
        }
        DepositRateBean bean = this.customerDepositRateBiz.getCustomerDepositRateById(objs);
        if (bean == null) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040003);
        }
        return BeanUtil.entity2map(bean);
    }

    @Override
    public void deleteCustomerDepositRateById(JSONObject objs) throws IqbException {
        /** 校验传递信息是否为空 **/
        if (CollectionUtils.isEmpty(objs)) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040003);
        }
        if (StringUtil.isEmpty(objs.getString("id"))) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040003);
        }
        this.customerDepositRateBiz.deleteCustomerDepositRateById(objs);
    }

    @Override
    public void updateCustomerDepositRateById(JSONObject objs) throws IqbException {
        /** 校验传递信息是否为空 **/
        if (CollectionUtils.isEmpty(objs)) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040003);
        }
        if (StringUtil.isEmpty(objs.getString("id"))) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040003);
        }
        this.customerDepositRateBiz.updateCustomerDepositRateById(objs);
    }

    @Override
    public List<Map<String, Object>> queryChannelListForSelect() throws IqbException {
        List<Map<String, Object>> businessDetailList = this.customerDepositRateBiz.queryChannelListForSelect();
        return businessDetailList;
    }

    @Override
    public Object getCustomerDepositRateInfo(JSONObject objs) throws IqbException {
        DepositRateBean depositRateBean = null;
        if(!CollectionUtils.isEmpty(objs) && StringUtil.isEmpty(objs.getString("businessClass")) && StringUtil.isEmpty(objs.getString("businessDetail"))){
            depositRateBean = this.customerDepositRateBiz.getCustomerDepositRateInfo(new JSONObject());
        }else{
            depositRateBean = this.customerDepositRateBiz.getCustomerDepositRateInfo(objs);
        }
        if(depositRateBean != null && StringUtil.isNotEmpty(depositRateBean.getDepositRate())){
            return depositRateBean.getDepositRate();
        }
        //查询
        if(depositRateBean == null && !StringUtil.isEmpty(objs.getString("customerCode"))){
            objs.remove("businessClass");
            objs.remove("businessDetail");
            depositRateBean = this.customerDepositRateBiz.getCustomerDepositRateInfo(objs);
            return depositRateBean.getDepositRate();
        }
        return DepositRateAttr.INTER_DEPOSIT_QUERY_NONE;
    }

}
