package test.com.iqb.eatep.front.test;

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
    private String urlSysmanageOrgCrmCustomerPush = "";
    
    @Value("${ec.token.url.ssq}")
    private String ecTokenUrlSsq = "";
    
    /**上上签开发者账号**/
    @Value("${ssq.mid}")
    private String ssqMid = "9a6edde7af9544bc810b4add64172943";
    
    /**上上签开发者私匙**/
    @Value("${ssq.pem}")
    private String ssqPem = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALHAxQ3h4NBtIZDw+ECiwmVD8ZGLB+hhxHEEEhxYa2amGcR4jWAAwK5p/gNFEMk89vJ5qqp6g7oBY37IK5lAs9E5V6vCSIvrJdLhWElgsvguIT2uAaNXdZI5imQclNQfDOQzpxITQz2hZUittHmmbPtDsKVPn8Z/JCy9USvyPO8dAgMBAAECgYEApClyVAXeSN/g9il2PMuUWU3Mliwe24EfmXi2+TK3zg9tEMR9XMKVMP92trJcdF43e2zeL6ACdBDAQODBU0IsTm5qSedD0gLwasomBX9K75dO64TnHya+zGl6c8TuUs1Lux6fcDMKJmV+6hI+GhHMocXvRfdMWxPaefwFcnNgFkkCQQDXeZPftnmd8yA9h0vQVPKPUkBONajmzV50Jf9xC+SsofsPnWW5WLXt0Ld2dwsivTcLomgp6P0b8oBXtHQ+TGLTAkEA0y8BA2kH18Hr1qTk8TKWvIgsq5JFAhzdw/V6z3GW0mBRbIn8q0iQjSC6qS0FAxIBMuH0m4PeiwE6eaEFdMXQTwJASFRARhUrp9CH3v9+fHU3YtK6UUaD5s3DSfvDTkAux6Ar9WbhUaEsmKK6IuL88vFO+/HNQ1w5Mltpp4ckihD8DwJBAMX5pJuL1PxhITRBtRagdEsd5bKaFvNaGFPiR1U9iUSDYxG7chEon8qNyi9asv4j3NfUl7lKsE/jwOvRFgpYf2UCQF+eNtLHW2JZ8yKxZwvNaMmaOi6zIbOSWsd3aNuAHGNF++MCDG8ejzXS9IUNLplcQ8lO3ngbfiDcHi9jzH2+5bA=";
    
    /**上上签服务域名**/
    @Value("${ssq.host}")
    private String ssqHost= "https://www.bestsign.cn";
    
    /**上上签应用名称**/
    @Value("${ssq.app.name}")
    private String ssqAppName = "BestSign/SDK/JAVA";
    
    /**上上签应用版本号**/
    @Value("${ssq.app.version}")
    private String ssqAppVersion = "1.3.3";
    
    /**上上签应连接超时时间**/
    @Value("${ssq.timeout.connect}")
    private String ssqConnectTimeout = "1000000";
    
    /**上上签读取超时时间**/
    @Value("${ssq.timeout.read}")
    private String ssqReadTimeout = "1000000";
    
    
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
}
