package com.iqb.eatep.ec.contract.bizretbean;

import java.util.List;
import java.util.Map;

/**
 * 
 * Description: 查询合同信息返回bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月28日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class SelectContractInfoRetBean {

    /** 业务id **/
    private String bizId;
    /** 机构编码  **/
    private String orgCode;
    /** 业务类型  **/
    private String bizType;
    /** 合同列表  **/
    private List<Map<String, Object>> ecList;
    
    public String getBizId() {
        return bizId;
    }
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    public List<Map<String, Object>> getEcList() {
        return ecList;
    }
    public void setEcList(List<Map<String, Object>> ecList) {
        this.ecList = ecList;
    }
    
}
