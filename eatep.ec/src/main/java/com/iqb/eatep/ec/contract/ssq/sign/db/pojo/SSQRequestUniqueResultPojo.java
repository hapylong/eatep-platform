package com.iqb.eatep.ec.contract.ssq.sign.db.pojo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.iqb.eatep.ec.base.EcAttr.DictConst;


public class SSQRequestUniqueResultPojo {
    private Map<String, Object> registerRequestMap = new HashMap<String, Object>();
    private Map<String, Object> imgUploadRequestMap = new HashMap<String, Object>();
    private Map<String, Object> personApplyCertificateMap = new HashMap<String, Object>();
    private Map<String, Object> orgApplyCertificateMap    = new HashMap<String, Object>();
    /** 注册 **/
    private String             ecSignerType;
    private String              ecSignerEmail;
    private String              ecSignerPhone;
    private String              ecSignerName;
    
    /** 上传签章 **/
    private byte[]              ecSignerImgDataBlob;
    private String              ecSignerImgName;
    private Boolean             overwrite;

    /** 申请证书(适用于个人) **/
    private String              ecSignerCertPwd;
    private Integer             ecSignerCertType;
    private String              ecSignerCertNo;
    private String              ecSignerProvince;
    private String              ecSignerCity;
    private String              ecSignerAddress;

    /** 申请证书(适用于企业) **/
    private String              bizRegNum;
    private String              organizationCode;
    private String              taxRegNum;
    private String              linkMan;
    private String              storeName;

    public Map<String, Object> getPersonApplyCertificateMap() {
        return personApplyCertificateMap;
    }

    public Map<String, Object> getOrgApplyCertificateMap() {
        return orgApplyCertificateMap;
    }

    public String getBizRegNum() {
        return bizRegNum;
    }

    public void setBizRegNum(String bizRegNum) {
        orgApplyCertificateMap.put("bizRegNum", bizRegNum);
        this.bizRegNum = bizRegNum;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        orgApplyCertificateMap.put("organizationCode", organizationCode);
        this.organizationCode = organizationCode;
    }

    public String getTaxRegNum() {
        return taxRegNum;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        orgApplyCertificateMap.put("linkMan", linkMan);
        this.linkMan = linkMan;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        registerRequestMap.put("storeName", storeName);
        imgUploadRequestMap.put("storeName", storeName);
        orgApplyCertificateMap.put("storeName", storeName);
        personApplyCertificateMap.put("storeName", storeName);
        this.storeName = storeName;
    }

    public void setTaxRegNum(String taxRegNum) {
        orgApplyCertificateMap.put("taxRegNum", taxRegNum);
        this.taxRegNum = taxRegNum;
    }

    public String getEcSignerCertPwd() {
        return ecSignerCertPwd;
    }

    public void setEcSignerCertPwd(String ecSignerCertPwd) {
        personApplyCertificateMap.put("ecSignerCertPwd", ecSignerCertPwd);
        orgApplyCertificateMap.put("ecSignerCertPwd", ecSignerCertPwd);
        this.ecSignerCertPwd = ecSignerCertPwd;
    }

    public Integer getEcSignerCertType() {
        return ecSignerCertType;
    }

    public void setEcSignerCertType(Integer ecSignerCertType) {
        personApplyCertificateMap.put("ecSignerCertType", ecSignerCertType);
        this.ecSignerCertType = ecSignerCertType;
    }

    public String getEcSignerCertNo() {
        return ecSignerCertNo;
    }

    public void setEcSignerCertNo(String ecSignerCertNo) {
        personApplyCertificateMap.put("ecSignerCertNo", ecSignerCertNo);
        orgApplyCertificateMap.put("ecSignerCertNo", ecSignerCertNo);
        this.ecSignerCertNo = ecSignerCertNo;
    }

    public String getEcSignerProvince() {
        return ecSignerProvince;
    }

    public void setEcSignerProvince(String ecSignerProvince) {
        personApplyCertificateMap.put("ecSignerProvince", ecSignerProvince);
        orgApplyCertificateMap.put("ecSignerProvince", ecSignerProvince);
        this.ecSignerProvince = ecSignerProvince;
    }

    public String getEcSignerCity() {
        return ecSignerCity;
    }

    public void setEcSignerCity(String ecSignerCity) {
        personApplyCertificateMap.put("ecSignerCity", ecSignerCity);
        orgApplyCertificateMap.put("ecSignerCity", ecSignerCity);
        this.ecSignerCity = ecSignerCity;
    }

    public String getEcSignerAddress() {
        return ecSignerAddress;
    }

    public void setEcSignerAddress(String ecSignerAddress) {
        personApplyCertificateMap.put("ecSignerAddress", ecSignerAddress);
        orgApplyCertificateMap.put("ecSignerAddress", ecSignerAddress);
        this.ecSignerAddress = ecSignerAddress;
    }

    public Map<String, Object> getPersonApplyCertificate() {
        return personApplyCertificateMap;
    }

    public String getEcSignerType() {
        return ecSignerType;
    }

    public void setEcSignerType(String ecSignerType) {
        registerRequestMap.put("ecSignerType", this.getEcSignType(ecSignerType));
        imgUploadRequestMap.put("ecSignerType", this.getEcSignType(ecSignerType));
        orgApplyCertificateMap.put("ecSignerType", this.getEcSignType(ecSignerType));
        this.ecSignerType = ecSignerType;
    }
    
    private Integer getEcSignType(String ecSignerType){
        if(StringUtils.contains(ecSignerType, DictConst.EC_SIGNER_TYPE_3)){
            return 2;
        }
        if(StringUtils.contains(ecSignerType, DictConst.EC_SIGNER_TYPE_2)){
            return 2;
        }
        return 1;
    }

    public String getEcSignerEmail() {
        return ecSignerEmail;
    }

    public void setEcSignerEmail(String ecSignerEmail) {
        registerRequestMap.put("ecSignerEmail", ecSignerEmail);
        imgUploadRequestMap.put("ecSignerEmail", ecSignerEmail);
        orgApplyCertificateMap.put("ecSignerEmail", ecSignerEmail);
        personApplyCertificateMap.put("ecSignerEmail", ecSignerEmail);
        this.ecSignerEmail = ecSignerEmail;
    }

    public String getEcSignerPhone() {
        return ecSignerPhone;
    }

    public void setEcSignerPhone(String ecSignerPhone) {
        registerRequestMap.put("ecSignerPhone", ecSignerPhone);
        imgUploadRequestMap.put("ecSignerPhone", ecSignerPhone);
        personApplyCertificateMap.put("ecSignerPhone", ecSignerPhone);
        orgApplyCertificateMap.put("ecSignerPhone", ecSignerPhone);
        this.ecSignerPhone = ecSignerPhone;
    }

    public String getEcSignerName() {
        return ecSignerName;
    }

    public void setEcSignerName(String ecSignerName) {
        registerRequestMap.put("ecSignerName", ecSignerName);
        imgUploadRequestMap.put("ecSignerName", ecSignerName);
        personApplyCertificateMap.put("ecSignerName", ecSignerName);
        orgApplyCertificateMap.put("ecSignerName", ecSignerName);
        this.ecSignerName = ecSignerName;
    }

    public Map<String, Object> getRegisterRequestMap() {
        return registerRequestMap;
    }

    public Map<String, Object> getImgUploadRequestMap() {
        return imgUploadRequestMap;
    }

    public byte[] getEcSignerImgDataBlob() {
        return ecSignerImgDataBlob;
    }

    public void setEcSignerImgDataBlob(byte[] ecSignerImgDataBlob) {
        imgUploadRequestMap.put("ecSignerImgDataBlob", ecSignerImgDataBlob);
        this.ecSignerImgDataBlob = ecSignerImgDataBlob;
    }

    public String getEcSignerImgName() {
        return ecSignerImgName;
    }

    public void setEcSignerImgName(String ecSignerImgName) {
        imgUploadRequestMap.put("ecSignerImgName", ecSignerImgName);
        this.ecSignerImgName = ecSignerImgName;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        imgUploadRequestMap.put("overwrite", false);
        this.overwrite = overwrite;
    }
}
