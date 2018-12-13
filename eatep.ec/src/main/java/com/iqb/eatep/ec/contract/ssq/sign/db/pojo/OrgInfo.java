package com.iqb.eatep.ec.contract.ssq.sign.db.pojo;

import com.iqb.eatep.ec.contract.ssq.sign.bean.EcSignerEntity;

public class OrgInfo extends EcSignerEntity {
    private String customerCode;
    private String customerShortName;
    private String customerName;
    private String ecSignerImgDataBlobStr;
    public String getEcSignerImgDataBlobStr() {
        return ecSignerImgDataBlobStr;
    }

    public void setEcSignerImgDataBlobStr(String ecSignerImgDataBlobStr) {
        this.ecSignerImgDataBlobStr = ecSignerImgDataBlobStr;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerShortName() {
        return customerShortName;
    }

    public void setCustomerShortName(String customerShortName) {
        this.customerShortName = customerShortName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
