package com.iqb.eatep.ec.log.notify.bean;

import com.iqb.eatep.ec.base.EcBaseEntity;

/**
 * 
 * Description: ec异步通知bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月9日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcNotifyBean extends EcBaseEntity {
    
    /** 回调地址  **/
    private String notifyUrl;
    
    /** 回调时间  **/
    private Integer notifyTime;
    
    /** 请求ID(唯一标识) **/
    private String reqId;
    
    /** 业务模板ID **/
    private String bizConfigId;
    
    /** 返回结果  **/
    private String notifyRes;
    
    /** 备注  **/
    private String remark;
    
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Integer getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Integer notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getBizConfigId() {
        return bizConfigId;
    }

    public void setBizConfigId(String bizConfigId) {
        this.bizConfigId = bizConfigId;
    }

    public String getNotifyRes() {
        return notifyRes;
    }

    public void setNotifyRes(String notifyRes) {
        this.notifyRes = notifyRes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
