package com.iqb.eatep.ec.contract.ssq.sign.db.pojo;

public class ContractParticipantOrgPersistPojo {

    /** base_info **/
    private String ecSignerCode;
    private String ecSignerAddress;
    private String ecSignerProvince;
    private String ecSignerCity;
    private String ecSignerName;

    /** enterprise_info **/
    private String socialCreditCode;
    private String organizationCode;
    private String taxRegNum;
    private String bizRegNum;
    private String ecSignerCertNo;
    private String enterpriseLinkMan;
    private String ecSignerEmail;
    private String ecSignerPhone;

    public String getEcSignerCode() {
        return ecSignerCode;
    }
    public void setEcSignerCode(String ecSignerCode) {
        this.ecSignerCode = ecSignerCode;
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
    public String getEcSignerName() {
        return ecSignerName;
    }
    public void setEcSignerName(String ecSignerName) {
        this.ecSignerName = ecSignerName;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
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

    public String getBizRegNum() {
        return bizRegNum;
    }

    public void setBizRegNum(String bizRegNum) {
        this.bizRegNum = bizRegNum;
    }
    public String getEcSignerCertNo() {
        return ecSignerCertNo;
    }
    public void setEcSignerCertNo(String ecSignerCertNo) {
        this.ecSignerCertNo = ecSignerCertNo;
    }

    public String getEnterpriseLinkMan() {
        return enterpriseLinkMan;
    }

    public void setEnterpriseLinkMan(String enterpriseLinkMan) {
        this.enterpriseLinkMan = enterpriseLinkMan;
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

}
