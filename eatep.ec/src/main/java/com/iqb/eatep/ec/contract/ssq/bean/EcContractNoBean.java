package com.iqb.eatep.ec.contract.ssq.bean;

/**
 * 
 * Description: 合同编号bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年10月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcContractNoBean {
    
    private Integer id;
    private String storeNo;
    private Integer signDate;
    private Integer contractSeq;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getStoreNo() {
        return storeNo;
    }
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }
    public Integer getSignDate() {
        return signDate;
    }
    public void setSignDate(Integer signDate) {
        this.signDate = signDate;
    }
    public Integer getContractSeq() {
        return contractSeq;
    }
    public void setContractSeq(Integer contractSeq) {
        this.contractSeq = contractSeq;
    }

}
