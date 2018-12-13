package com.iqb.eatep.ec.contract.ssq.bean;

/**
 * 
 * Description: 上上签发送合同实体类
 * @author baiyapeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    baiyapeng       1.0        1.0 Version 
 * </pre>
 */
public class PostFile {
	
	private String name = "";
	private String contentType = "";
	private byte[] data = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
