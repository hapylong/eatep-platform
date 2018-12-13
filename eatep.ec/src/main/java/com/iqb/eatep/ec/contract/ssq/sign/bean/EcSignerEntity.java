package com.iqb.eatep.ec.contract.ssq.sign.bean;

import javax.persistence.Table;

/**
 * 
 * Description: ec模板签署方bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Table(name = "iqb_ec_signer")
public class EcSignerEntity {
    private Integer id;
    private String ecSignerCode;//手机号
    private String ecSignerName;//姓名
    private String ecSignerType;
    private Integer ecSignerCertType;//证件类型(0-居民身份证  E-户口簿  F-临时居民身份证)
    private String ecSignerCertNo;//证件号
    private String ecSignerEmail;//邮箱
    private String ecSignerPhone;
    private String ecSignerAddress;//地址
    private String ecSignerProvince;//省
    private String ecSignerCity;//市
    private String isApplyCertComplete;
    private String ecSignerCertPwd;
    private Integer ecSignerCertPeriod;
    private String isUploadSignImg;
    private Integer ecSignerImgType;
    private String ecSignerImgName;
    private String ecSignerImgData;
    private String ssqUid;
    private String isSender;
    private String socialCreditCode;
    private String bizRegNum;
    private String organizationCode;
    private String taxRegNum;
    private Integer ecSignerStatus;
    
    private String businessLicenseNum;
    private String storeName;
    private String openBank;
    private String bankCardNum;
    
    private String emergencyContractInfo;
    private String emergencyContract;
    
    private String serviceCall;
    
    private Integer createTime;
    private Integer updateTime;
    private String creater;
    private String updater;
    private String deleteFlag;
    private String remark;
    private Integer version;
    
    /********************** biz extention *************************/
    private Integer ecSignType;
    private Integer ecInfoId;
    private Integer ecSignNotifyUrl;
    
    private String isDefaultSignerImg;
    private String ecSignerPicName;
    private byte[] ecSignerImgDataBlob;
    private String  enterpriseLinkMan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterpriseLinkMan() {
        return enterpriseLinkMan;
    }

    public void setEnterpriseLinkMan(String enterpriseLinkMan) {
        this.enterpriseLinkMan = enterpriseLinkMan;
    }

    public String getEcSignerCode() {
        return ecSignerCode;
    }

    public void setEcSignerCode(String ecSignerCode) {
        this.ecSignerCode = ecSignerCode;
    }

    public String getEcSignerName() {
        return ecSignerName;
    }

    public void setEcSignerName(String ecSignerName) {
        this.ecSignerName = ecSignerName;
    }

    public String getEcSignerType() {
        return ecSignerType;
    }

    public void setEcSignerType(String ecSignerType) {
        this.ecSignerType = ecSignerType;
    }

    public Integer getEcSignerCertType() {
        return ecSignerCertType;
    }

    public void setEcSignerCertType(Integer ecSignerCertType) {
        this.ecSignerCertType = ecSignerCertType;
    }

    public String getEcSignerCertNo() {
        return ecSignerCertNo;
    }

    public void setEcSignerCertNo(String ecSignerCertNo) {
        this.ecSignerCertNo = ecSignerCertNo;
    }

    public String getEcSignerEmail() {
        return ecSignerEmail;
    }

    public void setEcSignerEmail(String ecSignerEmail) {
        this.ecSignerEmail = ecSignerEmail;
    }

    public String getEcSignerPhone() {
        return ecSignerPhone;
    }

    public void setEcSignerPhone(String ecSignerPhone) {
        this.ecSignerPhone = ecSignerPhone;
    }

    public String getEcSignerAddress() {
        return ecSignerAddress;
    }

    public void setEcSignerAddress(String ecSignerAddress) {
        this.ecSignerAddress = ecSignerAddress;
    }

    public String getEcSignerProvince() {
        return ecSignerProvince;
    }

    public void setEcSignerProvince(String ecSignerProvince) {
        this.ecSignerProvince = ecSignerProvince;
    }

    public String getEcSignerCity() {
        return ecSignerCity;
    }

    public void setEcSignerCity(String ecSignerCity) {
        this.ecSignerCity = ecSignerCity;
    }

    public String getIsApplyCertComplete() {
        return isApplyCertComplete;
    }

    public void setIsApplyCertComplete(String isApplyCertComplete) {
        this.isApplyCertComplete = isApplyCertComplete;
    }

    public String getEcSignerCertPwd() {
        return ecSignerCertPwd;
    }

    public void setEcSignerCertPwd(String ecSignerCertPwd) {
        this.ecSignerCertPwd = ecSignerCertPwd;
    }

    public Integer getEcSignerCertPeriod() {
        return ecSignerCertPeriod;
    }

    public void setEcSignerCertPeriod(Integer ecSignerCertPeriod) {
        this.ecSignerCertPeriod = ecSignerCertPeriod;
    }

    public String getIsUploadSignImg() {
        return isUploadSignImg;
    }

    public void setIsUploadSignImg(String isUploadSignImg) {
        this.isUploadSignImg = isUploadSignImg;
    }

    public Integer getEcSignerImgType() {
        return ecSignerImgType;
    }

    public void setEcSignerImgType(Integer ecSignerImgType) {
        this.ecSignerImgType = ecSignerImgType;
    }

    public String getEcSignerImgName() {
        return ecSignerImgName;
    }

    public void setEcSignerImgName(String ecSignerImgName) {
        this.ecSignerImgName = ecSignerImgName;
    }

    public String getEcSignerImgData() {
        return ecSignerImgData;
    }

    public void setEcSignerImgData(String ecSignerImgData) {
        this.ecSignerImgData = ecSignerImgData;
    }

    public String getSsqUid() {
        return ssqUid;
    }

    public void setSsqUid(String ssqUid) {
        this.ssqUid = ssqUid;
    }

    public String getIsSender() {
        return isSender;
    }

    public void setIsSender(String isSender) {
        this.isSender = isSender;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getBizRegNum() {
        return bizRegNum;
    }

    public void setBizRegNum(String bizRegNum) {
        this.bizRegNum = bizRegNum;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getTaxRegNum() {
        return taxRegNum;
    }

    public void setTaxRegNum(String taxRegNum) {
        this.taxRegNum = taxRegNum;
    }

    public Integer getEcSignerStatus() {
        return ecSignerStatus;
    }

    public void setEcSignerStatus(Integer ecSignerStatus) {
        this.ecSignerStatus = ecSignerStatus;
    }

    public String getBusinessLicenseNum() {
        return businessLicenseNum;
    }

    public void setBusinessLicenseNum(String businessLicenseNum) {
        this.businessLicenseNum = businessLicenseNum;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getEmergencyContractInfo() {
        return emergencyContractInfo;
    }

    public void setEmergencyContractInfo(String emergencyContractInfo) {
        this.emergencyContractInfo = emergencyContractInfo;
    }

    public String getServiceCall() {
        return serviceCall;
    }

    public void setServiceCall(String serviceCall) {
        this.serviceCall = serviceCall;
    }

    public String getEmergencyContract() {
        return emergencyContract;
    }

    public void setEmergencyContract(String emergencyContract) {
        this.emergencyContract = emergencyContract;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
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

    public Integer getEcSignType() {
        return ecSignType;
    }

    public void setEcSignType(Integer ecSignType) {
        this.ecSignType = ecSignType;
    }

    public Integer getEcInfoId() {
        return ecInfoId;
    }

    public void setEcInfoId(Integer ecInfoId) {
        this.ecInfoId = ecInfoId;
    }

    public Integer getEcSignNotifyUrl() {
        return ecSignNotifyUrl;
    }

    public void setEcSignNotifyUrl(Integer ecSignNotifyUrl) {
        this.ecSignNotifyUrl = ecSignNotifyUrl;
    }

    public String getIsDefaultSignerImg() {
        return isDefaultSignerImg;
    }

    public void setIsDefaultSignerImg(String isDefaultSignerImg) {
        this.isDefaultSignerImg = isDefaultSignerImg;
    }

    public String getEcSignerPicName() {
        return ecSignerPicName;
    }

    public void setEcSignerPicName(String ecSignerPicName) {
        this.ecSignerPicName = ecSignerPicName;
    }

    public byte[] getEcSignerImgDataBlob() {
        return ecSignerImgDataBlob;
    }

    public void setEcSignerImgDataBlob(byte[] ecSignerImgDataBlob) {
        this.ecSignerImgDataBlob = ecSignerImgDataBlob;
    }
    
}
