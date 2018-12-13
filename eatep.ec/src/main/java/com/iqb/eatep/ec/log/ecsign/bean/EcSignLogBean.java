package com.iqb.eatep.ec.log.ecsign.bean;

import com.iqb.eatep.ec.base.EcBaseEntity;

/**
 * 
 * Description: 电子合同签署日志bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcSignLogBean extends EcBaseEntity {
    
    /** 签署方ID **/
    private String factorId;
    
    /** 业务模板ID **/
    private String bizConfigId;
    
    /** 合同模板ID **/
    private String templateId;
    
    /** 签署时间  **/
    private Integer signTime;
    
    /** 合同状态  **/
    private String ecStatus;
    
    /** 备注  **/
    private String remark;

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public String getBizConfigId() {
        return bizConfigId;
    }

    public void setBizConfigId(String bizConfigId) {
        this.bizConfigId = bizConfigId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getSignTime() {
        return signTime;
    }

    public void setSignTime(Integer signTime) {
        this.signTime = signTime;
    }

    public String getEcStatus() {
        return ecStatus;
    }

    public void setEcStatus(String ecStatus) {
        this.ecStatus = ecStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
