package com.iqb.eatep.ec.contract.template.bean;

public class PaginationBean extends ContractTemplateBean {
	private Integer pageNum;
	private Integer pageSize;
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
