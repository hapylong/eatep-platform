package com.iqb.eatep.ec.contract.ssq.bean;


/**
 * 
 * Description: Ec模板bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcTemplateBean {
    private Integer id;// 物理主键
    private String ecTplCode;// 模板代码
    private String ecTplName;// 模板名称
    private String ecTplTheme;// 模板主题
    private String ecTplAbstract; // 模板摘要
    private Integer ecTplType;// 模板文件类型(1 本地文件)
    private Integer ecTplStorageForm;// 模板存储形式
    private String ecTplContentData;// 模板数据
    private byte[] ecTplContentDataBlob;// 模板数据
    private Integer ecTplEffectiveDays;// 模板有效天数    
    private String isSenderPartSign;// 平台是否参与签署(0 否 1 是)
    private Integer status;// 启用状态(0 否 1 是)
    private Integer updateTime;
    private Integer createTime;
    private String creater;
    private String updater;
    private String deleteFlag;// 删除状态(0 否 1 是)
    private String remark;// 备注
    private Integer version;// 版本    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEcTplCode() {
        return ecTplCode;
    }

    public void setEcTplCode(String ecTplCode) {
        this.ecTplCode = ecTplCode;
    }

    public String getEcTplName() {
        return ecTplName;
    }

    public void setEcTplName(String ecTplName) {
        this.ecTplName = ecTplName;
    }

    public String getEcTplTheme() {
        return ecTplTheme;
    }

    public void setEcTplTheme(String ecTplTheme) {
        this.ecTplTheme = ecTplTheme;
    }

    public String getEcTplAbstract() {
        return ecTplAbstract;
    }

    public void setEcTplAbstract(String ecTplAbstract) {
        this.ecTplAbstract = ecTplAbstract;
    }

    public Integer getEcTplType() {
        return ecTplType;
    }

    public void setEcTplType(Integer ecTplType) {
        this.ecTplType = ecTplType;
    }

    public Integer getEcTplStorageForm() {
        return ecTplStorageForm;
    }

    public void setEcTplStorageForm(Integer ecTplStorageForm) {
        this.ecTplStorageForm = ecTplStorageForm;
    }

    public String getEcTplContentData() {
        return ecTplContentData;
    }

    public void setEcTplContentData(String ecTplContentData) {
        this.ecTplContentData = ecTplContentData;
    }

    public Integer getEcTplEffectiveDays() {
        return ecTplEffectiveDays;
    }

    public void setEcTplEffectiveDays(Integer ecTplEffectiveDays) {
        this.ecTplEffectiveDays = ecTplEffectiveDays;
    }

    public String getIsSenderPartSign() {
        return isSenderPartSign;
    }

    public void setIsSenderPartSign(String isSenderPartSign) {
        this.isSenderPartSign = isSenderPartSign;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public byte[] getEcTplContentDataBlob() {
        return ecTplContentDataBlob;
    }

    public void setEcTplContentDataBlob(byte[] ecTplContentDataBlob) {
        this.ecTplContentDataBlob = ecTplContentDataBlob;
    }

}
