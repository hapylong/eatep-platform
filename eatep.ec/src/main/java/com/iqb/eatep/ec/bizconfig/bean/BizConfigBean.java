package com.iqb.eatep.ec.bizconfig.bean;

import java.util.List;

import com.iqb.eatep.ec.contract.template.bean.ContractTemplateBean;

/**
 * Description: 业务模板实体类
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
public class BizConfigBean {
    private Integer id;// 物理主键
    private String bizTplCode;// 业务模板代码
    private String bizTplName;// 业务模板名称
    private String orgCode;// 机构代码
    private String orgName;// 机构名称
    private String isIncludeJunior;// 下级机构是否可用(0 否 1 是)
    private Integer bizType;// 业务类型
    private String signType;// 签署类型(1:线上签署 2:线下签署)
    private Integer status;// 启用状态(0 否 1 是)
    private Integer createTime;
    private Integer updateTime;
    private String creater;
    private String updater;
    private String deleteFlag;// 删除状态(0 否 1 是)
    private String remark;
    private Integer version;
    
    /**
     * 关联属性
     * */
    private List<BizConfigTemplateBean> bizConfigTemplateBeanList;// 业务模板-合同模板实体集合
    private List<ContractTemplateBean> contractTemplateBeanList;// 合同模板实体集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBizTplCode() {
        return bizTplCode;
    }

    public void setBizTplCode(String bizTplCode) {
        this.bizTplCode = bizTplCode;
    }

    public String getBizTplName() {
        return bizTplName;
    }

    public void setBizTplName(String bizTplName) {
        this.bizTplName = bizTplName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIsIncludeJunior() {
        return isIncludeJunior;
    }

    public void setIsIncludeJunior(String isIncludeJunior) {
        this.isIncludeJunior = isIncludeJunior;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public List<BizConfigTemplateBean> getBizConfigTemplateBeanList() {
		return bizConfigTemplateBeanList;
	}

	public void setBizConfigTemplateBeanList(
			List<BizConfigTemplateBean> bizConfigTemplateBeanList) {
		this.bizConfigTemplateBeanList = bizConfigTemplateBeanList;
	}

	public List<ContractTemplateBean> getContractTemplateBeanList() {
		return contractTemplateBeanList;
	}

	public void setContractTemplateBeanList(
			List<ContractTemplateBean> contractTemplateBeanList) {
		this.contractTemplateBeanList = contractTemplateBeanList;
	}
}
