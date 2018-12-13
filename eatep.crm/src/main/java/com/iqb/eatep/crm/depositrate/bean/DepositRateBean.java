package com.iqb.eatep.crm.depositrate.bean;

/**
 * 
 * Description: 保证金费率bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年10月31日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class DepositRateBean{
    
    /**
     * 主键ID
     */
    private String id;
    
    /**
     * 客户编码
     */
    private String customerCode;
    
    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 业务大类
     */
    private String businessClass;
    
    /**
     * 业务详细
     */
    private String businessDetail;
    
    /**
     * 保证金费率
     */
    private String depositRate;
    
    /**
     * 备注
     */
    private String remark;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBusinessClass() {
        return businessClass;
    }
    
    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }
    
    public String getBusinessDetail() {
        return businessDetail;
    }
    
    public void setBusinessDetail(String businessDetail) {
        this.businessDetail = businessDetail;
    }
    
    public String getDepositRate() {
        return depositRate;
    }
    
    public void setDepositRate(String depositRate) {
        this.depositRate = depositRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
