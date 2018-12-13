package test.com.iqb.eatep.front.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.contract.ssq.service.BestSignService;
import com.iqb.eatep.ec.contract.ssq.service.Constants.CERT_TYPE;
import com.iqb.eatep.ec.contract.ssq.service.Constants.CONTRACT_NEEDVIDEO;
import com.iqb.eatep.ec.contract.ssq.service.Constants.CONTRACT_STATUS;
import com.iqb.eatep.ec.contract.ssq.service.Constants.DOWNLOAD_TYPE;
import com.iqb.eatep.ec.contract.ssq.service.Constants.USER_TYPE;
import com.iqb.eatep.ec.util.ImageUtil;
import com.iqb.etep.common.exception.IqbException;
/**
 * 
 * Description: 上上签服务接口实现类
 * @author baiyapeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月12日    baiyapeng       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("rawtypes")
@Service
public class BestSignServiceImpl extends BestSignService {	
    
    private static final Logger logger = LoggerFactory.getLogger(BestSignServiceImpl.class);
    
    private EcConfig ecConfig = new EcConfig(); 
    
    private HttpUtil httpUtil = new HttpUtil();    
    
	@Override
    public JSONObject regUser(Map parameterMap) throws IqbException {    	
    	try {
    		verifyItemOfMap(parameterMap, new String[] {"ecSignerType", "ecSignerPhone", "ecSignerName"}, true);
        	
    		Integer userType = (Integer) parameterMap.get("ecSignerType");
        	String ecSignerPhone = (String) parameterMap.get("ecSignerPhone");
        	String ecSignerName = (String) parameterMap.get("ecSignerName");    	
        	
        	Map<String, Object> resultMap = this.regUser(Integer.toString(userType), ecSignerPhone, ecSignerName);
    		return parseResultMap(resultMap);
    	} catch (IqbException iqbe) {
    		throw iqbe;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
    	}
	}
    
	@Override
	protected Map<String, Object> regUser(final String userType, final String userMobile,
			final String userName) throws IqbException, Exception {	
		final String method = "regUser.json";
		String path = "/open/" + method;
		logger.info("上上签注册用户服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("userType", userType); 
					put("mobile", userMobile);
					put("name", userName);
					put("throwOnExists", "");
				}});
				
				
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签注册用户服务：{}", requestBody);	
		
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), null);
		logger.info("上上签注册用户服务：{}", JSONObject.toJSONString(result));		
		
		return result;
	}
	
	@Override
	public JSONObject uploadUserImage(Map parameterMap)
			throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"ecSignerType", "ecSignerPhone", "ecSignerName", "ecSignerImgDataBlob"}, true);
	    	
			Integer ecSignerType = (Integer) parameterMap.get("ecSignerType");
	    	String ecSignerPhone = (String) parameterMap.get("ecSignerPhone");
	    	String ecSignerName = (String) parameterMap.get("ecSignerName");
	    	byte[] ecSignerImgDataBlob = (byte[]) parameterMap.get("ecSignerImgDataBlob");    	
	    	String ecSignerEmail = (String) getItemOfMap(parameterMap, "ecSignerEmail");
	    	String ecSignerImgName = (String) getItemOfMap(parameterMap, "ecSignerImgName");
	    	Integer ecSignerImgType = (Integer) getItemOfMap(parameterMap, "ecSignerImgType");
	    	Boolean overwrite = (Boolean) getItemOfMap(parameterMap, "overwrite");	    	
	    	
	    	Map<String, Object> resultMap = this.uploadUserImage(Integer.toString(ecSignerType), ecSignerEmail, ecSignerPhone, ecSignerName, ecSignerImgDataBlob, ecSignerImgName, overwrite == null ? false : overwrite, ecSignerImgType == null ? null : Integer.toString(ecSignerImgType));
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
    		throw iqbe;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
    	}
	}
	
	@Override
	protected Map<String, Object> uploadUserImage(final String userType,
			final String userEmail, final String userMobile, final String userName,
			final byte[] byts, final String sealName, final boolean overwrite, final String sealType)
			throws IqbException, Exception {
		final String method = "uploaduserimage.json";
		String path = "/open/" + method;
		logger.info("上上签上传用户签章服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("usertype", userType);
					put("useracount", userEmail == null ? "" : userEmail); 
					put("usermobile", userMobile);
					put("username", userName);
					put("imgName", Long.toString(System.currentTimeMillis()) + "." + (ImageUtil.getPicType(byts)).toLowerCase());
					put("imgtype", ImageUtil.getPicType(byts));
					put("image", Base64.encodeBase64String(byts));					
					put("sealname", sealName == null ? "" : sealName);
					put("updateflag", overwrite ? "1" : "0");
					put("sealImageType", sealType == null ? "" : sealType);
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签上传用户签章服务：{}", requestBody);
		
		Map<String, String> headerData = new HashMap<String, String>();
		headerData.put("Expect", "");
				        
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), headerData);
		logger.info("上上签上传用户签章服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}
	
	@Override
	public JSONObject certificateApplyForPersonal(Map parameterMap)
			throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"ecSignerPhone", "ecSignerName", "ecSignerCertPwd", "ecSignerCertType", "ecSignerCertNo", "ecSignerProvince", "ecSignerCity", "ecSignerAddress"}, true);
	    	
	    	String ecSignerPhone = (String) parameterMap.get("ecSignerPhone");
	    	String ecSignerName = (String) parameterMap.get("ecSignerName");
	    	String ecSignerCertPwd = (String) parameterMap.get("ecSignerCertPwd");
	    	Integer ecSignerCertType = (Integer) parameterMap.get("ecSignerCertType");
	    	String ecSignerCertNo = (String) parameterMap.get("ecSignerCertNo");
	    	String ecSignerProvince = (String) parameterMap.get("ecSignerProvince");
	    	String ecSignerCity = (String) parameterMap.get("ecSignerCity");
	    	String ecSignerAddress = (String) parameterMap.get("ecSignerAddress");    	
	    	String ecSignerEmail = (String) getItemOfMap(parameterMap, "ecSignerEmail");
	    	Integer ecSignerCertPeriod = (Integer) getItemOfMap(parameterMap, "ecSignerCertPeriod");
	    	
	    	Map<String, Object> resultMap = this.certificateApply(ecSignerEmail, ecSignerPhone, ecSignerName, ecSignerCertPwd, Integer.toString(ecSignerCertType), ecSignerCertNo, ecSignerProvince, ecSignerCity, ecSignerAddress, ecSignerCertPeriod == null ? null : Integer.toString(ecSignerCertPeriod));
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
    		throw iqbe;
    	} catch (Exception e) {
    		throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
    	}
	}

	
	@Override
	protected Map<String, Object> certificateApply(final String userEmail, final String userMobile,
			final String userName, final String password, final String identType,
			final String identNo, final String province, final String city, final String address,
			final String duration) throws IqbException, Exception {
		final String method = "certificateApply.json";
		String path = "/open/" + method;
		logger.info("上上签申请用户证书服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("userType", Integer.toString(USER_TYPE.PERSONAL.value()));
					put("email", userEmail == null ? "" : userEmail); 
					put("linkMobile", userMobile);
					put("mobile", userMobile);
					put("name", userName);
					put("password", password);
					put("certificateType", Integer.toString(CERT_TYPE.NORMAL.value()));
					put("identType", identType);
					put("identNo", identNo);
					put("province", province);
					put("city", city);
					put("address", address);
					put("duration", duration == null ? "24" : identType);					
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签申请用户证书服务：{}", requestBody);
		
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), null);
		logger.info("上上签申请用户证书服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}

	@Override
	public JSONObject certificateApplyForEnterprise(Map parameterMap)
			throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"ecSignerPhone", "ecSignerName", "ecSignerCertPwd", "ecSignerCertNo", "bizRegNum", "organizationCode", "taxRegNum", "ecSignerProvince", "ecSignerCity", "ecSignerAddress"}, true);
	    	
	    	String ecSignerPhone = (String) parameterMap.get("ecSignerPhone");
	    	String ecSignerName = (String) parameterMap.get("ecSignerName");
	    	String ecSignerCertPwd = (String) parameterMap.get("ecSignerCertPwd");
	    	String ecSignerCertNo = (String) parameterMap.get("ecSignerCertNo");
	    	String bizRegNum = (String) parameterMap.get("bizRegNum");
	    	String organizationCode = (String) parameterMap.get("organizationCode");
	    	String taxRegNum = (String) parameterMap.get("taxRegNum");
	    	String ecSignerProvince = (String) parameterMap.get("ecSignerProvince");
	    	String ecSignerCity = (String) parameterMap.get("ecSignerCity");
	    	String ecSignerAddress = (String) parameterMap.get("ecSignerAddress");   
	    	String ecSignerEmail = (String) getItemOfMap(parameterMap, "ecSignerEmail");
	    	String linkMan = (String) getItemOfMap(parameterMap, "linkMan");
	    	Integer ecSignerCertPeriod = (Integer) getItemOfMap(parameterMap, "ecSignerCertPeriod");
	    	
	    	Map<String, Object> resultMap = this.certificateApply(ecSignerEmail, ecSignerPhone, ecSignerName, ecSignerCertPwd, linkMan == null ? "" : linkMan, ecSignerCertNo, bizRegNum, organizationCode, taxRegNum, ecSignerProvince, ecSignerCity, ecSignerAddress, ecSignerCertPeriod == null ? null : Integer.toString(ecSignerCertPeriod));
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
    		throw iqbe;
    	} catch (Exception e) {
    		throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
    	}
	}
	
	@Override
	protected Map<String, Object> certificateApply(final String userEmail, final String userMobile,
			final String userName, final String password, final String linkMan, final String linkIdCode,
			final String icCode, final String orgCode, final String taxCode, final String province,
			final String city, final String address, final String duration) throws IqbException,
			Exception {
		final String method = "certificateApply.json";
		String path = "/open/" + method;
		logger.info("上上签申请用户证书服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("userType", Integer.toString(USER_TYPE.ENTERPRISE.value()));
					put("email", userEmail == null ? "" : userEmail); 
					put("linkMobile", userMobile);
					put("mobile", userMobile);
					put("name", userName);
					put("password", password);
					put("linkMan", linkMan);
					put("linkIdCode", linkIdCode);
					put("certificateType", Integer.toString(CERT_TYPE.NORMAL.value()));
					put("icCode", icCode);					
					put("orgCode", orgCode);
					put("taxCode", taxCode);
					put("province", province);
					put("city", city);
					put("address", address);
					put("duration", duration == null ? "24" : duration);					
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签申请用户证书服务：{}", requestBody);
		
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), null);
		logger.info("上上签申请用户证书服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}
	
	@Override
	public JSONObject contractUpload(Map parameterMap)
			throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"ecFileBlob", "ecFileName", "sendUser"}, true);
	    	
			byte[] ecFileBlob = (byte[]) parameterMap.get("ecFileBlob");
			String ecFileName = (String) parameterMap.get("ecFileName");
	    	@SuppressWarnings("unchecked")
			Map<String, Object> sendUser = (Map<String, Object>) parameterMap.get("sendUser");
	    	
	    	verifyItemOfMap(sendUser, new String[] {"ecSignerType", "ecSignerPhone", "ecSignerName", "ecTheme", "ecEffectiveDays", "ecSenderType"}, true);
	    	Integer ecSignerType = (Integer) sendUser.get("ecSignerType");
	    	String ecSignerPhone = (String) sendUser.get("ecSignerPhone");
	    	String ecSignerName = (String) sendUser.get("ecSignerName");
	    	String ecTheme = (String) sendUser.get("ecTheme");
	    	Integer ecEffectiveDays = (Integer) sendUser.get("ecEffectiveDays");
	    	Integer ecSenderType = (Integer) sendUser.get("ecSenderType");   	
			String ecSignerEmail = (String) getItemOfMap(sendUser, "ecSignerEmail");
			String ecAbstract = (String) getItemOfMap(sendUser, "ecAbstract");
			
			sendUser.clear();
			sendUser.put("usertype", Integer.toString(ecSignerType));
			sendUser.put("mobile", ecSignerPhone);
			sendUser.put("name", ecSignerName);
			sendUser.put("emailtitle", ecTheme);
			sendUser.put("sxdays", Integer.toString(ecEffectiveDays));
			sendUser.put("selfsign", Integer.toString(ecSenderType));
			sendUser.put("email", ecSignerEmail == null ? "" : ecSignerEmail);
			sendUser.put("emailcontent", ecAbstract == null ? "" : ecAbstract);
			sendUser.put("UserfileType", "1");
			sendUser.put("Signimagetype", "0");
			
	    	@SuppressWarnings("unchecked")
			List<Map<String, Object>> receiveUserList = (List<Map<String, Object>>) getItemOfMap(parameterMap, "receiveUserList");
	    	if (receiveUserList != null && receiveUserList.size() > 0) {
	    		for (Map<String, Object> m : receiveUserList) {
	        		verifyItemOfMap(m, new String[] {"ecSignerType", "ecSignerPhone", "ecSignerName"}, true);
	        		
	        		Integer ecSignerTyp = (Integer) m.get("ecSignerType");
	    	    	String ecSignerPhon = (String) m.get("ecSignerPhone");
	    	    	String ecSignerNam = (String) m.get("ecSignerName");
	    	    	String ecSignerEmai = (String) getItemOfMap(m, "ecSignerEmail");
	        		
	    	    	m.clear();
	    			m.put("usertype", Integer.toString(ecSignerTyp));
	    			m.put("mobile", ecSignerPhon);
	    			m.put("name", ecSignerNam);
	    			m.put("email", ecSignerEmai == null ? "" : ecSignerEmai);
	    			m.put("Signimagetype", "0");
	    			m.put("isvideo", Integer.toString(CONTRACT_NEEDVIDEO.NONE.value()));
	        	}
	    	}
	    	
	    	
	    	Map<String, Object> resultMap = this.contractUpload(ecFileBlob, ecFileName, sendUser, receiveUserList);
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
    		throw iqbe;
    	} catch (Exception e) {
    	    e.printStackTrace();
    		throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
    	}
	}
	
	@Override
	protected Map<String, Object> contractUpload(final byte[] byts, final String fileName, 
			final Map<String, Object> sendUser,
			final List<Map<String, Object>> receiveUserList) throws IqbException,
			Exception {		
		final String method = "sjdsendcontractdocUpload.json";
		String path = "/open/" + method;
		logger.info("上上签合同上传服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("file", fileName);
					put("sendUser", JSONObject.toJSONString(sendUser)); 
					put("userlist", (receiveUserList != null && receiveUserList.size() > 0) ?  JSONObject.toJSONString(receiveUserList) : "");			
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签合同上传服务：{}", requestBody);
		
		byte[] postData = byts;
		
		List<Map<String, Object>> sendUserList = new ArrayList<Map<String, Object>>();
		sendUserList.add(sendUser);
		String jsonSendUserList = JSONArray.toJSONString(sendUserList);
		String jsonReceiveUserList = null;
		if (receiveUserList != null && receiveUserList.size() > 0) {
			jsonReceiveUserList = JSONArray.toJSONString(receiveUserList);
		}		
		
		String signData;
		if (receiveUserList != null && receiveUserList.size() > 0) {
			signData = EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.md5(postData), EncodeUtil.urlEncode(fileName), jsonReceiveUserList, jsonSendUserList);
		} else {
			signData = EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.md5(postData), EncodeUtil.urlEncode(fileName), jsonSendUserList);			
		}
		
		Map<String, String> headerData = new HashMap<String, String>();
		headerData.put("filename", EncodeUtil.urlEncode(fileName));
		headerData.put("senduser", jsonSendUserList);
		if(receiveUserList != null  && receiveUserList.size() > 0) {
			headerData.put("userlist", jsonReceiveUserList);
		}				
		
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, postData, signData, headerData);
		logger.info("上上签合同上传服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}
	
	@Override
	public JSONObject contractUploadAdd(Map parameterMap)
			throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"tpSignid", "receiveUserList"}, true);
	    	
			String tpSignid = (String) parameterMap.get("tpSignid");    	
	    	@SuppressWarnings("unchecked")
			List<Map<String, Object>> receiveUserList = (List<Map<String, Object>>) parameterMap.get("receiveUserList");
	    	if (receiveUserList.size() == 0) {
	    		throw new IqbException(EcReturnInfo.EC_SSQ_01030001);
	    	}
	    	if (receiveUserList != null && receiveUserList.size() > 0) {
	    		for (Map<String, Object> m : receiveUserList) {
	        		verifyItemOfMap(m, new String[] {"ecSignerType", "ecSignerPhone", "ecSignerName"}, true);
	        		
	        		Integer ecSignerType = (Integer) m.get("ecSignerType");
	    	    	String ecSignerPhone = (String) m.get("ecSignerPhone");
	    	    	String ecSignerName = (String) m.get("ecSignerName");
	    	    	String ecSignerEmail = (String) getItemOfMap(m, "ecSignerEmail");
	        		
	    	    	m.clear();
	    			m.put("usertype", Integer.toString(ecSignerType));
	    			m.put("mobile", ecSignerPhone);
	    			m.put("name", ecSignerName);
	    			m.put("email", ecSignerEmail == null ? "" : ecSignerEmail);
	    			m.put("Signimagetype", "0");
	    			m.put("isvideo", Integer.toString(CONTRACT_NEEDVIDEO.NONE.value()));
	        	}
	    	}
	    	
	    	Map<String, Object> resultMap = this.contractUpload(tpSignid, receiveUserList);
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}
	
	@Override
	protected Map<String, Object> contractUpload(final String signId,
			final List<Map<String, Object>> receiveUserList) throws IqbException,
			Exception {
		final String method = "sjdsendcontract.json";
		String path = "/open/" + method;
		logger.info("上上签追加合同接受者服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("signid", signId); 
					put("userlist", JSONArray.toJSONString(receiveUserList));
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签追加合同接受者服务：{}", requestBody);
		
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), null);
		logger.info("上上签追加合同接受者服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}
	
	@Override
	public JSONObject autoSign(Map parameterMap) throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"ecSignerPhone", "tpSignid", "coordinateList"}, true);
	    	
			String ecSignerPhone = (String) parameterMap.get("ecSignerPhone");
	    	String tpSignid = (String) parameterMap.get("tpSignid");
	    	@SuppressWarnings("unchecked")
			List<Map<String, Object>> coordinateList = (List<Map<String, Object>>) JSONObject.parse(ObjectUtils.toString(parameterMap.get("coordinateList")));
	    	if (coordinateList.size() == 0) {
	    		throw new IqbException(EcReturnInfo.EC_SSQ_01030001);
	    	}
	    	for (Map<String, Object> m : coordinateList) {
	    		verifyItemOfMap(m, new String[] {"ecSealPageNum", "ecSealPositionX", "ecSealPositionY"}, true);
        		
	    		String ecSealPageNum = ObjectUtils.toString(m.get("ecSealPageNum"));
	    		String ecSealPositionX = ObjectUtils.toString(m.get("ecSealPositionX"));
        		double ecSealPositionX_d = Integer.parseInt(ecSealPositionX);
        		String ecSealPositionY = ObjectUtils.toString(m.get("ecSealPositionY"));
        		double ecSealPositionY_d = Integer.parseInt(ecSealPositionY);
        		
    	    	m.clear();
    			m.put("pagenum", Integer.toString(Integer.parseInt(ecSealPageNum)));
    			m.put("signx", Double.toString(ecSealPositionX_d / 100));
    			m.put("signy", Double.toString(ecSealPositionY_d / 100));
	    	}
	    	
	    	String sealName = (String) getItemOfMap(parameterMap, "sealName");
	    	String ecTokenUrl = (String) getItemOfMap(parameterMap, "ecTokenUrl");
	    	
	    	Map<String, Object> resultMap = this.autoSign(ecSignerPhone , tpSignid, coordinateList, true, sealName, ecTokenUrl);
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
		    e.printStackTrace();
		    logger.info("调用上上签接口异常：{}", e);
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}
	
	@Override
	protected Map<String, Object> autoSign(final String userAccount, final String signId,
			final List<Map<String, Object>> coordinateList, final boolean isEnd,
			final String sealName, final String noticeUrl) throws IqbException, Exception {
		final String method = "AutoSignFopp.json";
		String path = "/open/" + method;
		logger.info("上上签自动签署服务：{}", ecConfig.getSsqHost() + path);

		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("email", userAccount);
					put("signid", signId);
					put("Coordinatelist", JSONArray.toJSONString(coordinateList));
					put("openflag", isEnd ? "1" : "0");
					put("sealname", sealName == null ? "" : sealName);
					put("noticeUrls", noticeUrl == null ? "" : noticeUrl);
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签自动签署服务：{}", requestBody);	

		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), null);
		logger.info("上上签自动签署服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}
	
	@Override
	public String getSignPage(Map parameterMap) throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"ecSignerPhone", "tpSignid", "coordinateList", "tpReturnUrl", "signDeviceType"}, true);
	    	
			String ecSignerPhone = (String) parameterMap.get("ecSignerPhone");
	    	String tpSignid = (String) parameterMap.get("tpSignid");
	    	@SuppressWarnings("unchecked")
	    	List<Map<String, Object>> coordinateList = (List<Map<String, Object>>) JSONObject.parse(ObjectUtils.toString(parameterMap.get("coordinateList")));
	    	if (coordinateList.size() == 0) {
	    		throw new IqbException(EcReturnInfo.EC_SSQ_01030001);
	    	}
	    	for (Map<String, Object> m : coordinateList) {
	    		verifyItemOfMap(m, new String[] {"ecSealPageNum", "ecSealPositionX", "ecSealPositionY"}, true);

                String ecSealPageNum = ObjectUtils.toString(m.get("ecSealPageNum"));
                String ecSealPositionX = ObjectUtils.toString(m.get("ecSealPositionX"));
                double ecSealPositionX_d = Integer.parseInt(ecSealPositionX);
                String ecSealPositionY = ObjectUtils.toString(m.get("ecSealPositionY"));
                double ecSealPositionY_d = Integer.parseInt(ecSealPositionY);
                
                m.clear();
                m.put("pagenum", Integer.toString(Integer.parseInt(ecSealPageNum)));
                m.put("signx", Double.toString(ecSealPositionX_d / 100));
                m.put("signy", Double.toString(ecSealPositionY_d / 100));
	    	}
	    	String tpReturnUrl = (String) parameterMap.get("tpReturnUrl");
	    	Integer signDeviceType = (Integer) parameterMap.get("signDeviceType");
	    	String ecTokenUrl = (String) getItemOfMap(parameterMap, "ecTokenUrl");
	    	
			return this.getSignPage(ecSignerPhone, tpSignid, coordinateList, tpReturnUrl, Integer.toString(signDeviceType), ecTokenUrl);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
		    e.printStackTrace();
		    logger.info("调用上上签手动签署接口异常：{}", e);
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}
	
	@Override
	protected String getSignPage(final String userAccount, final String signId,
			final List<Map<String, Object>> coordinateList, final String returnUrl,
			final String deviceType, final String noticeUrl) throws IqbException,
			Exception {
		final String method = "getSignPageSignimagePc.json";
		String path = "/openpagepp/" + method;
		logger.info("生成上上签手动签署链接服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("userEmail", userAccount);
					put("signId", signId); 
					put("coordinateList", signId); 
					put("returnUrl", JSONArray.toJSONString(coordinateList)); 
					put("deviceType", deviceType); 
					put("noticeUrl", noticeUrl == null ? "" : noticeUrl); 
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("生成上上签手动签署链接服务：{}", requestBody);	
		
		String signData = EncodeUtil.getSignData(method, ecConfig.getSsqMid(), signId, userAccount, JSONArray.toJSONString(coordinateList), returnUrl, deviceType);

		String sign = EncodeUtil.getRsaSign(ecConfig.getSsqPem(), signData);

		StringBuilder requestPath = new StringBuilder(path + "?");
		requestPath.append("mid=" + EncodeUtil.urlEncode(ecConfig.getSsqMid()) + "&");
		requestPath.append("sign=" + EncodeUtil.urlEncode(sign) + "&");
		requestPath.append("fsid=" + EncodeUtil.urlEncode(signId) + "&");
		requestPath.append("email=" + EncodeUtil.urlEncode(userAccount) + "&");
		requestPath.append("Coordinatelist=" + EncodeUtil.urlEncode(JSONArray.toJSONString(coordinateList)) + "&");
		requestPath.append("returnurl=" + EncodeUtil.urlEncode(returnUrl) + "&");
		requestPath.append("typedevice=" + deviceType);
		if (noticeUrl != null && noticeUrl.length() > 0) {
			requestPath.append("&");
			requestPath.append("pushurl=" + EncodeUtil.urlEncode(noticeUrl));
		}

		path = requestPath.toString();
		logger.info("生成上上签手动签署链接服务：{}", ecConfig.getSsqHost() + path);
		
		return ecConfig.getSsqHost() + path;
	}
	
	@Override
	public JSONObject endContract(Map parameterMap) throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"tpSignid"}, true);
	    	
			String tpSignid = (String) parameterMap.get("tpSignid");
	    	
	    	Map<String, Object> resultMap = this.endContract(tpSignid);
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}
	
	@Override
	protected Map<String, Object> endContract(final String signId) throws IqbException,
			Exception {
		final String method = "endcontract.json";
		String path = "/open/" + method;
		logger.info("上上签合同结束服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("signid", signId);
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签合同结束服务：{}", requestBody);
		
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), null);
		logger.info("上上签合同结束服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}
	
	@Override
	public JSONObject contractInfo(Map parameterMap)
			throws IqbException {
		try {
			verifyItemOfMap(parameterMap, new String[] {"tpSignid"}, true);
	    	
			String tpSignid = (String) parameterMap.get("tpSignid");
	    	
	    	Map<String, Object> resultMap = this.contractInfo(tpSignid);
			return parseResultMap(resultMap);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
		    e.printStackTrace();
		    logger.error(e.getMessage());
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}
	
	@Override
	protected Map<String, Object> contractInfo(final String signId) throws IqbException,
			Exception {
		final String method = "contractInfo.json";
		String path = "/open/" + method;
		logger.info("上上签合同信息查询服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("fsdid", signId);
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("上上签合同信息查询服务：{}", requestBody);
		
		Map<String, Object> result = httpUtil.httpPost("POST", path, requestBody, EncodeUtil.getSignData(method, ecConfig.getSsqMid(), EncodeUtil.getRequestMd5(requestBody)), null);
		logger.info("上上签合同信息查询服务：{}", JSONObject.toJSONString(result));	
		
		return result;
	}
	
	@Override
	public String viewContract(Map map) throws IqbException {
		try {
			verifyItemOfMap(map, new String[] {"tpSignid", "tpDocid"}, true);
	    	
			String tpSignid = (String) map.get("tpSignid");
			String tpDocid = (String) map.get("tpDocid");
	    	
			return this.viewContract(tpSignid, tpDocid);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}

	@Override
	protected String viewContract(final String signId, final String docId) throws IqbException,
			Exception {
		final String method = "ViewContract.page";
		String path = "/openpage/" + method;
		logger.info("生成上上签合同预览链接服务：{}", ecConfig.getSsqHost() + path);
		
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {{
			put("request", new HashMap<String, Object>() {{
				put("url", ecConfig.getSsqHost() + "/open/" + method);
				put("content", new HashMap<String, Object>() {{
					put("signId", signId); 
					put("docId", docId); 
				}});
			}});
		}};
		String requestBody = JSONObject.toJSONString(data);
		logger.info("生成上上签合同预览链接服务：{}", requestBody);	

		String signData = EncodeUtil.getSignData(method, ecConfig.getSsqMid(), signId, docId);
		
		String sign = EncodeUtil.getRsaSign(ecConfig.getSsqPem(), signData);

		StringBuffer requestPath = new StringBuffer(path + "?");
		requestPath.append("mid=" + EncodeUtil.urlEncode(ecConfig.getSsqMid()) + "&");
		requestPath.append("sign=" + EncodeUtil.urlEncode(sign) + "&");
		requestPath.append("fsdid=" + EncodeUtil.urlEncode(signId) + "&");
		requestPath.append("docid=" + EncodeUtil.urlEncode(docId));
		path = requestPath.toString();
		logger.info("生成上上签合同预览链接服务：{}", ecConfig.getSsqHost() + path);
		
		return ecConfig.getSsqHost() + path;
	}	
	
	@Override
	public String contractDownload(Map map) throws IqbException {
		try {
			verifyItemOfMap(map, new String[] {"tpSignid"}, true);
	    	
			String tpSignid = (String) map.get("tpSignid");
			DOWNLOAD_TYPE downloadType = (DOWNLOAD_TYPE) getItemOfMap(map, "downloadType");
	    	
			return this.contractDownload(tpSignid, downloadType);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}

	@Override
	protected String contractDownload(final String signId, final DOWNLOAD_TYPE downloadType)
			throws IqbException, Exception {
		
		if (downloadType == null || downloadType == DOWNLOAD_TYPE.PDF) {
			final String method = "contractDownloadMobile.page";
			String path = "/openpage/" + method;
			logger.info("生成上上签合同下载链接服务：{}", ecConfig.getSsqHost() + path);

			@SuppressWarnings("serial")
			Map<String, Object> data = new HashMap<String, Object>() {{
				put("request", new HashMap<String, Object>() {{
					put("url", ecConfig.getSsqHost() + "/open/" + method);
					put("content", new HashMap<String, Object>() {{
						put("signId", signId); 
						put("downloadType", "PDF"); 
					}});
				}});
			}};
			String requestBody = JSONObject.toJSONString(data);
			logger.info("生成上上签合同下载链接服务：{}", requestBody);	
			
			String signData = EncodeUtil.getSignData(method, ecConfig.getSsqMid(), signId, Integer.toString(CONTRACT_STATUS.DONE.value()));
			
			String sign = EncodeUtil.getRsaSign(ecConfig.getSsqPem(), signData);

			StringBuffer requestPath = new StringBuffer(path + "?");
			requestPath.append("mid=" + EncodeUtil.urlEncode(ecConfig.getSsqMid()) + "&");
			requestPath.append("sign=" + EncodeUtil.urlEncode(sign) + "&");
			requestPath.append("fsdid=" + EncodeUtil.urlEncode(signId) + "&");
			requestPath.append("status=" + EncodeUtil.urlEncode(Integer.toString(CONTRACT_STATUS.DONE.value())));
			path = requestPath.toString();
			logger.info("生成上上签合同下载链接服务：{}", ecConfig.getSsqHost() + path);
			
			return ecConfig.getSsqHost() + path;
		} else {
			final String method = "contractDownload.page";
			String path = "/openpage/" + method;
			logger.info("生成上上签合同下载链接服务：{}", ecConfig.getSsqHost() + path);

			@SuppressWarnings("serial")
			Map<String, Object> data = new HashMap<String, Object>() {{
				put("request", new HashMap<String, Object>() {{
					put("url", ecConfig.getSsqHost() + "/open/" + method);
					put("content", new HashMap<String, Object>() {{
						put("signId", signId); 
						put("downloadType", "ZIP"); 
					}});
				}});
			}};
			String requestBody = JSONObject.toJSONString(data);
			logger.info("生成上上签合同下载链接服务：{}", requestBody);	
			
			String signData = EncodeUtil.getSignData(method, ecConfig.getSsqMid(), signId, Integer.toString(CONTRACT_STATUS.DONE.value()));
			
			String sign = EncodeUtil.getRsaSign(ecConfig.getSsqPem(), signData);

			StringBuffer requestPath = new StringBuffer(path + "?");
			requestPath.append("mid=" + EncodeUtil.urlEncode(ecConfig.getSsqMid()) + "&");
			requestPath.append("sign=" + EncodeUtil.urlEncode(sign) + "&");
			requestPath.append("fsdid=" + EncodeUtil.urlEncode(signId) + "&");
			requestPath.append("status=" + EncodeUtil.urlEncode(Integer.toString(CONTRACT_STATUS.DONE.value())));
			path = requestPath.toString();
			logger.info("生成上上签合同下载链接服务：{}", ecConfig.getSsqHost() + path);
			
			return ecConfig.getSsqHost() + path;
		}		
	}
	
	@Override
	public byte[] contractDownloadLocal(Map map) throws IqbException {
		try {
			verifyItemOfMap(map, new String[] {"tpSignid"}, true);
	    	
			String tpSignid = (String) map.get("tpSignid");
			DOWNLOAD_TYPE downloadType = (DOWNLOAD_TYPE) getItemOfMap(map, "downloadType");
	    	
			Map<String, Object> resultMap = this.contractDownloadLocal(tpSignid, downloadType);
			return parseResultMap2(resultMap);
		} catch (IqbException iqbe) {
			throw iqbe;
		} catch (Exception e) {
			e.printStackTrace();
			throw (new IqbException(EcReturnInfo.EC_SSQ_01030002));
		}
	}
	
	@Override
	protected Map<String, Object> contractDownloadLocal(String signId, final DOWNLOAD_TYPE downloadType) throws IqbException,
			Exception {
		String url = this.contractDownload(signId, downloadType);
		Map<String, Object> result = httpUtil.get(url, true);
		return result;
	}
	
	/**
     * 校验 map
     * */
    public static void verifyMap(Map map) throws IqbException {
    	if (map == null || map.size() == 0){
    		throw new IqbException(EcReturnInfo.EC_SSQ_01030001);    		
    	}
    }
    
    /**
     * 校验参数
     * */
    public static void verifyItemOfMap(Map map, String[] keys, Boolean necessary) throws IqbException{
    	verifyMap(map);
    	if (keys != null) {
    		for (int i = 0; i < keys.length; i++) {
    			if (keys[i] != null) {
    				if (map.containsKey(keys[i])) {
                		if (map.get(keys[i]) == null) {
                			if (necessary != null && necessary){
                    			throw new IqbException(EcReturnInfo.EC_SSQ_01030001);
                    		}                 			  
                		}
                	} else {
                		if (necessary != null && necessary){
                			throw new IqbException(EcReturnInfo.EC_SSQ_01030001);
                		}    		
                	}
    			}
    		}    		
    	}    	
    }
    
    /**
     * 校验参数
     * */
    public static Object getItemOfMap(Map map, String key) throws IqbException{
    	if (map != null && map.size() > 0) {    	
			if (key != null) {
				if (map.containsKey(key)) {
            		if (map.get(key) != null) {
            			return map.get(key);
            		} 
            		return null;              		
            	} 
				return null;
			}
			return null;
    	}
    	return null;
    }
    
    /**
     * 校验结果
     * */
    public static JSONObject parseResultMap(Map<String, Object> resultMap) throws IqbException {
    	if (resultMap == null || resultMap.size() == 0){
    		return null;
    	}
    	if (resultMap.containsKey("responseCode") && resultMap.get("responseCode") != null) {
    		Integer responseCode = (Integer) resultMap.get("responseCode");
        	if (responseCode == 200){
        		if (resultMap.containsKey("response") && resultMap.get("response") != null) {
        			JSONObject objs = JSONObject.parseObject((String) resultMap.get("response"));
        			if (objs.containsKey("response") && objs.getJSONObject("response") != null) {
        				return objs.getJSONObject("response");
        			}
        			return null;
        		} 
        		return null;
        	}
        	throw new IqbException(EcReturnInfo.EC_SSQ_01030002);
    	}    	
    	throw new IqbException(EcReturnInfo.EC_SSQ_01030002);
    }
    
    /**
     * 校验结果
     * */
    public static byte[] parseResultMap2(Map<String, Object> resultMap) throws IqbException {
    	if (resultMap == null || resultMap.size() == 0){
    		return null;
    	}
    	
    	
    	if (resultMap.containsKey("responseCode") && resultMap.get("responseCode") != null) {
    		Integer responseCode = (Integer) resultMap.get("responseCode");
        	if (responseCode == 200){
        		if (resultMap.containsKey("response") && resultMap.get("response") != null) {
        			return (byte[]) resultMap.get("response");
        		} 
        		return null;
        	}
        	throw new IqbException(EcReturnInfo.EC_SSQ_01030002);
    	}    	
    	throw new IqbException(EcReturnInfo.EC_SSQ_01030002);
    }

    @Override
    protected Map<String, Object> regUser(String userType, String userMobile, String userName, String email)
            throws IqbException, Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
