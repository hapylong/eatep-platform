package com.iqb.eatep.ec.contract.entry.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcAttr.BizConst;
import com.iqb.eatep.ec.base.EcAttr.StatusAttr;
import com.iqb.eatep.ec.base.EcAttr.ThirdEcConst.SSQKeysConst;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.contract.bizretbean.EcTaskNotifyBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoSignerBean;
import com.iqb.eatep.ec.contract.ecinfo.service.IEcInfoService;
import com.iqb.eatep.ec.contract.entry.service.IThirdEcService;
import com.iqb.eatep.ec.util.httputil.SendHttpsUtil;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.StringUtil;

/**
 * 
 * Description: 上上签服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class SSQEcServiceImpl implements IThirdEcService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(SSQEcServiceImpl.class);
    
    private int i = 0;
    
    @Autowired
    private IEcInfoService ecInfoServiceImpl;
    
    //1.缺少事务控制
    //2.合同反查接口
    @Override
    public Object dealSsqManualSignNotify(JSONObject objs) throws IqbException {
        logger.info("签署完成回调信息：", JSONObject.toJSONString(objs));
        if(objs == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030004);
        }
        /** 获取请求参数  **/
        String docId = objs.getString(SSQKeysConst.SSQ_RET_DOCID_KEY);
        String code = objs.getString(SSQKeysConst.SSQ_RET_CODE_KEY);
        String user = objs.getString(SSQKeysConst.SSQ_RET_USER_KEY);
        logger.info("签署完成回调信息：docId:" + docId + ",code:" + code + ",user:" + user);
        if(!SSQKeysConst.SSQ_SUCC_CODE.equals(code)){
            return null;
        }
        EcInfoSignerBean ecInfoSignerBean = this.ecInfoServiceImpl.selectContractInfoByEcSignerCode(objs);
        logger.info("签署完成回调信息：ecInfoSignerBean:", JSONObject.toJSONString(ecInfoSignerBean));
        if(ecInfoSignerBean == null){
            return null;
        }
        ecInfoSignerBean.setTpUserStatusAsyn(StatusAttr.EC_INFO_STATUS_SIGN_FINISH);
        this.ecInfoServiceImpl.updateContractEcSignerInfo(ecInfoSignerBean);
        
        /** 判断电子合同签署人是否完成签署  **/
        boolean isSignerFinishSign = isEcInfoSignerFinishSign(docId);
        if(isSignerFinishSign == false){
            return false;
        }
        
        /** 修改电子合同表中的状态  **/
        EcInfoBean ecInfoBean = this.ecInfoServiceImpl.selectContractInfoByDocId(docId);
        if(ecInfoBean == null){
            return null;
        }
        ecInfoBean.setStatus(Integer.parseInt(StatusAttr.EC_INFO_STATUS_SIGN_FINISH));
        this.ecInfoServiceImpl.updateContractEcInfo(ecInfoBean);
        
        /** 判断同一次bizId业务请求是否所有电子合同都完成了签署  **/
        boolean isAllEcFinishByBizId = isAllEcFinishByBizId(ecInfoBean.getBizId(), ecInfoBean.getBizType(), ecInfoBean.getOrgCode());
        if(isAllEcFinishByBizId == true){
            this.notifyBizEcFinish(ecInfoBean.getBizId(), ecInfoBean.getBizType(), ecInfoBean.getOrgCode());
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
     * Description: 判断电子合同签署人是否完成签署
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午3:52:03
     */
    private boolean isEcInfoSignerFinishSign(String docId) throws IqbException {
        List<EcInfoSignerBean> ecInfoSignerList = this.ecInfoServiceImpl.listEcSignerByDocId(docId);
        if(CollectionUtils.isEmpty(ecInfoSignerList)){
            logger.info("根据docId查询到的ec签署方列表:{}", JSONObject.toJSONString(ecInfoSignerList));
            return false;
        }
        
        /** 遍历电子合同签署人列表  **/
        for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
            if(!StatusAttr.EC_INFO_STATUS_SIGN_FINISH.equals(ecInfoSignerBean.getTpUserStatusAsyn())){
                return false;
            }
        }
        return true;
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
    
    @Override
    public Object dealSsqManualSignReturnUrl(JSONObject objs) throws IqbException {
        if(objs == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030004);
        }
        String code = objs.getString(SSQKeysConst.SSQ_RET_CODE_KEY);
        String signID = objs.getString(SSQKeysConst.SSQ_RET_SIGNID_KEY);
        
        if(SSQKeysConst.SSQ_SUCC_CODE.equals(code)){
            this.ecInfoServiceImpl.selectContractInfoBySignID(signID);
        }
        
        return true;
    }

}
