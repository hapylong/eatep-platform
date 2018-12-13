package com.iqb.eatep.ec.contract.ecinfo.bean;

import com.iqb.eatep.ec.base.EcBaseEntity;

/**
 * 
 * Description: ec电子合同签署人表
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月24日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcInfoSignerBean extends EcBaseEntity {
    
    private String bizId;//业务id
    private Integer bizType;//业务类型
    private String orgCode;//机构代码
    private Integer ecInfoId;//合同ID
    private String ecSignerCode;//签署方代码
    private String ecSignerName;//签署方名
    private String ecSignerType;//签署方类型（1.个人;2.企业）
    private Integer ecSignerCertType;//签署方证件类型(0-   居民身份证  E-  户口簿  F-  临时居民身份证)
    private String ecSignerCertNo;//签署方证件号
    private String ecSignerEmail;//邮箱
    private String ecSignerPhone;//手机号
    private String ecSignerAddress;//地址
    private String ecSignerProvince;//省份
    private String ecSignerCity;//城市
    private String ecSignType;//签署类型
    private String isApplyCertComplete;
    private String ecSignerCertPwd;
    private Integer ecSignerCertPeriod;
    private String socialCreditCode;//社会信用码
    private String bizRegNum;//工商注册号
    private String organizationCode;//组织机构代码
    private String taxRegNum;//税务登记号
    private String coordinateList;//签章位置信息
    private String tpSignRetInfo;//第三方合同上传返回信息
    private String tpSignRetCode;//第三方合同上传返回码
    private String tpSignRetContent;//第三方合同上传返回信息
    private String tpSignid;//第三方合同编号
    private String tpDocid;//第三方文档存储编号
    private String tpReturnUrl;//第三方签署返回指定URL
    private String tpUserAcc;//第三方用户唯一标识
    private String tpUserStatusSyn;//第三方用户同步签署状态(1已完成 0未完成)
    private String tpUserStatusAsyn;//第三方用户异步签署状态(1已完成 0未完成)
    private Integer signDeviceType;//1表示返回PC端签名页面，2表示返回移动端签名页面
    private String manualSignUrl;//手动签名地址
    private String ecTokenUrl;//合同回调地址
    private String ecSignerImgName;//图章名称

    private String businessLicenseNum;
    private String storeName;
    private String openBank;
    private String bankCardNum;
    
    private String serviceCall;
    
    private String emergencyContractInfo;
    private String emergencyContract;
    
    public String getBizId() {
        return bizId;
    }
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
    public Integer getBizType() {
        return bizType;
    }
    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public Integer getEcInfoId() {
        return ecInfoId;
    }
    public void setEcInfoId(Integer ecInfoId) {
        this.ecInfoId = ecInfoId;
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
    public String getEcSignType() {
        return ecSignType;
    }
    public void setEcSignType(String ecSignType) {
        this.ecSignType = ecSignType;
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
    public String getCoordinateList() {
        return coordinateList;
    }
    public void setCoordinateList(String coordinateList) {
        this.coordinateList = coordinateList;
    }
    public String getTpSignRetInfo() {
        return tpSignRetInfo;
    }
    public void setTpSignRetInfo(String tpSignRetInfo) {
        this.tpSignRetInfo = tpSignRetInfo;
    }
    public String getTpSignRetCode() {
        return tpSignRetCode;
    }
    public void setTpSignRetCode(String tpSignRetCode) {
        this.tpSignRetCode = tpSignRetCode;
    }
    public String getTpSignRetContent() {
        return tpSignRetContent;
    }
    public void setTpSignRetContent(String tpSignRetContent) {
        this.tpSignRetContent = tpSignRetContent;
    }
    public String getTpSignid() {
        return tpSignid;
    }
    public void setTpSignid(String tpSignid) {
        this.tpSignid = tpSignid;
    }
    public String getTpDocid() {
        return tpDocid;
    }
    public void setTpDocid(String tpDocid) {
        this.tpDocid = tpDocid;
    }
    public String getTpReturnUrl() {
        return tpReturnUrl;
    }
    public void setTpReturnUrl(String tpReturnUrl) {
        this.tpReturnUrl = tpReturnUrl;
    }
    public String getTpUserAcc() {
        return tpUserAcc;
    }
    public void setTpUserAcc(String tpUserAcc) {
        this.tpUserAcc = tpUserAcc;
    }
    public String getTpUserStatusSyn() {
        return tpUserStatusSyn;
    }
    public void setTpUserStatusSyn(String tpUserStatusSyn) {
        this.tpUserStatusSyn = tpUserStatusSyn;
    }
    public String getTpUserStatusAsyn() {
        return tpUserStatusAsyn;
    }
    public void setTpUserStatusAsyn(String tpUserStatusAsyn) {
        this.tpUserStatusAsyn = tpUserStatusAsyn;
    }
    public Integer getSignDeviceType() {
        return signDeviceType;
    }
    public void setSignDeviceType(Integer signDeviceType) {
        this.signDeviceType = signDeviceType;
    }
    public String getManualSignUrl() {
        return manualSignUrl;
    }
    public void setManualSignUrl(String manualSignUrl) {
        this.manualSignUrl = manualSignUrl;
    }
    public String getEcTokenUrl() {
        return ecTokenUrl;
    }
    public void setEcTokenUrl(String ecTokenUrl) {
        this.ecTokenUrl = ecTokenUrl;
    }
    public String getEcSignerImgName() {
        return ecSignerImgName;
    }
    public void setEcSignerImgName(String ecSignerImgName) {
        this.ecSignerImgName = ecSignerImgName;
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
    public String getServiceCall() {
        return serviceCall;
    }
    public void setServiceCall(String serviceCall) {
        this.serviceCall = serviceCall;
    }
    public String getEmergencyContractInfo() {
        return emergencyContractInfo;
    }
    public void setEmergencyContractInfo(String emergencyContractInfo) {
        this.emergencyContractInfo = emergencyContractInfo;
    }
    public String getEmergencyContract() {
        return emergencyContract;
    }
    public void setEmergencyContract(String emergencyContract) {
        this.emergencyContract = emergencyContract;
    }
    
}
