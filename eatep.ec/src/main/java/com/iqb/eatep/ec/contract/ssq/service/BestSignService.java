package com.iqb.eatep.ec.contract.ssq.service;



import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.contract.ssq.service.Constants.DOWNLOAD_TYPE;
import com.iqb.etep.common.exception.IqbException;
/**
 * 
 * Description: 上上签服务接口
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
public abstract class BestSignService {
	
	/************************************************** 对外接口 **************************************************/
	
	/**
	 * 注册用户
	 * 
	 * @param ecSignerType(Y/Integer) 
	 * @param ecSignerEmail(N/String)
	 * @param ecSignerPhone(Y/String)
	 * @param ecSignerName(Y/String)
	 * @return
	 * @throws IqbException
	 */
	public abstract JSONObject regUser(Map map) throws IqbException;
	
	/**
	 * 上传用户签章
	 * 
	 * @param ecSignerType(Y/Integer)
	 * @param ecSignerEmail(N/String)
	 * @param ecSignerPhone(Y/String)
	 * @param ecSignerName(Y/String)
	 * @param ecSignerImgDataBlob(Y/byte[])
	 * @param ecSignerImgName(N/String)  					自定义签章名
	 * @param ecSignerImgType(N/Integer)					签章类型(默认大陆签章)
	 * @param overwrite(N/Boolean)							是否更新签章(默认否)
	 * @return
	 * @throws IqbException
	 */
	public abstract JSONObject uploadUserImage(Map map) throws IqbException;
	
	/**
	 * 申请用户证书(适用于个人)
	 * 
	 * @param ecSignerEmail(N/String)
	 * @param ecSignerPhone(Y/String)
	 * @param ecSignerName(Y/String)
	 * @param ecSignerCertPwd(Y/String)
	 * @param ecSignerCertType(Y/Integer)					证件类型
	 * @param ecSignerCertNo(Y/String)
	 * @param ecSignerProvince(Y/String)
	 * @param ecSignerCity(Y/String)
	 * @param ecSignerAddress(Y/String)
	 * @param ecSignerCertPeriod(N/Integer)					证书有效期(默认24个月)
	 * @return
	 * @throws IqbException
	 */
	public abstract JSONObject certificateApplyForPersonal(Map map) throws IqbException;
	
	/**
	 * 申请用户证书(适用于企业)
	 * 
	 * @param ecSignerEmail(N/String)
	 * @param ecSignerPhone(Y/String)
	 * @param ecSignerName(Y/String)
	 * @param ecSignerCertPwd(Y/String)
	 * @param linkMan(N/String)
	 * @param ecSignerCertNo(Y/String)
	 * @param bizRegNum(Y/String)							工商注册号(统一社会信用代码)
	 * @param organizationCode(Y/String)					组织机构代码(统一社会信用代码)
	 * @param taxRegNum(Y/String)							税务登记证号(统一社会信用代码)
	 * @param ecSignerProvince(Y/String)
	 * @param ecSignerCity(Y/String)
	 * @param ecSignerAddress(Y/String)
	 * @param ecSignerCertPeriod(N/Integer)					证书有效期(默认24个月)
	 * @return
	 * @throws IqbException
	 */
	public abstract JSONObject certificateApplyForEnterprise(Map map) throws IqbException;
	
	/**
	 * 上传合同
	 * 
	 * @param ecFileBlob(Y/byte[])
	 * @param ecFileName(Y/String)
	 * @param sendUser(Y/Map<String, Object>)				合同发送者[ecSignerType(Y/Integer), ecSignerEmail(N/String), ecSignerPhone(Y/String), ecSignerName(Y/String), ecTheme(Y/String), ecAbstract(N/String), ecEffectiveDays(Y/Integer), ecSenderType(Y/Integer)]
	 * @param receiveUserList(N/List<Map<String, Object>>)	合同接受者列表[ecSignerType(Y/Integer), ecSignerEmail(N/String), ecSignerPhone(Y/String), ecSignerName(Y/String)]
	 * @return
	 * @throws IqbException
	 */
	public abstract JSONObject contractUpload(Map map) throws IqbException;
	
	/**
	 * 追加合同接受者
	 * 
	 * @param tpSignid(Y/String)							合同文档编号 
	 * @param receiveUserList(Y/List<Map<String, Object>>)	合同接受者列表[ecSignerType(Y/String), ecSignerEmail(N/String), ecSignerPhone(Y/String), ecSignerName(Y/String)]
	 * @return
	 * @throws IqbException 
	 */
	public abstract JSONObject contractUploadAdd(Map map) throws IqbException;
	
	/**
	 * 自动签署(支持多处签名)
	 *
	 * @param ecSignerEmail(N/String)	
	 * @param ecSignerPhone(Y/String)				
	 * @param tpSignid(Y/String)							合同文档编号  
	 * @param coordinateList(Y/List<Map<String, Object>>)	签章坐标列表[ecSealPageNum(Y/Integer), ecSealPositionX(Y/Integer), ecSealPositionY(Y/Integer)]
	 * @param sealName(N/String)							签章名
	 * @param ecTokenUrl(N/String)							签署成功异步推送地址
	 * @return	
	 * @throws IqbException
	 */
	public abstract JSONObject autoSign(Map map) throws IqbException;
	
	/**
	 * 生成手动签署链接(支持多处签名)
	 *
	 * @param ecSignerEmail(N/String)	
	 * @param ecSignerPhone(Y/String)			
	 * @param tpSignid(Y/String)							合同文档编号
	 * @param coordinateList(Y/List<Map<String, Object>>)	签章坐标列表[ecSealPageNum(Y/Integer), ecSealPositionX(Y/Integer), ecSealPositionY(Y/Integer)]
	 * @param tpReturnUrl(Y/String)							签署成功跳转地址(不能够带有参数)
	 * @param signDeviceType(Y/Integer)						签署客户端(PC端/移动端)
	 * @param ecTokenUrl(N/String)							签署成功异步推送地址
	 * @return	
	 * @throws IqbException
	 */
	public abstract String getSignPage(Map map) throws IqbException;
	
	/**
	 * 结束合同
	 * 
	 * @param tpSignid(Y/String)							合同文档编号
	 * @return
	 * @throws IqbException
	 */
	public abstract JSONObject endContract(Map map) throws IqbException;
	
	/**
	 * 合同信息查询
	 * 
	 * @param tpSignid(Y/String)							合同文档编号
	 * @return
	 * @throws IqbException
	 */
	public abstract JSONObject contractInfo(Map map) throws IqbException;
	
	/**
	 * 生成合同预览链接
	 * 
	 * @param tpSignid(Y/String)							合同文档编号
	 * @param tpDocid(Y/String)								合同存储编号
	 * @return
	 * @throws IqbException
	 */
	public abstract String viewContract(Map map) throws IqbException;
	
	/**
	 * 生成合同下载链接(适用于下载到客户端)
	 * 
	 * @param tpSignid(Y/String)							合同文档编号
	 * @param downloadType(N/enum)							下载类型(默认PDF)
	 * @return 
	 * @throws IqbException 
	 */
	public abstract String contractDownload(Map map) throws IqbException;
	
	/**
	 * 生成合同下载字节数组(适用于下载到服务器)
	 * 
	 * @param tpSignid(Y/String)							合同文档编号
	 * @param downloadType(N/enum)							下载类型(默认PDF)
	 * @return 
	 * @throws IqbException 
	 */
	public abstract byte[] contractDownloadLocal(Map map) throws IqbException;
	
	/************************************************** 对内接口 **************************************************/
	
	/**
	 * 注册用户
	 * 
	 * @param userType(Y)
	 * @param userEmail(N)
	 * @param userMobile(Y)
	 * @param userName(Y)
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> regUser(final String userType, final String userMobile, final String userName) throws IqbException, Exception;
	
	/**
	 * 注册用户
	 * 
	 * @param userType(Y)
	 * @param userEmail(N)
	 * @param userMobile(Y)
	 * @param userName(Y)
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> regUser(final String userType, final String userMobile, final String userName, final String email) throws IqbException, Exception;
	
	/**
	 * 用户上传签章
	 * 
	 * @param userType(Y)
	 * @param userEmail(N)
	 * @param userMobile(Y)
	 * @param userName(Y)
	 * @param byts(Y)
	 * @param sealName(N)  									自定义签章名
	 * @param sealType(N)									签章类型(默认大陆签章)
	 * @param overwrite(N)									是否更新签章(默认否)
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> uploadUserImage(final String userType, final String userEmail, final String userMobile, final String userName, final byte[] data, final String sealName, final boolean overwrite, final String sealType) throws IqbException, Exception;
	
	/**
	 * 申请证书(适用于个人用户)
	 * 
	 * @param userEmail(N)
	 * @param userMobile(Y)
	 * @param userName(Y)
	 * @param password(Y)
	 * @param identType(Y)									证件类型
	 * @param identNo(Y)
	 * @param province(Y)
	 * @param city(Y)
	 * @param address(Y)
	 * @param duration(N)									证书有效期(默认24个月)
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> certificateApply(final String userEmail, final String userMobile, final String userName, final String password, final String identType, final String identNo, final String province, final String city, final String address, final String duration) throws IqbException, Exception;
	
	/**
	 * 申请证书(适用于企业用户)
	 * 
	 * @param userEmail(N)
	 * @param userMobile(Y)
	 * @param userName(Y)
	 * @param password(Y)
	 * @param linkMan(Y)
	 * @param linkIdCode(Y)
	 * @param icCode(Y)										工商注册号(统一社会信用代码)
	 * @param orgCode(Y)									组织机构代码(统一社会信用代码)
	 * @param taxCode(Y)									税务登记证号(统一社会信用代码)
	 * @param province(Y)
	 * @param city(Y)
	 * @param address(Y)
	 * @param duration(N)									证书有效期(默认24个月)
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> certificateApply(final String userEmail, final String userMobile, final String userName, final String password, final String linkMan, final String linkIdCode, final String icCode,  final String orgCode, final String taxCode, final String province, final String city, final String address, final String duration) throws IqbException, Exception;
	
	/**
	 * 上传合同文件
	 * 
	 * @param byts(Y)
	 * @param fileName(Y)
	 * @param sendUser(Y)									合同发送者
	 * @param receiveUserList(N)							合同接受者列表
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> contractUpload(final byte[] byts, final String fileName, final Map<String, Object> sendUser, final List<Map<String, Object>> receiveUserList) throws IqbException, Exception;
	
	/**
	 * 追加合同接受者
	 * 
	 * @param signId(Y)										合同文档编号 
	 * @param receiveUserList(Y)							合同接受者列表
	 * @return
	 * @throws IqbException 
	 * @throws Exception 
	 */
	protected abstract Map<String, Object> contractUpload(final String signId, final List<Map<String, Object>> receiveUserList) throws IqbException, Exception;
	
	/**
	 * 自动签名(支持多处签名)
	 *
	 * @param userAccount(Y)				
	 * @param signId(Y)										合同文档编号  
	 * @param coordinateList(Y)								签章坐标列表
	 * @param isEnd(Y)										是否结束合同
	 * @param sealName(N)									签章名
	 * @param noticeUrls(N)									签署成功异步推送地址
	 * @return	
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> autoSign(final String userAccount, final String signId, final List<Map<String, Object>> coordinateList, final boolean isEnd, final String sealName, final String noticeUrl) throws IqbException, Exception;
	
	/**
	 * 手动签名(支持多处签名)
	 *
	 * @param userAccount(Y)			
	 * @param signId(Y)										合同文档编号
	 * @param coordinateList(Y)								签章坐标列表
	 * @param returnUrl(Y)									签署成功跳转地址(不能够带有参数)
	 * @param deviceType(Y)									签署客户端(PC端/移动端)
	 * @param noticeUrl(N)									签署成功异步推送地址
	 * @return	
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract String getSignPage(final String userAccount, final String signId, final List<Map<String, Object>> coordinateList, final String returnUrl, final String deviceType, final String noticeUrl) throws IqbException, Exception;
	
	/**
	 * 结束合同
	 * 
	 * @param signId(Y)										合同文档编号
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> endContract(final String signId) throws IqbException, Exception;
	
	/**
	 * 合同信息查询
	 * 
	 * @param signId(Y)										合同文档编号
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract Map<String, Object> contractInfo(final String signId) throws IqbException, Exception;
	
	/**
	 * 合同预览
	 * 
	 * @param signId(Y)										合同文档编号
	 * @param docId(Y)										合同存储编号
	 * @return
	 * @throws IqbException
	 * @throws Exception
	 */
	protected abstract String viewContract(final String signId, final String docId) throws IqbException, Exception;
	
	/**
	 * 合同下载
	 * 
	 * @param signId(Y)										合同文档编号
	 * @param downloadType(N)								下载类型(PDF/ZIP)
	 * @return 
	 * @throws IqbException 
	 * @throws Exception 
	 */
	protected abstract String contractDownload(final String signId, final DOWNLOAD_TYPE downloadType) throws IqbException, Exception;
	
	/**
	 * 合同下载(本地)
	 * 
	 * @param signId(Y)										合同文档编号
	 * @return 
	 * @throws IqbException 
	 * @throws Exception 
	 */
	protected abstract Map<String, Object> contractDownloadLocal(final String signId, final DOWNLOAD_TYPE downloadType) throws IqbException, Exception;
}
