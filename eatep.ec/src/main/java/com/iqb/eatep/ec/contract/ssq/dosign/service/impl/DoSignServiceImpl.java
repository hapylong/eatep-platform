package com.iqb.eatep.ec.contract.ssq.dosign.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcAttr.BizConst;
import com.iqb.eatep.ec.base.EcAttr.EcSignerConst;
import com.iqb.eatep.ec.base.EcAttr.EcTemplateConst;
import com.iqb.eatep.ec.base.EcAttr.MapKeysConst.SSQContractConst;
import com.iqb.eatep.ec.base.EcAttr.StatusAttr;
import com.iqb.eatep.ec.base.EcAttr.ThirdEcConst.SSQKeysConst;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.base.config.EcConfig;
import com.iqb.eatep.ec.contract.bizretbean.EcTaskNotifyBean;
import com.iqb.eatep.ec.contract.bizretbean.SubmitEcRetBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoSignerBean;
import com.iqb.eatep.ec.contract.ecinfo.service.IEcInfoService;
import com.iqb.eatep.ec.contract.ssq.dosign.biz.DoSignBiz;
import com.iqb.eatep.ec.contract.ssq.dosign.service.IDoSignService;
import com.iqb.eatep.ec.contract.ssq.service.BestSignService;
import com.iqb.eatep.ec.contract.ssq.sign.service.EcSignFactorService;
import com.iqb.eatep.ec.contract.ssq.template.service.IEcTemplateService;
import com.iqb.eatep.ec.util.HttpUtil;
import com.iqb.eatep.ec.util.httputil.SendHttpsUtil;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.BeanUtil;
import com.iqb.etep.common.utils.StringUtil;
import com.iqb.etep.sysmanage.dict.service.ISysDictService;

/**
 * 
 * Description: 完成签署流程服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service
public class DoSignServiceImpl implements IDoSignService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(DoSignServiceImpl.class);
    
    private static final ThreadLocal<Map<String, Object>> reqThread = new ThreadLocal<Map<String,Object>>();
    
    private int i = 0;
    
    @Autowired
    private DoSignBiz doSignBiz;
    
    @Autowired
    private IEcTemplateService ecTemplateServiceImpl;
    
    @Autowired
    private EcSignFactorService ecSignFactorServiceImpl;
    
    @Autowired
    private BestSignService bestSignServiceImpl;
    
    @Autowired
    private ISysDictService sysDictService;
    
    @Autowired
    private IEcInfoService ecInfoServiceImpl;
    
    @Autowired
    private HttpUtil httpUtil;   

    @Autowired
    private EcConfig ecConfig;
    
    /**
     * 
     * Description: 调用上上签手动签署接口
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午4:12:23
     */
    private String callSSQManualSign(Map<String, String> map) throws IqbException {
        String regRetStr = null;
        try {
            regRetStr = this.bestSignServiceImpl.getSignPage(map);
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        if(regRetStr == null){
            throw new IqbException(EcReturnInfo.EC_DO_SIGN_01050002);
        }
        logger.info("调用上上签手动签署接口发送数据：{}", JSONObject.toJSONString(map));
        logger.info("调用上上签手动签署接口返回数据：{}", regRetStr);
        return regRetStr;
    }
    
    /**
     * 
     * Description: 调用上上签合同上传接口
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:48:10
     */
    private Map<String, Object> callSSQContractUpload(Map<String, Object> reqMap) throws IqbException {
        Map<String, Object> regRetMap = null;
        try {
            regRetMap = this.bestSignServiceImpl.contractUpload(reqMap);
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        this.isSSQRetSucc(regRetMap);
        JSONObject contentObjs = this.getContentFromRetMap(regRetMap);
        logger.info("调用上上签合同上传接口发送数据：{}", JSONObject.toJSONString(reqMap));
        logger.info("调用上上签合同上传接口返回数据：{}", JSONObject.toJSONString(regRetMap));
        return contentObjs;
    }

    /**
     * 
     * Description: 获取合同发送方
     * @param ecInfo 
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午3:14:32
     */
    private Map<String, Object> getContractSendUser(EcInfoBean ecInfo){
        Map<String, Object> sendUserMap = new HashMap<String, Object>();
        sendUserMap.put("ecSignerPhone", ecConfig.getSsqSenderPhone());
        sendUserMap.put("ecSignerName", getStrAfterConv(ecConfig.getSsqSenderName()));
        sendUserMap.put("ecSignerEmail", ecConfig.getSsqSenderEmail());
        sendUserMap.put("signimagetype", ecInfo.getEcSenderSignimgType());
        sendUserMap.put("ecSignerType", ecInfo.getEcSenderType());
        sendUserMap.put("userfileType", ecInfo.getEcSenderUserFiletype());
        sendUserMap.put("ecTheme", ecInfo.getEcTheme());
        sendUserMap.put("ecEffectiveDays", ecInfo.getEcEffectiveDays());
        sendUserMap.put("ecSenderType", ecInfo.getIsSenderSign());
        return sendUserMap;
    }
    
    private String getStrAfterConv(String msg){
        try {
            msg = new String(msg.getBytes("ISO-8859-1"), "UTF-8");
            msg = new String(msg.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.info("中文转码失败");
        }
        return msg;
    }
    
    /**
     * 
     * Description: 判断上上签是否返回成功
     * @param
     * @return boolean
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月3日 上午11:31:36
     */
    private void isSSQRetSucc(Map<String, Object> retMap) throws IqbException{
        if(retMap == null){
            throw new IqbException(EcReturnInfo.EC_DO_SSQ_01050005);
        }
        if(retMap.get(SSQKeysConst.SSQ_RET_INFO) == null){
            throw new IqbException(EcReturnInfo.EC_DO_SSQ_01050005);
        }
        String code = ObjectUtils.toString(((JSONObject)retMap.get(SSQKeysConst.SSQ_RET_INFO)).get(SSQKeysConst.SSQ_RET_CODE_KEY));
        if(!SSQKeysConst.SSQ_SUCC_CODE.equals(code)){
            throw new IqbException(EcReturnInfo.EC_DO_SSQ_01050005);
        }
    }
    
    /**
     * 
     * Description: 从返回信息中获取content
     * @param
     * @return JSONObject
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月3日 上午11:36:33
     */
    private JSONObject getContentFromRetMap(Map<String, Object> retMap) throws IqbException{
        JSONObject contentMap = (JSONObject) retMap.get(SSQKeysConst.SSQ_RET_CONTENT_KEY);
        if(contentMap == null){
            throw new IqbException(EcReturnInfo.EC_DO_SSQ_01050006);
        }
        return contentMap;
    }
    
    @Override
    public SubmitEcRetBean submitEc(Map<String, Object> reqObjs) throws IqbException {
        if(reqObjs == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030007);
        }
        
        /** 获取电子合同列表  **/
        List<EcInfoBean> ecInfoList = this.getEcInfoList(reqObjs);
        logger.info("从数据库中查询出来的电子合同信息为：{}", JSONObject.toJSONString(ecInfoList));
        /** 进行线程缓存  **/
        reqThread.set(reqObjs);
        
        /** 执行合同发送和用户签署，并返回手动签署地址  **/
        SubmitEcRetBean submitEcRetBean = this.doSendContractAndSign(ecInfoList);
        
        logger.info("提交合同处理完毕，回调业务地址:{}，回调内容:{}", reqObjs.get(ecInfoList.get(0).getEcNotifyUrl()), JSONObject.toJSONString(submitEcRetBean));
        
        try {
            SendHttpsUtil.postMsg4GetMap(ecInfoList.get(0).getEcNotifyUrl(), JSONObject.toJSONString(submitEcRetBean));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("回调业务系统发生异常", e);
            throw new IqbException(EcReturnInfo.EC_BIZ_01030001);
        }
        
        return submitEcRetBean;
    }
    
    /**
     * 
     * Description: 执行发送电子合同和用户签署（自动签署）
     * @param
     * @return List<Map<String,String>>
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午5:54:45
     */
    private SubmitEcRetBean doSendContractAndSign(List<EcInfoBean> ecInfoList) throws IqbException {
        /** 初始化返回数据  **/
        SubmitEcRetBean submitEcRetBean = this.getSubmitEcRetBean();
        List<Map<String, Object>> ecList = new ArrayList<Map<String,Object>>();
        
        /** 遍历电子模板列表，逐个进行处理  **/
        for(EcInfoBean ecInfo : ecInfoList){
            if(EcTemplateConst.EC_TYPE_ZZ.equals(ecInfo.getEcType())){
                ecInfo.setStatus(Integer.parseInt(StatusAttr.EC_INFO_STATUS_SIGN_FINISH));
                Integer id = this.ecInfoServiceImpl.updateContractEcInfo(ecInfo);
                if(id <= 0){
                    continue;
                }
                /** 判断同一次bizId业务请求是否所有电子合同都完成了签署  **/
                boolean isAllEcFinishByBizId = isAllEcFinishByBizId(ecInfo.getBizId(), ecInfo.getBizType(), ecInfo.getOrgCode());
                if(isAllEcFinishByBizId == true){
                    this.notifyBizEcFinish(ecInfo.getBizId(), ecInfo.getBizType(), ecInfo.getOrgCode());
                }
                continue;
            }
            /** 合同发送  **/
            List<Map<String, Object>> contlist = this.sendContract(ecInfo);
            if(contlist == null){
                throw new IqbException(EcReturnInfo.EC_DO_SSQ_01050005);
            }
            this.updateEcInfoAndEcSignerInfo(contlist, ecInfo);
            
            /** 初始化返回信息  **/
            Map<String, Object> ecMap = new HashMap<String, Object>();
            ecMap.put(BizConst.EC_NAME_KEY, ecInfo.getEcFileName());
            ecMap.put(BizConst.EC_CODE_KEY, ecInfo.getEcContractNo());
            
            /** 开始签名，返回签名结果  **/
            List<Map<String, String>> ecSignerSignUrlList = this.doSign(ecInfo);
            ecMap.put(BizConst.EC_SIGNER_SIGN_URL_LIST_KEY, ecSignerSignUrlList);
            ecList.add(ecMap);
        }
        
        submitEcRetBean.setEcList(ecList);
        submitEcRetBean.setStatus(StatusAttr.COMMON_YES);
        return submitEcRetBean;
    }
    
    /**
     * 
     * Description: 判断同一次bizId业务请求是否所有电子合同都完成了签署
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午3:41:55
     */
    private boolean isAllEcFinishByBizId(String bizId, Integer bizType, String orgCode) throws IqbException{
        List<EcInfoBean> ecInfoList = this.ecInfoServiceImpl.listEcInfo(bizId, bizType, orgCode);
        logger.info("判断同一次bizId业务请求是否所有电子合同都完成了签署, 查询数据库结果为:{}", JSONObject.toJSONString(ecInfoList));
        if(CollectionUtils.isEmpty(ecInfoList)){
            logger.info("判断同一次bizId业务请求是否所有电子合同都完成了签署, 查询数据库结果为空.");
            return false;
        }
        for(EcInfoBean ecInfoBean : ecInfoList){
            if(ecInfoBean.getStatus() != Integer.parseInt(StatusAttr.EC_INFO_STATUS_SIGN_FINISH)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * Description: 通知业务系统bizid对应合同已经全部完成签署
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午4:04:21
     */
    private void notifyBizEcFinish(String bizId, Integer bizType, String orgCode) throws IqbException{
        
        /** 获取业务参数  **/
        if(StringUtil.isEmpty(bizId) || StringUtil.isEmpty(orgCode)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030014);
        }
        
        /** 初始化返回信息  **/
        EcTaskNotifyBean ecTaskNotifyBean = new EcTaskNotifyBean();
        ecTaskNotifyBean.setBizId(bizId);
        ecTaskNotifyBean.setBizType(bizType.toString());
        ecTaskNotifyBean.setOrgCode(orgCode);
        
        /** 根据传入信息获取电子合同列表  **/
        List<EcInfoBean> ecInfoList = this.ecInfoServiceImpl.listEcInfo(bizId, bizType, orgCode);
        /** 电子合同列表  **/
        List<Map<String, Object>> ecList = new ArrayList<Map<String,Object>>();
        /** 遍历电子合同列表,获取签署人信息  **/
        for(EcInfoBean ecInfo : ecInfoList){
            /** 封装ecmap **/
            Map<String, Object> ecMap = new HashMap<String, Object>();
            ecMap.put(BizConst.EC_NAME_KEY, ecInfo.getEcFileName());
            ecMap.put(BizConst.EC_CODE_KEY, ecInfo.getEcContractNo());
            ecMap.put(BizConst.EC_VIEW_URL, ecInfo.getEcViewUrl());
            ecList.add(ecMap);
        }
        ecTaskNotifyBean.setEcList(ecList);
        ecTaskNotifyBean.setStatus(StatusAttr.COMMON_YES);
        logger.info("回调通知信息：{}", JSONObject.toJSONString(ecTaskNotifyBean));
        try {
            i++;
            SendHttpsUtil.postMsg4GetMap(ecInfoList.get(0).getEcTaskUrl(), JSONObject.toJSONString(ecTaskNotifyBean));
        } catch (Exception e) {
            if(i < 6){
                this.notifyBizEcFinish(bizId, bizType, orgCode);
            }
        }
        
    }
    
    /**
     * 
     * Description: 更新电子合同信息和电子合同签署人信息
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月3日 下午3:11:03
     */
    private void updateEcInfoAndEcSignerInfo(List<Map<String, Object>> contlist, EcInfoBean ecInfo) throws IqbException {
        /** 更新电子合同信息  **/
        String docId = ObjectUtils.toString(((JSONObject)contlist.get(0).get(SSQKeysConst.SSQ_RET_CONTINFO)).get(SSQKeysConst.SSQ_RET_DOCiD_KEY));
        String signId = ObjectUtils.toString(((JSONObject)contlist.get(0).get(SSQKeysConst.SSQ_RET_CONTINFO)).get(SSQKeysConst.SSQ_RET_SIGNID_KEY));
        ecInfo.setTpDocid(docId);
        ecInfo.setTpSignid(signId);
        this.ecInfoServiceImpl.updateContractEcInfo(ecInfo);
        this.ecInfoServiceImpl.updateEcSignerInfoSetDocId(ecInfo);
        
        
    }

    /**
     * 
     * Description: 获取ec提交返回信息
     * @param
     * @return SubmitEcRetBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午6:38:17
     */
    private SubmitEcRetBean getSubmitEcRetBean() {
        /** 获取请求数据  **/
        Map<String, Object> reqObjs = reqThread.get();
        /** 封装生成合同返回数据bean  **/
        SubmitEcRetBean submitEcRetBean = new SubmitEcRetBean();
        submitEcRetBean.setBizId(ObjectUtils.toString(reqObjs.get(BizConst.BIZ_ID_KEY)));
        submitEcRetBean.setOrgCode(ObjectUtils.toString(reqObjs.get(BizConst.ORG_CODE_KEY)));
        submitEcRetBean.setBizType(ObjectUtils.toString((reqObjs.get(BizConst.EC_BIZ_TYPE))));
        return submitEcRetBean;
    }

    /**
     * 
     * Description: 进行签名
     * @param
     * @return List<Map<String,String>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午6:37:25
     */
    private List<Map<String, String>> doSign(EcInfoBean ecInfo) throws IqbException {
        /** 获取需要收件人列表  **/
        List<EcInfoSignerBean> ecInfoSignerList = this.ecInfoServiceImpl.listEcSignerByEcInfoId(ecInfo.getId());
        
        /** 获取ec签署方列表  **/
        List<Map<String, String>> ecManualSignList = new ArrayList<Map<String,String>>();
        for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
            if(EcTemplateConst.EC_SIGN_TYPE_AUTO.equals(ecInfoSignerBean.getEcSignType())){
                this.callSSQAutoSign(ecInfoSignerBean);
                ecInfoSignerBean.setManualSignUrl(null);
                ecInfoSignerBean.setTpUserStatusAsyn(StatusAttr.EC_ASYN_SIGN_SUCC);
                this.simulationSSQNotifyForAutoSign(ecInfoSignerBean.getTpSignid(), ecInfoSignerBean.getEcSignerPhone());
            }else{
                String ecManualSignUrl = this.callSSQManualSign(BeanUtil.entity2map(ecInfoSignerBean));
                Map<String, String> ecSignMap = new HashMap<String, String>();
                ecSignMap.put(EcSignerConst.EC_SIGNER_CODE_KEY, ecInfoSignerBean.getEcSignerCode());
                ecSignMap.put(EcSignerConst.EC_SIGNER_SIGN_URL_KEY, ecManualSignUrl);
                ecManualSignList.add(ecSignMap);
                ecInfoSignerBean.setManualSignUrl(ecManualSignUrl);
            }
            this.ecInfoServiceImpl.updateContractEcSignerInfo(ecInfoSignerBean);
        }
        
        /** 生成合同预览地址并记录到数据库  **/
        String ecViewUrl = this.getSSQEcViewUrl(BeanUtil.entity2map(ecInfo));
        ecInfo.setEcViewUrl(ecViewUrl);
        String ecDownloadUrl = this.getSSQEcDownloadUrl(BeanUtil.entity2map(ecInfo));
        ecInfo.setEcDownloadUrl(ecDownloadUrl);
        this.ecInfoServiceImpl.updateContractEcInfo(ecInfo);
        
        return ecManualSignList;
    }
    
    /**
     * 
     * Description: 为自动签署模拟上上签异步通知
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年5月8日 下午4:32:26
     */
    private void simulationSSQNotifyForAutoSign(String docId, String user){
        JSONObject objs = new JSONObject();
        objs.put(SSQKeysConst.SSQ_RET_DOCID_KEY, docId);
        objs.put(SSQKeysConst.SSQ_RET_USER_KEY, user);
        objs.put(SSQKeysConst.SSQ_RET_CODE_KEY, SSQKeysConst.SSQ_SUCC_CODE);
        SendHttpsUtil.postMsg4GetMap(ecConfig.getEcTokenUrlSsq(), JSONObject.toJSONString(objs));
    }

    /**
     * 
     * Description: 获取上上签电子合同预览地址
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午4:12:23
     */
    private String getSSQEcViewUrl(Map<String, String> map) throws IqbException {
        String regRetStr = null;
        try {
            regRetStr = this.bestSignServiceImpl.viewContract(map);
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        if(regRetStr == null){
            throw new IqbException(EcReturnInfo.EC_DO_SIGN_01050002);
        }
        logger.info("调用上上签查看合同接口发送数据：{}", JSONObject.toJSONString(map));
        logger.info("调用上上签查看合同接口返回数据：{}", regRetStr);
        return regRetStr;
    }
    
    /**
     * 
     * Description: 合同下载地址
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午3:13:52
     */
    private String getSSQEcDownloadUrl(Map<String, String> map) throws IqbException {
        String regRetStr = null;
        try {
            regRetStr = this.bestSignServiceImpl.contractDownload(map);
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        if(regRetStr == null){
            throw new IqbException(EcReturnInfo.EC_DO_SIGN_01050002);
        }
        logger.info("调用上上签查看合同接口发送数据：{}", JSONObject.toJSONString(map));
        logger.info("调用上上签查看合同接口返回数据：{}", regRetStr);
        return regRetStr;
    }
    
    /**
     * 
     * Description: 调用上上签自动签署接口
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 上午10:24:45
     */
    private Object callSSQAutoSign(EcInfoSignerBean ecInfoSignerBean) throws IqbException {
        Map<String, Object> regRetMap = null;
        try {
            regRetMap = this.bestSignServiceImpl.autoSign(BeanUtil.entity2map(ecInfoSignerBean));
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        this.isSSQRetSucc(regRetMap);
        JSONObject contentObjs = this.getContentFromRetMap(regRetMap);
        logger.info("调用上上签自动签署接口发送数据：{}", JSONObject.toJSONString(ecInfoSignerBean));
        logger.info("调用上上签自动签署接口返回数据：{}", JSONObject.toJSONString(regRetMap));
        return contentObjs;
    }
    
    /**
     * 
     * Description: 合同发送
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 上午10:23:24
     */
    private List<Map<String, Object>> sendContract(EcInfoBean ecInfo) throws IqbException {
        logger.info("执行合同发送至上上签，合同信息：{}", JSONObject.toJSONString(ecInfo));
        /** 获取合同发送方信息  **/
        Map<String, Object> sendUser = this.getContractSendUser(ecInfo);
        /** 获取ec签署方列表  **/
        List<EcInfoSignerBean> ecInfoSignerList = this.ecInfoServiceImpl.listEcSignerByEcInfoId(ecInfo.getId());
        List<Map> ecInfoSignerMapList = new ArrayList<Map>();
        for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
            ecInfoSignerMapList.add(BeanUtil.entity2map(ecInfoSignerBean));
        }
        
        logger.info("从数据库中查询出来的签署人信息为：{}", JSONObject.toJSONString(ecInfoSignerList));
        
        /** 数据封装  **/
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put(SSQContractConst.ECFILEBLOB, ecInfo.getEcFileBlob());
        reqMap.put(SSQContractConst.FILENAME, ecInfo.getEcFileName());
        reqMap.put(SSQContractConst.SENDUSER, sendUser);
        reqMap.put(SSQContractConst.USERLIST, ecInfoSignerMapList);
        
//        test-do
        /** 调用上上签合同上传接口  **/
        Map<String, Object> contentObjs =  this.callSSQContractUpload(reqMap);
        Object contlistObjs = contentObjs.get(SSQContractConst.CONTLIST);
        if(contlistObjs == null){
            return null;
        }
        return (List<Map<String, Object>>)contlistObjs;
    }
    

    /**
     * 
     * Description: 获取电子合同信息列表
     * @param
     * @return List<EcInfoBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午5:45:05
     */
    private List<EcInfoBean> getEcInfoList(Map<String, Object> reqObjs) throws IqbException{
        /** 从请求中获取业务参数  **/
        String bizId = ObjectUtils.toString(reqObjs.get(BizConst.BIZ_ID_KEY));
        Integer bizType = Integer.parseInt(ObjectUtils.toString(reqObjs.get(BizConst.EC_BIZ_TYPE)));
        String orgCode = ObjectUtils.toString(reqObjs.get(BizConst.ORG_CODE_KEY));
        
        /** 获取电子合同列表  **/
        List<EcInfoBean> ecInfoList = (List<EcInfoBean>) this.ecInfoServiceImpl.listEcInfo(bizId, bizType, orgCode);
        if(CollectionUtils.isEmpty(ecInfoList)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030011);
        }
        return ecInfoList;
    }

}
