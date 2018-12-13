package com.iqb.eatep.ec.contract.ssq.template.service.impl;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.apiinterface.EcApiInter;
import com.iqb.eatep.ec.base.EcAttr.BizConst;
import com.iqb.eatep.ec.base.EcAttr.DictConst;
import com.iqb.eatep.ec.base.EcAttr.EcSignerConst;
import com.iqb.eatep.ec.base.EcAttr.EcTemplateConst;
import com.iqb.eatep.ec.base.EcAttr.MapKeysConst.SSQUserInfo;
import com.iqb.eatep.ec.base.EcAttr.StatusAttr;
import com.iqb.eatep.ec.base.EcAttr.ThirdEcConst.SSQKeysConst;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.base.config.EcConfig;
import com.iqb.eatep.ec.bizconfig.bean.BizConfigBean;
import com.iqb.eatep.ec.bizconfig.service.BizConfigService;
import com.iqb.eatep.ec.contract.bizretbean.GenerateContractRetBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoSignerBean;
import com.iqb.eatep.ec.contract.ecinfo.service.IEcInfoService;
import com.iqb.eatep.ec.contract.ssq.bean.EcTemplateBean;
import com.iqb.eatep.ec.contract.ssq.service.BestSignService;
import com.iqb.eatep.ec.contract.ssq.sign.bean.EcSignerEntity;
import com.iqb.eatep.ec.contract.ssq.sign.service.EcSignFactorService;
import com.iqb.eatep.ec.contract.ssq.template.biz.ContractNoBiz;
import com.iqb.eatep.ec.contract.ssq.template.biz.EcTemplateBiz;
import com.iqb.eatep.ec.contract.ssq.template.service.IEcTemplateService;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateBean;
import com.iqb.eatep.ec.contract.template.bean.ContractTemplateSignerBean;
import com.iqb.eatep.ec.contract.template.biz.ContractTemplateSignerBiz;
import com.iqb.eatep.ec.util.CommonUtil;
import com.iqb.eatep.ec.util.EcSpringUtil;
import com.iqb.eatep.ec.util.poi.WordPoiUtil;
import com.iqb.etep.common.base.config.BaseConfig;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.BeanUtil;
import com.iqb.etep.common.utils.StringUtil;
import com.iqb.etep.sysmanage.dict.bean.SysDictItem;
import com.iqb.etep.sysmanage.dict.service.ISysDictService;

/**
 * 
 * Description: ec模板服务实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service
public class EcTemplateServiceImpl implements IEcTemplateService{
    
    private static final Logger logger = LoggerFactory.getLogger(EcTemplateServiceImpl.class);
    
    private static final ThreadLocal<Map<String, String>> contractNoThread = new ThreadLocal<Map<String,String>>();
    
    private static final ThreadLocal<Map<String, Object>> reqThread = new ThreadLocal<Map<String,Object>>();
    
    private static final ThreadLocal<List<EcInfoSignerBean>> ecInfoSignerBeanThread = new ThreadLocal<List<EcInfoSignerBean>>();
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    
    @Autowired
    private EcTemplateBiz ecTemplateBiz;

    @Autowired
    private EcSignFactorService ecSignFactorServiceImpl;
    
    @Autowired
    private ISysDictService sysDictService;
    
    @Autowired
    private BestSignService bestSignServiceImpl;
    
    @Autowired
    private ContractTemplateSignerBiz contractTemplateSignerBiz;
    
    @Autowired
    private IEcInfoService ecInfoServiceImpl;
    
    @Autowired
    private BizConfigService bizConfigServiceImpl;
    
    @Autowired
    private EcConfig ecConfig; 
    
    @Autowired
    private ContractNoBiz contractNoBiz; 
    
    @Autowired
    private BaseConfig baseConfig;
    
    /**
     * 
     * Description: 根据查询条件获取ec模板列表
     * @param
     * @return List<EcTemplateBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午2:51:21
     */
    @SuppressWarnings("unused")
    @Deprecated
    private List<EcTemplateBean> getEcTemplateListByCons(Map<String, String> reqObjs) throws IqbException{
        List<EcTemplateBean> templateList = this.ecTemplateBiz.getEcTemplateListByCons(reqObjs);
        if(CollectionUtils.isEmpty(templateList)){
            logger.error("模板变量替换查询到的模板列表为空");
            throw new IqbException(EcReturnInfo.EC_POI_01010004); 
        }
        return templateList;
    }

    @Override
    public EcTemplateBean getEcTemplateById(String id) throws IqbException {
        if(StringUtil.isEmpty(id)){
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecTemplateBiz.getEcTemplateById(id);
    }

    @Override
    public EcTemplateBean getEcTemplateByTemplateCode(String templateCode) throws IqbException {
        if(StringUtil.isEmpty(templateCode)){
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecTemplateBiz.getEcTemplateByTemplateCode(templateCode);
    }

    @Override
    public GenerateContractRetBean generateEc(Map<String, Object> reqObjs) throws IqbException {
        logger.info("生成合同入参:" + JSONObject.toJSONString(reqObjs));
        if(reqObjs == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030007);
        }
        /** 获取合同模板列表  **/
        List<BizConfigBean> bizConfigList = this.bizConfigServiceImpl.selectToListOfUnionBeanByParameterMap(reqObjs);
        if(CollectionUtils.isEmpty(bizConfigList)){
            throw new IqbException(EcReturnInfo.EC_COMMON_01000004);
        }
        BizConfigBean bizConfigBean = bizConfigList.get(0);
        
        List<ContractTemplateBean> contractTemplateList = bizConfigBean.getContractTemplateBeanList();
        
        if(CollectionUtils.isEmpty(contractTemplateList)){
            throw new IqbException(EcReturnInfo.EC_POI_01010004);
        }
        logger.info("从数据库中查询出来的合同模板信息为：{}", JSONObject.toJSONString(contractTemplateList));
        /** 进行线程缓存  **/
        reqThread.set(reqObjs);
        
        /** 持久化签署人信息  **/
        this.persistSignerInfo();
        
        /** 电子合同列表  **/
        List<EcInfoBean> ecInfoList = new ArrayList<EcInfoBean>();
        
        /** 清空之前生成的合同信息  **/
        EcInfoBean delEcInfoBean = new EcInfoBean();
        delEcInfoBean.setBizId(ObjectUtils.toString(reqObjs.get(BizConst.BIZ_ID_KEY)));
        delEcInfoBean.setBizType(Integer.parseInt(ObjectUtils.toString(reqObjs.get(BizConst.EC_BIZ_TYPE))));
        this.ecInfoServiceImpl.deleteEcInfo(delEcInfoBean);
        this.ecInfoServiceImpl.deleteEcInfoSigner(delEcInfoBean);
        
        String storeNo = ObjectUtils.toString(reqObjs.get(BizConst.STORE_NO));
        if(StringUtils.isEmpty(storeNo)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030026);
        }
        
        //1.减少锁内容 
        //2.减少回滚风险
        //3.合同编号是否可以不连续，合同号是否可以作废
        //4.合同号  编号生产规则  生成器
        //5.加锁的位置
        
        synchronized (storeNo) {
            try {
                
                String contractNoSeq = contractNoBiz.getContractSeq(storeNo);
                
                /** 进行合同信息持久化到数据库中的电子合同表,并进行缓存  **/
                for(int i = 0 ; i < contractTemplateList.size() ; i++){
                    ContractTemplateBean ecTemplateBean = contractTemplateList.get(i);
                    logger.info("正在处理处理合同,{}", JSONObject.toJSONString(ecTemplateBean));
                    EcInfoBean ecInfoBean = this.persistTplToEc(bizConfigBean.getId(), ecTemplateBean, storeNo, sdf.format(new Date()) + contractNoSeq);
                    ecInfoList.add(ecInfoBean);
                }
                
                /** 获取电子合同签署人信息列表  **/
                List<EcInfoSignerBean> ecInfoSignerList = this.getEcSignerInfoList(reqObjs);
                logger.info("从数据库中查询出来的签署人信息为：{}", JSONObject.toJSONString(ecInfoSignerList));
                
                /** 进行线程缓存  **/
                reqThread.set(reqObjs);
                
                /** 执行用户注册和ca证书申请  **/
                this.doUserRegAndCA(ecInfoSignerList);
                
                /** 生成电子合同预览html **/
                GenerateContractRetBean generateContractRetBean = this.getGenerateContractRet(ecInfoList);
                
                contractNoBiz.addContractSeq(storeNo);
                
                return generateContractRetBean;
                
            } catch (IqbException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
        }
    }
    

    /**
     * 
     * Description: 执行用户注册和ca证书申请
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午4:54:53
     */
    private void doUserRegAndCA(List<EcInfoSignerBean> ecInfoSignerList) throws IqbException{
        
        /** 执行用户注册  **/
        this.doUserRegToSSQ(ecInfoSignerList);
        
        /** 执行证书申请  **/
        this.doCA(ecInfoSignerList);
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
     * Description: 调用上上签申请证书接口
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午2:35:19
     */
    private void callSSQCA(Map userInfo) throws IqbException {
        Map<String, Object> regRetMap = null;
        try {
            regRetMap = this.bestSignServiceImpl.certificateApplyForPersonal(userInfo);
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        this.isSSQRetSucc(regRetMap);
        logger.info("调用上上签申请证书接口发送数据：{}", JSONObject.toJSONString(userInfo));
        logger.info("调用上上签申请证书接口返回数据：{}", JSONObject.toJSONString(regRetMap));
    }
    
    
    /**
     * 
     * Description: 调用上上签申请证书接口
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午2:35:19
     */
    private void callSSQCAForEnterprise(Map userInfo) throws IqbException {
        Map<String, Object> regRetMap = null;
        try {
            regRetMap = this.bestSignServiceImpl.certificateApplyForEnterprise(userInfo);
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        this.isSSQRetSucc(regRetMap);
        logger.info("调用上上签申请证书接口发送数据：{}", JSONObject.toJSONString(userInfo));
        logger.info("调用上上签申请证书接口返回数据：{}", JSONObject.toJSONString(regRetMap));
    }
    

    /**
     * 
     * Description: 调用上上签接口为用户申请ca证书
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午5:08:00
     */
    private void doCA(List<EcInfoSignerBean> ecInfoSignerList) throws IqbException {
        if(ecInfoSignerList == null || ecInfoSignerList.size() == 0){
            return;
        }
        for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
            /** 获取签署方代码  **/
            String factorCode = ecInfoSignerBean.getEcSignerCode();
            /** 获取签署方信息  **/
            EcSignerEntity ecSignFactorBean = this.ecSignFactorServiceImpl.getEcSignFactorByFactorCode(factorCode);
            if(ecSignFactorBean == null){
                throw new IqbException(EcReturnInfo.EC_BIZ_01030013);
            }
            if(StatusAttr.IS_APPLY_COMPLETE_YES.equals(ecSignFactorBean.getIsApplyCertComplete())){
               continue; 
            }
            /** 调用申请证书接口  **/
            Map userInfo = BeanUtil.entity2map(ecInfoSignerBean);
            if(DictConst.EC_SIGNER_TYPE_3.equals(ecSignFactorBean.getEcSignerType()) || DictConst.EC_SIGNER_TYPE_2.equals(ecSignFactorBean.getEcSignerType())){
                this.callSSQCAForEnterprise(userInfo);
            }else{
                this.callSSQCA(userInfo);
            }
            userInfo.put(EcSignerConst.APPLY_CERT_COMPLETE_KEY, StatusAttr.IS_APPLY_COMPLETE_YES);
            this.ecSignFactorServiceImpl.updateFactorInfo(userInfo);
        }
    }

    /**
     * 
     * Description: 调用上上签用户注册接口
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午5:06:26
     */
    private void doUserRegToSSQ(List<EcInfoSignerBean> ecInfoSignerList) throws IqbException {
        if(ecInfoSignerList == null || ecInfoSignerList.size() == 0){
            return;
        }
        for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
            /** 获取签署方代码  **/
            String factorCode = ecInfoSignerBean.getEcSignerCode();
            /** 获取签署方信息  **/
            EcSignerEntity ecSignFactorBean = this.ecSignFactorServiceImpl.getEcSignFactorByFactorCode(factorCode);
            if(ecSignFactorBean == null){
                throw new IqbException(EcReturnInfo.EC_BIZ_01030013);
            }
            if(Integer.parseInt(StatusAttr.FACTOR_STATUS_HASREG) == ecSignFactorBean.getEcSignerStatus()){
               continue; 
            }
            /** 调用注册接口  **/
            Map userInfo = BeanUtil.entity2map(ecInfoSignerBean);
            
            //test-do
            String ssqUid = this.callSSQUserReg(userInfo);
            
            userInfo.put(SSQUserInfo.SSQ_USER_ID, ssqUid);
            this.ecSignFactorServiceImpl.updateFactorSsqUid(userInfo);
        }
    }

    
    /** 持久化签署人信息  
     * @throws IqbException **/
    private void persistSignerInfo() throws IqbException {
        /** 获取请求数据  **/
        Map<String, Object> reqObjs = reqThread.get();
        /** 获取用户信息列表  **/
        List<Map<String, Object>> signUserList = (List<Map<String, Object>>) reqObjs.get(BizConst.EC_SIGNER_LIST);
        if(signUserList == null || signUserList.size() == 0){
            return;
        }
        for(Map<String, Object> userInfo : signUserList){
            /** 获取签署方代码  **/
            String factorCode = ObjectUtils.toString(userInfo.get(BizConst.EC_SIGNER_CODE));
            /** 获取签署方类型  **/
            String factorType = ObjectUtils.toString(userInfo.get(BizConst.EC_SIGNER_TYPE));
            /** 获取签署方信息  **/
            EcSignerEntity ecSignFactorBean = this.ecSignFactorServiceImpl.getEcSignFactorByFactorCode(factorCode);
            if(ecSignFactorBean == null){
                /** 从业务实现类中获取用户信息  **/
                String bizImplClassName = this.getClassNameFromBizImplClass(factorType);
                userInfo = this.getSignerInfoFromBizImplClass(bizImplClassName, factorCode);
                logger.info("从实现类中取出的用户信息为：" + JSONObject.toJSONString(userInfo));
                this.ecSignFactorServiceImpl.insertFactorInfo(userInfo);
            }
        }
    }

    /**
     * 
     * Description: 从业务实现类中获取实现类名称
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月23日 下午9:13:54
     */
    private String getClassNameFromBizImplClass(String ecSignerType) throws IqbException{
        Map<String, String> bizImplMap = this.getBizImplClassNameMap();
        return ObjectUtils.toString(bizImplMap.get(ecSignerType));
    }

    /**
     * 
     * Description: 获取业务实现类名
     * @param
     * @return List<Map<String,String>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月23日 下午9:02:53
     */
    private Map<String, String> getBizImplClassNameMap() throws IqbException{
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<SysDictItem> ecSignerDictList = this.sysDictService.selectSysDictTypeToListOfBeanFormRedis(this.getBizImplClassDictKey(DictConst.EC_SIGNER_IMPL_CLASS));
            for(SysDictItem sysDictItem : ecSignerDictList){
                if(StringUtil.isEmpty(sysDictItem.getDictCode())){
                    continue;
                }
                map.put(sysDictItem.getDictCode(), sysDictItem.getDictValue());
            }
        } catch (IqbException e) {
            throw new IqbException(EcReturnInfo.EC_BIZ_01030005);
        }
        return map;
    }
    
    /**
     * 
     * Description: 获取字典key获取业务组合
     * @param
     * @return JSONObject
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月23日 下午9:33:55
     */
    private JSONObject getBizImplClassDictKey(String dictTypeCode){
        JSONObject objs = new JSONObject();
        objs.put(DictConst.DICT_TYPE_CODE, dictTypeCode);
        return objs;
    }

    /**
     * 
     * Description: 获取签署人信息
     * @param
     * @return Map<String,String>
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月23日 下午9:18:09
     */
    private Map<String, Object> getSignerInfoFromBizImplClass(String bizImplClassName, String signerCode) throws IqbException{
        if(StringUtil.isEmpty(bizImplClassName)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030006);
        }
        EcApiInter ecApiInter = (EcApiInter)EcSpringUtil.getBean(bizImplClassName);
        Map<String, Object> ecSignerInfo = ecApiInter.getEcSignerInfo(signerCode);
        if(ecSignerInfo == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030006);
        }
        return ecSignerInfo;
    }
    
    /**
     * 
     * Description: 调用上上签用户注册接口
     * @param
     * @return boolean
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午5:33:03
     */
    private String callSSQUserReg(Map<String, Object> userInfo) throws IqbException {
        Map<String, Object> regRetMap = null;
        try {
            regRetMap = this.bestSignServiceImpl.regUser(userInfo);
        } catch (Exception e) {
            if(e instanceof IqbException){
                throw (IqbException)e;
            }
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        if(regRetMap == null){
            throw new IqbException(EcReturnInfo.EC_DO_SIGN_01050002);
        }
        if(!regRetMap.containsKey(SSQKeysConst.SSQ_RET_CONTENT_KEY)){
            throw new IqbException(EcReturnInfo.EC_DO_SIGN_01050002);
        }
        logger.info("调用上上签注册接口发送数据：{}", JSONObject.toJSONString(userInfo));
        logger.info("调用上上签注册接口返回数据：{}", JSONObject.toJSONString(regRetMap));
        return ObjectUtils.toString(((Map<String, Object>)regRetMap.get(SSQKeysConst.SSQ_RET_CONTENT_KEY)).get(SSQKeysConst.SSQ_UID));
    }
    

    /**
     * 
     * Description: 获取电子合同返回
     * @param
     * @return GenerateContractRetBean
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 上午9:47:58
     */
    private GenerateContractRetBean getGenerateContractRet(List<EcInfoBean> ecInfoList) throws IqbException {
        logger.info("生成电子合同接口返回数据处理传入信息:{}", JSONObject.toJSONString(ecInfoList));
        if(CollectionUtils.isEmpty(ecInfoList)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030008);
        }
        
        /** 返回数据初始化  **/
        GenerateContractRetBean generateContractRetBean = this.getGenerateContractRetBean();
        List<Map<String, String>> ecViewList = new ArrayList<Map<String,String>>();
        
        /** 合同信息列表  **/
        for(int i = 0 ; i < ecInfoList.size() ; i++){
            EcInfoBean ecInfoBean = ecInfoList.get(i);
            Map<String, String> ecInfoMap = this.getGenerateEcViewRet(ecInfoBean);
            ecViewList.add(ecInfoMap);
        }
        generateContractRetBean.setEcList(ecViewList);
        
        logger.info("电子合同返回信息：{}", JSONObject.toJSONString(generateContractRetBean));
        
        return generateContractRetBean;
    }
    
    /**
     * 
     * Description: 获取生成电子合同返回预览信息
     * @param
     * @return Map<String,String>
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 上午10:00:29
     */
    private Map<String, String> getGenerateEcViewRet(EcInfoBean ecInfoBean) throws IqbException {
        Map<String, String> map = new HashMap<String, String>();
        map.put(BizConst.EC_NAME_KEY, ecInfoBean.getEcFileName());
        map.put(BizConst.EC_CODE_KEY, ecInfoBean.getEcContractNo());
        map.put(BizConst.EC_URL_KEY, this.getEcViewUrl(ecInfoBean));
        return map;
    }
    
    /**
     * 
     * Description: 获取电子合同预览url
     * @param
     * @return String
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 上午10:04:58
     */
    private String getEcViewUrl(EcInfoBean ecInfoBean) throws IqbException{
        String htmlFileName = ecInfoBean.getEcContractNo();
        try {
            return WordPoiUtil.getHtmlUrlFromWord(baseConfig.getCommon_basedir(), null, ecInfoBean.getEcFileName(), new ByteArrayInputStream(ecInfoBean.getEcFileBlob()), htmlFileName);
        } catch (IqbException e) {
            e.printStackTrace();
            throw new IqbException(EcReturnInfo.EC_BIZ_01030009);
        }
    }
    
    /**
     * 
     * Description: 下载地址
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午4:02:53
     */
    private String getEcDownloadUrl(EcInfoBean ecInfoBean) throws IqbException{
        String ecFileName = ecInfoBean.getEcFileName();
        String ecContractNo = ecInfoBean.getEcContractNo();
        return WordPoiUtil.getTempServiceUrl() + ecContractNo + ecFileName;
    }

    /**
     * 
     * Description: 获取合同返回信息bean
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 上午9:55:20
     */
    private GenerateContractRetBean getGenerateContractRetBean(){
        /** 获取请求数据  **/
        Map<String, Object> reqObjs = reqThread.get();
        /** 封装生成合同返回数据bean  **/
        GenerateContractRetBean generateContractRetBean = new GenerateContractRetBean();
        generateContractRetBean.setBizId(ObjectUtils.toString(reqObjs.get(BizConst.BIZ_ID_KEY)));
        generateContractRetBean.setOrgCode(ObjectUtils.toString(reqObjs.get(BizConst.ORG_CODE_KEY)));
        generateContractRetBean.setBizType(ObjectUtils.toString((reqObjs.get(BizConst.EC_BIZ_TYPE))));
        return generateContractRetBean;
    }

    /**
     * 
     * Description: 将合同信息持久化到电子合同表
     * @param integer 
     * @param contractNoSeq 
     * @param storeNo 
     * @param
     * @return Object
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月24日 下午4:15:45
     */
    @Transactional
    private EcInfoBean persistTplToEc(Integer bizConfigId, ContractTemplateBean ecTemplateBean, String storeNo, String contractNoSeq) throws IqbException{
        EcInfoBean ecInfoBean = new EcInfoBean();
        /** 将签署人信息同步到线程缓存中  **/
        this.persistSignerToThread(ecTemplateBean, null);
        
        /** 封装业务传递的请求数据  **/
        Map<String, Object> reqObjs = reqThread.get();
        String bizId = ObjectUtils.toString(reqObjs.get(BizConst.BIZ_ID_KEY));
        Integer bizType = Integer.parseInt(ObjectUtils.toString(reqObjs.get(BizConst.EC_BIZ_TYPE)));
        String orgCode = ObjectUtils.toString(reqObjs.get(BizConst.ORG_CODE_KEY));
        String ecNotifyUrl = ObjectUtils.toString(reqObjs.get(BizConst.EC_NOTIFY_URL));
        String taskUrl = ObjectUtils.toString(reqObjs.get(BizConst.EC_TASK_URL));
        
        ecInfoBean.setBizId(bizId);
        ecInfoBean.setBizType(bizType);
        ecInfoBean.setBizConfigId(bizConfigId.toString());
        ecInfoBean.setOrgCode(orgCode);
        ecInfoBean.setEcNotifyUrl(ecNotifyUrl);
        ecInfoBean.setEcTaskUrl(taskUrl);
        
        /** 注入电子合同信息  **/
        String ecContractNo = storeNo.toUpperCase() + "-" + ecTemplateBean.getEcTplType() + "-" + contractNoSeq;
        ecInfoBean.setEcContractNo(ecContractNo);
        ecInfoBean.setEcFile(ecTemplateBean.getEcTplContentDataBlob());
        ecInfoBean.setEcTplId(ecTemplateBean.getId().toString());
        ecInfoBean.setEcFileName(ecTemplateBean.getEcTplName());
        ecInfoBean.setEcTheme(ecTemplateBean.getEcTplTheme());
        ecInfoBean.setEcAbstract(ecTemplateBean.getEcTplAbstract());
        ecInfoBean.setEcEffectiveDays(ecTemplateBean.getEcTplEffectiveDays());
        ecInfoBean.setEcFileType(ecTemplateBean.getEcTplType());
        
        ecInfoBean.setEcFileBlob(this.getEcFileBlob(baseConfig.getCommon_basedir(), ecTemplateBean, ecContractNo));
        
        /** 注入发件人相关信息  **/
        ecInfoBean.setEcSenderName(getStrAfterConv(ecConfig.getSsqSenderName()));
        ecInfoBean.setEcSenderAcc(ecConfig.getSsqSenderPhone());
        ecInfoBean.setEcSenderType(Integer.parseInt(DictConst.EC_SIGNER_TYPE_ENTERPRISE));
        ecInfoBean.setIsSenderSign(Integer.parseInt(ecTemplateBean.getIsSenderPartSign()));
        ecInfoBean.setEcSenderUserFiletype(Integer.parseInt(EcSignerConst.EC_SIGNER_USER_FILE_TYPE_LOCALHOST));
        ecInfoBean.setEcSenderSignimgType(Integer.parseInt(EcSignerConst.EC_SIGNER_SIGN_IMAGE_TYPE));
        
        ecInfoBean.setEcType(ecTemplateBean.getEcType());
        ecInfoBean.setEcDownloadUrl(this.getEcDownloadUrl(ecInfoBean));
        
        /** 设置状态相关信息  **/
        ecInfoBean.setStatus(Integer.parseInt(StatusAttr.EC_INFO_STATUS_UNFINISH));//未签署
        
        /** 持久化到数据库  **/
        Integer id = this.ecInfoServiceImpl.saveEcInfo(ecInfoBean);
        if(id <= 0){
            logger.info("持久化到数据库失败,合同信息:{}", JSONObject.toJSONString(ecInfoBean));
            return null;
        }
        if(EcTemplateConst.EC_TYPE_DZ.equals(ecTemplateBean.getEcType())){
            /** 向电子合同签署人信息表中插入签署人信息  **/
            this.persistSignerToEcDB(ecInfoBean.getId());
        }
        
        return ecInfoBean;
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
     * Description: 获取ec文件流数据
     * @param ecContractNo 
     * @param
     * @return byte[]
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 上午10:22:04
     */
    private byte[] getEcFileBlob(String baseDir, ContractTemplateBean ecTemplateBean, String ecContractNo) throws IqbException{
        /** 封装业务传递的请求数据  **/
        Map<String, Object> reqObjs = reqThread.get();
        /** 模板属性替换  **/
        String ecTemplateAttr = ObjectUtils.toString(reqObjs.get(BizConst.EC_TEMPLATE_ATTR));
        
        Map<String, String> objs = BeanUtil.packEntityToMap(JSONObject.parseObject(ecTemplateAttr));
        
        objs.put(BizConst.EC_SIGN_CONTRACT_NO_KEY + ecTemplateBean.getEcTplType(), ecContractNo);
        
        if(EcTemplateConst.EC_TYPE_DZ.equals(ecTemplateBean.getEcType())){
            setSignerInfoToEcObjs(ecTemplateBean, ecContractNo, objs);
        }
        
        CommonUtil.removeNullValue(objs);
        reqObjs.put(BizConst.EC_TEMPLATE_ATTR, JSONObject.toJSONString(objs));
        byte[] ecTplFileBlob = ecTemplateBean.getEcTplContentDataBlob();
        
        if(ecTplFileBlob == null){
           throw new IqbException(EcReturnInfo.EC_BIZ_01030027); 
        }
        
        byte[] ecFileBlob = null;
        try {
            ecFileBlob = WordPoiUtil.getOutputStreamAfterReplaceWordWithB(baseDir, ecContractNo + ecTemplateBean.getEcTplName(), new ByteArrayInputStream(ecTplFileBlob), objs);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("模板替换发生异常", e);
            throw new IqbException(EcReturnInfo.EC_BIZ_01030016);
        }
        
        return ecFileBlob;
    }

    /**
     * 
     * Description: 将签署人相关信息传入模板
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月11日 下午4:55:42
     */
    private void setSignerInfoToEcObjs(ContractTemplateBean ecTemplateBean, String ecContractNo,
            Map<String, String> objs) {
        /** 解析签署人信息  **/
        List<EcInfoSignerBean> ecInfoSignerList = ecInfoSignerBeanThread.get();
        Map<String, String> contractNoMap = contractNoThread.get();
        if(contractNoMap == null){
            contractNoMap = new HashMap<String, String>();
        }
        for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_SIGNER_REPLACE_NAME_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEcSignerName());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_SIGNER_REPLACE_IDCARD_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEcSignerCertNo());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_SIGNER_REPLACE_PHONE_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEcSignerPhone());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_SIGNER_REPLACE_ADDRESS_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEcSignerAddress());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_SIGNER_REPLACE_EMAIL_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEcSignerEmail());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_BUSINESS_LICENSE_NUM_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getBusinessLicenseNum());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_STORE_NAME_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getStoreName());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_OPEN_BANK_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getOpenBank());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_BANK_CARD_NUM_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getBankCardNum());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_EMERGENCY_CONTRACT_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEmergencyContract());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_EMERGENCY_CONTRACT_INFO_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEmergencyContractInfo());
            objs.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_SERVICE_CALL_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getServiceCall());
            contractNoMap.put(BizConst.EC_TEMPLATE_SIGNER_REPLACE_KEY + BizConst.EC_TEMPLATE_SIGNER_REPLACE_NAME_KEY + ecInfoSignerBean.getEcSignerType(), ecInfoSignerBean.getEcSignerName());
        }
        contractNoMap.put(BizConst.EC_SIGN_CONTRACT_NO_KEY + ecTemplateBean.getEcTplType(), ecContractNo);
        contractNoThread.set(contractNoMap);
        
        objs.putAll(contractNoMap);
    }

    /**
     * 
     * Description: 向电子合同表中持久化签署人信息
     * @param id 
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午2:19:46
     */
    private void persistSignerToThread(ContractTemplateBean ecTemplateBean, Integer id) throws IqbException {
        if(EcTemplateConst.EC_TYPE_ZZ.equals(ecTemplateBean.getEcType())){
            return;
        }
        /** 封装业务传递的请求数据  **/
        Map<String, Object> reqObjs = reqThread.get();
        String bizId = ObjectUtils.toString(reqObjs.get(BizConst.BIZ_ID_KEY));
        Integer bizType = Integer.parseInt(ObjectUtils.toString(reqObjs.get(BizConst.EC_BIZ_TYPE)));
        String orgCode = ObjectUtils.toString(reqObjs.get(BizConst.ORG_CODE_KEY));
        /** 获取用户信息列表  **/
        List<Map<String, String>> signUserList = (List<Map<String, String>>) reqObjs.get(BizConst.EC_SIGNER_LIST);
        Map<String, String> userMap = this.getSignUserMap(signUserList);
        
        ContractTemplateSignerBean contractTemplateSignerBean = new ContractTemplateSignerBean();
        contractTemplateSignerBean.setEcTplId(ecTemplateBean.getId());
        /** 获取签署人信息列表  **/
        List<ContractTemplateSignerBean> singerTplList = this.contractTemplateSignerBiz.selectToListOfBean(contractTemplateSignerBean);
        
        /** 签署人列表  **/
        List<EcInfoSignerBean> ecInfoSignerList = new ArrayList<EcInfoSignerBean>();
        
        /** 获取位置信息  **/
        Map<String, String> signPositionMap = this.getSignPositionMap(ecTemplateBean.getId());
        
        Map<String, String> ecSignerMap = this.getSignerCodeMap();
        for(ContractTemplateSignerBean contractTemplateSigner : singerTplList){
            String signerCode = ecSignerMap.get(contractTemplateSigner.getEcSignerType().toString());
            EcSignerEntity ecSignerEntity = this.ecSignFactorServiceImpl.getEcSignFactorByFactorCode(signerCode);
            ecSignerEntity.setEcInfoId(id);
            EcInfoSignerBean ecInfoSignerBean = this.getEcInfoSignerFromEcSignerEntity(ecSignerEntity);
            ecInfoSignerBean.setBizId(bizId);
            ecInfoSignerBean.setBizType(bizType);
            ecInfoSignerBean.setOrgCode(orgCode);
            ecInfoSignerBean.setTpReturnUrl(userMap.get(ecInfoSignerBean.getEcSignerCode()));
            ecInfoSignerBean.setEcTokenUrl(ecConfig.getEcTokenUrlSsq());
            ecInfoSignerBean.setCoordinateList(signPositionMap.get(contractTemplateSigner.getEcSignerType().toString()));
            ecInfoSignerBean.setEcSignType(signPositionMap.get(BizConst.EC_SIGN_TYPE + contractTemplateSigner.getEcSignerType()));
            
            ecInfoSignerList.add(ecInfoSignerBean);
        }
        ecInfoSignerBeanThread.set(ecInfoSignerList);
    }
    
    private Map<String, String> getSignPositionMap(Integer id) throws IqbException {
        if(id == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030017);
        }
        ContractTemplateSignerBean contractTemplateSignerBean = new ContractTemplateSignerBean();
        contractTemplateSignerBean.setEcTplId(id);
        List<ContractTemplateSignerBean> contractTemplateSignerList = this.contractTemplateSignerBiz.selectToListOfBean(contractTemplateSignerBean);
        
        /** 判断签署方信息是否为空  **/
        if(CollectionUtils.isEmpty(contractTemplateSignerList)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030017);
        }
        Map<String, String> contractTemplateSignerMap = new HashMap<String, String>();
        for(ContractTemplateSignerBean contractTemplateSigner : contractTemplateSignerList){
            List<Map<String, String>> positionList = new ArrayList<Map<String,String>>();
            String ecSignerType = contractTemplateSigner.getEcSignerType().toString();
            String ecSealPageNum = contractTemplateSigner.getEcSealPageNum().toString();
            String ecSealPositionX = contractTemplateSigner.getEcSealPositionX().toString();
            String ecSealPositionY = contractTemplateSigner.getEcSealPositionY().toString();
            Map<String, String> positionMap = new HashMap<String, String>();
            positionMap.put(BizConst.EC_SEAL_PAGE_NUM, ecSealPageNum);
            positionMap.put(BizConst.EC_SEAL_POSITION_X, ecSealPositionX);
            positionMap.put(BizConst.EC_SEAL_POSITION_Y, ecSealPositionY);
            positionList.add(positionMap);
            contractTemplateSignerMap.put(ecSignerType, JSONObject.toJSONString(positionList));
            contractTemplateSignerMap.put(BizConst.EC_SIGN_TYPE + ecSignerType, contractTemplateSigner.getEcSignType().toString());
        }
        return contractTemplateSignerMap;
    }

    /**
     * 
     * Description: 将签署人信息持久化到数据库
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月6日 上午11:16:34
     */
    private void persistSignerToEcDB(Integer id) throws IqbException {
        List<EcInfoSignerBean> ecInfoSignerList = ecInfoSignerBeanThread.get();
        for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
            logger.info("入库的签署人信息:{}", JSONObject.toJSONString(ecInfoSignerBean));
            ecInfoSignerBean.setEcInfoId(id);
            if(ecInfoSignerBean.getEcSignerCertType() == null){
                ecInfoSignerBean.setEcSignerCertType(Integer.parseInt(BizConst.EC_SIGNER_CERT_TYPE));
            }
            ecInfoSignerBean.setEcSignerCertPwd(System.currentTimeMillis() + "");
            if(ecInfoSignerBean.getSignDeviceType() == null){
                ecInfoSignerBean.setSignDeviceType(Integer.parseInt(EcSignerConst.EC_SIGN_DEVICE_TYPE_MOBILE));
            }
            if(ecInfoSignerBean.getTpReturnUrl() == null){
                ecInfoSignerBean.setTpReturnUrl("");//??????????????????????????????????????????????
            }
            if(StringUtil.contains(ecInfoSignerBean.getEcSignerType(), DictConst.EC_SIGNER_TYPE_3) || StringUtil.contains(ecInfoSignerBean.getEcSignerType(), DictConst.EC_SIGNER_TYPE_2) ){
                ecInfoSignerBean.setEcSignerType(DictConst.EC_SIGNER_TYPE_ENTERPRISE);
            }else{
                ecInfoSignerBean.setEcSignerType(DictConst.EC_SIGNER_TYPE_PERSONAL);
            }
            this.ecInfoServiceImpl.saveEcSignerInfo(ecInfoSignerBean);
        }
    }
    
    /**
     * 
     * Description: 获取签署人列表
     * @param
     * @return Map<String,String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月2日 上午10:44:37
     */
    private Map<String, String> getSignUserMap(List<Map<String, String>> signUserList){
        Map<String, String> userMap = new HashMap<String, String>();
        for(Map<String, String> signUserMap : signUserList){
            userMap.put(signUserMap.get(BizConst.EC_SIGNER_CODE), signUserMap.get(BizConst.EC_SIGNER_NOTIFY_URL));
        }
        return userMap;
    }
    
    /**
     * 
     * Description: 将签署人bean转换成ec签署人bean
     * @param
     * @return EcInfoSignerBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:25:05
     */
    private EcInfoSignerBean getEcInfoSignerFromEcSignerEntity(EcSignerEntity ecSignerEntity){
        return BeanUtil.toJavaObject(JSONObject.toJSONString(ecSignerEntity), EcInfoSignerBean.class);
    }
    
    /**
     * 
     * Description: 获取签署人信息，
     * @param
     * @return Map<String,String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:00:59
     */
    private Map<String, String> getSignerCodeMap() throws IqbException{
        Map<String, String> retMap = new HashMap<String, String>();
        /** 获取请求数据  **/
        Map<String, Object> reqObjs = reqThread.get();
        /** 获取用户信息列表  **/
        List<Map<String, String>> signUserList = (List<Map<String, String>>) reqObjs.get(BizConst.EC_SIGNER_LIST);
        logger.info("签署用户信息列表为:{}", JSONObject.toJSONString(signUserList));
        
        for(Map<String, String> userMap : signUserList){
            String ecSignerType = userMap.get(BizConst.EC_SIGNER_TYPE);
            String ecSignerCode = userMap.get(BizConst.EC_SIGNER_CODE);
            if(StringUtil.isEmpty(ecSignerType)){
                throw new IqbException(EcReturnInfo.EC_BIZ_01030010);
            }
            retMap.put(ecSignerType, ecSignerCode);
        }
        
        return retMap;
    }
    
    /**
     * 
     * Description: 获取电子合同签署人信息列表
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午5:49:13
     */
    private List<EcInfoSignerBean> getEcSignerInfoList(Map<String, Object> reqObjs) throws IqbException{
        /** 从请求中获取业务参数  **/
        String bizId = ObjectUtils.toString(reqObjs.get(BizConst.BIZ_ID_KEY));
        Integer bizType = Integer.parseInt(ObjectUtils.toString(reqObjs.get(BizConst.EC_BIZ_TYPE)));
        String orgCode = ObjectUtils.toString(reqObjs.get(BizConst.ORG_CODE_KEY));
        
        /** 获取电子合同签署人信息列表  **/
        List<EcInfoSignerBean> ecInfoSignerList = (List<EcInfoSignerBean>) this.ecInfoServiceImpl.listEcSignerInfo(bizId, bizType, orgCode);
        if(CollectionUtils.isEmpty(ecInfoSignerList)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030012);
        }
        return ecInfoSignerList;
    }
    
}
