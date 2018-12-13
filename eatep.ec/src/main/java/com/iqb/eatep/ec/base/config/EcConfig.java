package com.iqb.eatep.ec.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.iqb.etep.common.base.config.BaseConfig;

/**
 * 
 * Description: ec配置
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Configuration
public class EcConfig extends BaseConfig {

    @Value("${url_sysmanage_org_crm_customer_push}")
    private String urlSysmanageOrgCrmCustomerPush;
    
    @Value("${ec.token.url.ssq}")
    private String ecTokenUrlSsq;
    
    /**上上签开发者账号**/
    @Value("${ssq.mid}")
    private String ssqMid;
    
    /**上上签开发者私匙**/
    @Value("${ssq.pem}")
    private String ssqPem;
    
    /**上上签服务域名**/
    @Value("${ssq.host}")
    private String ssqHost;
    
    /**上上签应用名称**/
    @Value("${ssq.app.name}")
    private String ssqAppName;
    
    /**上上签应用版本号**/
    @Value("${ssq.app.version}")
    private String ssqAppVersion;
    
    /**上上签应连接超时时间**/
    @Value("${ssq.timeout.connect}")
    private String ssqConnectTimeout;
    
    /**上上签读取超时时间**/
    @Value("${ssq.timeout.read}")
    private String ssqReadTimeout;
    
    /** 发件人邮箱  **/
    @Value("${ssq.sender.email}")
    private String ssqSenderEmail;
    
    /** 发件人姓名  **/
    @Value("${ssq.sender.name}")
    private String ssqSenderName;
    
    /** 发件人手机号  **/
    @Value("${ssq.sender.phone}")
    private String ssqSenderPhone;
    
    
    public String getEcTokenUrlSsq() {
        return ecTokenUrlSsq;
    }

    public void setEcTokenUrlSsq(String ecTokenUrlSsq) {
        this.ecTokenUrlSsq = ecTokenUrlSsq;
    }

    public String getUrlSysmanageOrgCrmCustomerPush() {
        return urlSysmanageOrgCrmCustomerPush;
    }

    public void setUrlSysmanageOrgCrmCustomerPush(String urlSysmanageOrgCrmCustomerPush) {
        this.urlSysmanageOrgCrmCustomerPush = urlSysmanageOrgCrmCustomerPush;
    }

	public String getSsqMid() {
		return ssqMid;
	}

	public void setSsqMid(String ssqMid) {
		this.ssqMid = ssqMid;
	}

	public String getSsqPem() {
		return ssqPem;
	}

	public void setSsqPem(String ssqPem) {
		this.ssqPem = ssqPem;
	}

	public String getSsqHost() {
		return ssqHost;
	}

	public void setSsqHost(String ssqHost) {
		this.ssqHost = ssqHost;
	}

	public String getSsqAppName() {
		return ssqAppName;
	}

	public void setSsqAppName(String ssqAppName) {
		this.ssqAppName = ssqAppName;
	}

	public String getSsqAppVersion() {
		return ssqAppVersion;
	}

	public void setSsqAppVersion(String ssqAppVersion) {
		this.ssqAppVersion = ssqAppVersion;
	}

	public String getSsqConnectTimeout() {
		return ssqConnectTimeout;
	}

	public void setSsqConnectTimeout(String ssqConnectTimeout) {
		this.ssqConnectTimeout = ssqConnectTimeout;
	}

	public String getSsqReadTimeout() {
		return ssqReadTimeout;
	}

	public void setSsqReadTimeout(String ssqReadTimeout) {
		this.ssqReadTimeout = ssqReadTimeout;
	}

    public String getSsqSenderEmail() {
        return ssqSenderEmail;
    }

    public void setSsqSenderEmail(String ssqSenderEmail) {
        this.ssqSenderEmail = ssqSenderEmail;
    }

    public String getSsqSenderName() {
        return ssqSenderName;
    }

    public void setSsqSenderName(String ssqSenderName) {
        this.ssqSenderName = ssqSenderName;
    }

    public String getSsqSenderPhone() {
        return ssqSenderPhone;
    }

    public void setSsqSenderPhone(String ssqSenderPhone) {
        this.ssqSenderPhone = ssqSenderPhone;
    }
	
}
