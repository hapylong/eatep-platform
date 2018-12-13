package com.iqb.eatep.crm.customer.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.iqb.eatep.crm.base.CrmBaseBiz;
import com.iqb.eatep.crm.base.CrmReturnInfo;
import com.iqb.eatep.crm.constant.CrmAttr.CrmDBDefaultAttr;
import com.iqb.eatep.crm.customer.bean.CustomerBean;
import com.iqb.eatep.crm.customer.bean.CustomerPushRecord;
import com.iqb.eatep.crm.customer.dao.CustomerDao;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.BeanUtil;
import com.iqb.etep.common.utils.DateTools;
import com.iqb.etep.common.utils.StringUtil;

/**
 * 
 * Description: 客户biz服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年9月19日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class CustomerBiz extends CrmBaseBiz {

    /**
     * 客户dao
     */
    @Autowired
    private CustomerDao customerDao;

    /**
     * 
     * Description: 保存来自etep推送的信息
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月21日 下午3:05:12
     */
    public void saveCustomerServiceFromEtep(Map m) throws IqbException {
        CustomerBean customerBean = BeanUtil.mapToBean(m, CustomerBean.class);
        /** 校验数据完整性 **/
        if (customerBean == null || StringUtil.isEmpty(customerBean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.ETEP_PUSH_01020002);
        }
        /** 判断客户信息是否已经存在 **/
        CustomerBean bean = this.getCustomerInfoByCustmerCode(m);
        if (bean != null && StringUtil.isNotEmpty(bean.getCustomerCode())) {
            this.updateCustomerInfoFromEtep(m);
            return;
        }

        /**
         * 设置默认信息
         */
        customerBean.setCreateTime(Integer.toString(DateTools.getCurrTime()));
        customerBean.setDeleteFlag(CrmDBDefaultAttr.CRM_DEFAULT_DELETE_FLAG);
        customerBean.setCustomerStatus(CrmDBDefaultAttr.CRM_DEFAULT_CUSTOMER_STATUS);

        super.setDb(0, super.MASTER);
        this.customerDao.saveCustomerServiceFromEtep(customerBean);
        this.customerDao.insertXFJRCustomerForEtep(customerBean);
    }

    /**
     * 
     * Description: 根据客户编码获取客户信息
     * 
     * @param
     * @return CustomerBean
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月21日 下午7:56:28
     */
    public CustomerBean getCustomerInfoByCustmerCode(Map m) throws IqbException {
        CustomerBean customerBean = BeanUtil.mapToBean(m, CustomerBean.class);
        /** 校验数据完整性 **/
        if (customerBean == null || StringUtil.isEmpty(customerBean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.COMMON_01010001);
        }
        super.setDb(0, super.SLAVE);
        return this.customerDao.getCustomerInfoByCustmerCode(customerBean.getCustomerCode());
    }

    /**
     * 
     * Description: 根据客户编码简称获取客户信息
     * 
     * @param
     * @return CustomerBean
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月21日 下午7:56:28
     */
    public CustomerBean getCustomerInfoByCustmerShortNameCode(Map m) throws IqbException {
        CustomerBean customerBean = BeanUtil.mapToBean(m, CustomerBean.class);
        /** 校验数据完整性 **/
        if (customerBean == null || StringUtil.isEmpty(customerBean.getCustomerShortNameCode())) {
            throw new IqbException(CrmReturnInfo.COMMON_01010001);
        }
        super.setDb(0, super.SLAVE);
        return this.customerDao.getCustomerInfoByCustmerShortNameCode(customerBean.getCustomerShortNameCode());
    }

    /**
     * 
     * Description: 插入客户推送记录
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年9月21日 下午8:45:59
     */
    public synchronized void insertCustomerPushRecord(CustomerPushRecord customerPushRecord) {
        super.setDb(0, super.MASTER);
        this.customerDao.insertCustomerPushRecord(customerPushRecord);
    }

    /**
     * 
     * Description: 更新来自etep的客户信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年9月22日 下午3:18:16
     */
    public synchronized void updateCustomerInfoFromEtep(Map m) {
        CustomerBean customerBean = BeanUtil.mapToBean(m, CustomerBean.class);
        super.setDb(0, super.MASTER);
        this.customerDao.updateCustomerInfoFromEtep(customerBean);
    }

    /**
     * 
     * Description: 查询客户信息列表
     * 
     * @param
     * @return List<CustomerBean>
     * @throws
     * @Author wangxinbang Create Date: 2016年9月22日 下午3:58:51
     */
    public List<CustomerBean> queryCustomerList(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        PageHelper.startPage(getPagePara(objs));
        return this.customerDao.queryCustomerList(objs);
    }

    /**
     * 
     * Description: 根据客户编号删除客户信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年9月22日 下午4:20:11
     */
    public void deleteCustomerInfo(String customerCode) {
        super.setDb(0, super.MASTER);
        this.customerDao.deleteCustomerInfo(customerCode);
        this.customerDao.deleteCustomerEnterpriseInfo(customerCode);
        this.customerDao.deleteCustomerXFJRInfo(customerCode);
    }

    /**
     * 
     * Description: 新增客户信息
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月23日 下午2:12:02
     */
    public void insertCustomerBaseInfo(Map m) throws IqbException {

        CustomerBean customerBean = BeanUtil.mapToBean(m, CustomerBean.class);
        /** 校验数据完整性 **/
        if (customerBean == null || StringUtil.isEmpty(customerBean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.ETEP_PUSH_01020002);
        }
        /** 判断客户信息是否已经存在 **/
        CustomerBean bean = this.getCustomerInfoByCustmerCode(m);
        if (bean != null && StringUtil.isNotEmpty(bean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040004);
        }
        /** 判断客户简称编码是否已经存在 **/
        CustomerBean Sbean = this.getCustomerInfoByCustmerShortNameCode(m);
        if (Sbean != null && StringUtil.isNotEmpty(Sbean.getCustomerShortNameCode())) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040006);
        }

        /**
         * 设置默认信息
         */
        customerBean.setCreateTime(Integer.toString(DateTools.getCurrTime()));
        customerBean.setDeleteFlag(CrmDBDefaultAttr.CRM_DEFAULT_DELETE_FLAG);
        customerBean.setCustomerStatus(CrmDBDefaultAttr.CRM_DEFAULT_CUSTOMER_STATUS);

        super.setDb(0, super.MASTER);
        this.customerDao.insertCustomerBaseInfo(customerBean);
    }

    /**
     * 
     * Description: 更新客户信息
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月23日 下午2:25:23
     */
    public void updateCustomerBaseInfo(JSONObject objs) throws IqbException {
        CustomerBean customerBean = BeanUtil.mapToBean(objs, CustomerBean.class);
        CustomerBean Sbean = this.getCustomerInfoByCustmerShortNameCode(objs);
        if (Sbean != null && !customerBean.getCustomerCode().equals(Sbean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040006);
        }
        super.setDb(0, super.MASTER);
        this.customerDao.updateCustomerBaseInfo(customerBean);
        //this.customerDao.updateCustomerXFJRInfo(customerBean);
        this.customerDao.updateCustomerEnterpriseInfo(customerBean);
    }

    /**
     * 
     * Description: 插入客户企业信息
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月25日 下午1:42:53
     */
    public void insertCustomerEnterpriseInfo(Map m) throws IqbException {

        CustomerBean customerBean = BeanUtil.mapToBean(m, CustomerBean.class);
        /** 校验数据完整性 **/
        if (customerBean == null || StringUtil.isEmpty(customerBean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040002);
        }
        /** 删除客户企业信息 **/
        this.deleteCustomerEnterpriseInfo(customerBean);

        super.setDb(0, super.MASTER);
        this.customerDao.insertCustomerEnterpriseInfo(customerBean);
    }

    /**
     * 
     * Description: 删除客户企业相关信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年9月25日 下午1:47:54
     */
    private Integer deleteCustomerEnterpriseInfo(CustomerBean customerBean) {
        super.setDb(0, super.MASTER);
        return this.customerDao.deleteCustomerEnterpriseInfo(customerBean.getCustomerCode());
    }

    /**
     * 
     * Description: 删除消费金融端客户相关信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年9月25日 下午1:47:54
     */
    private Integer deleteCustomerXFJRInfo(CustomerBean customerBean) {
        super.setDb(0, super.MASTER);
        return this.customerDao.deleteCustomerXFJRInfo(customerBean.getCustomerCode());
    }

    /**
     * 
     * Description: 插入消费金融相关信息
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月25日 下午1:43:40
     */
    public void insertCustomerXFJRInfo(Map m) throws IqbException {

        CustomerBean customerBean = BeanUtil.mapToBean(m, CustomerBean.class);
        /** 校验数据完整性 **/
        if (customerBean == null || StringUtil.isEmpty(customerBean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040002);
        }
        /** 删除客户企业信息 **/
        this.deleteCustomerXFJRInfo(customerBean);

        super.setDb(0, super.MASTER);
        this.customerDao.insertCustomerXFJRInfo(customerBean);
    }

    /**
     * 
     * Description: 根据客户编码更新推送时间
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年9月26日 下午3:14:56
     */
    public Integer updatePushTimeByCustmerCode(CustomerBean customerBean) throws IqbException {
        /** 校验数据完整性 **/
        if (customerBean == null || StringUtil.isEmpty(customerBean.getCustomerCode())) {
            throw new IqbException(CrmReturnInfo.CRM_CUSTOMER_01040002);
        }
        customerBean.setPushTime(DateTools.getCurrTime() + "");
        super.setDb(0, super.MASTER);
        return this.customerDao.updatePushTimeByCustmerCode(customerBean);
    }

    public List<CustomerBean> getCustomerInfoByCustomerType(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.customerDao.queryCustomerList(objs);
    }

    /**
     * 
     * Description: 上传图片信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年10月8日 下午2:36:18
     */
    public Integer uploadCustomerImg(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return this.customerDao.uploadCustomerImg(objs);
    }

    /**
     * 
     * Description: 删除图片信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年10月8日 下午2:36:18
     */
    public Integer deleteCustomerImg(String id) {
        super.setDb(0, super.MASTER);
        return this.customerDao.deleteCustomerImg(id);
    }

    /**
     * 
     * Description: 上传公司印章
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年10月9日 上午9:59:22
     */
    public Integer uploadCustomerComponyImg(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return this.customerDao.uploadCustomerComponyImg(objs);
    }

    /**
     * 
     * Description: 上传法人印章
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年10月9日 上午10:00:03
     */
    public Integer uploadCustomerCorporateImg(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return this.customerDao.uploadCustomerCorporateImg(objs);
    }

    /**
     * 
     * Description: 删除公司印章
     * 
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang Create Date: 2016年10月9日 上午10:01:21
     */
    public Integer deleteCustomerComponyImg(String customerCode) {
        super.setDb(0, super.MASTER);
        return this.customerDao.deleteCustomerComponyImg(customerCode);
    }

    /**
     * 
     * Description: 删除法人印章
     * 
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang Create Date: 2016年10月9日 上午10:01:31
     */
    public Integer deleteCustomerCorporateImg(String customerCode) {
        super.setDb(0, super.MASTER);
        return this.customerDao.deleteCustomerCorporateImg(customerCode);
    }

    /**
     * 
     * Description: 根据客户编码查询门店信息
     * 
     * @param
     * @return CustomerBean
     * @throws
     * @Author wangxinbang Create Date: 2016年10月11日 下午2:18:01
     */
    public CustomerBean getCustomerStoreInfoByCode(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.customerDao.getCustomerStoreInfoByCode(objs);
    }

    /**
     * 
     * Description: 获取市下拉框集合
     * 
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author wangxinbang Create Date: 2016年10月14日 下午12:14:37
     */
    public List<Map<String, Object>> getCustomerCityListByProvince(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.customerDao.getCustomerCityListByProvince(objs);
    }

    /**
     * 
     * Description: 根据机构id获取渠道列表
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年11月9日 下午4:13:10
     */
    public List<Map<String, Object>> getChannelListByOrgCode(Map<String, String> map) {
        super.setDb(0, super.SLAVE);
        return this.customerDao.getChannelListByOrgCode(map);
    }



}
