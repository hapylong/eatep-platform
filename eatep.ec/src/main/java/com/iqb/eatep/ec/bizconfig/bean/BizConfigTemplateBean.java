package com.iqb.eatep.ec.bizconfig.bean;
/**
 * Description: 业务模板-合同模板实体类
 * 
 * @author baiaypeng
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    baiaypeng       1.0        1.0 Version 
 * </pre>
 */
public class BizConfigTemplateBean {
    private Integer id;// 物理主键
    private Integer bizConfigId;// 业务模板主键
    private Integer templateId;// 合同模板主键
    private Integer templateSignSeq;// 合同权值
    
    public Integer getId() {
        return id;                                     
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBizConfigId() {
        return bizConfigId;
    }

    public void setBizConfigId(Integer bizConfigId) {
        this.bizConfigId = bizConfigId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateSignSeq() {
        return templateSignSeq;
    }

    public void setTemplateSignSeq(Integer templateSignSeq) {
        this.templateSignSeq = templateSignSeq;
    }
}