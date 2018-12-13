package com.iqb.eatep.ec.contract.ecinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcAttr.BizConst;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.contract.bizretbean.SelectContractInfoRetBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoSignerBean;
import com.iqb.eatep.ec.contract.ecinfo.biz.EcInfoBiz;
import com.iqb.eatep.ec.contract.ecinfo.service.IEcInfoService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.StringUtil;

/**
 * 
 * Description: 电子合同信息服务接口实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月27日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("rawtypes")
@Service
public class EcInfoServiceImpl implements IEcInfoService {

    @Autowired
    private EcInfoBiz ecInfoBiz;
    
    @Override
    public Integer saveEcInfo(EcInfoBean ecInfoBean) throws IqbException {
        return this.ecInfoBiz.saveEcInfo(ecInfoBean);
    }

    @Override
    public Integer saveEcSignerInfo(EcInfoSignerBean ecInfoSignerBean) throws IqbException {
        return this.ecInfoBiz.saveEcSignerInfo(ecInfoSignerBean);
    }

    @Override
    public List<EcInfoBean> listEcInfo(String bizId, Integer bizType, String orgCode) throws IqbException {
        return this.ecInfoBiz.listEcInfo(bizId, bizType, orgCode);
    }

    @Override
    public List<EcInfoSignerBean> listEcSignerInfo(String bizId, Integer bizType, String orgCode) throws IqbException {
        return this.ecInfoBiz.listEcSignerInfo(bizId, bizType, orgCode);
    }

    @Override
    public List<EcInfoSignerBean> listEcSignerByEcInfoId(Integer ecInfoId) throws IqbException {
        return this.ecInfoBiz.listEcSignerByEcInfoId(ecInfoId);
        
    }

    @Override
    public SelectContractInfoRetBean selectContractInfo(Map objs) throws IqbException {
        /** 传入参数校验  **/
        if(objs == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030014);
        }
        /** 获取业务参数  **/
        String bizId = ObjectUtils.toString(objs.get(BizConst.BIZ_ID_KEY));
        String orgCode = ObjectUtils.toString(objs.get(BizConst.ORG_CODE_KEY));
        String bizType = ObjectUtils.toString(objs.get(BizConst.EC_BIZ_TYPE));
        String ecSignerCode = ObjectUtils.toString(objs.get(BizConst.EC_SIGNER_CODE));
        if(StringUtil.isEmpty(bizId) || StringUtil.isEmpty(orgCode) || StringUtil.isEmpty(bizType)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030014);
        }
        
        /** 初始化返回信息  **/
        SelectContractInfoRetBean selectContractInfoRetBean = new SelectContractInfoRetBean();
        selectContractInfoRetBean.setBizId(bizId);
        selectContractInfoRetBean.setOrgCode(orgCode);
        selectContractInfoRetBean.setBizType(bizType);
        /** 根据传入信息获取电子合同列表  **/
        List<EcInfoBean> ecInfoList = this.listEcInfo(bizId, Integer.parseInt(bizType), orgCode);
        /** 电子合同列表  **/
        List<Map<String, Object>> ecList = new ArrayList<Map<String,Object>>();
        /** 遍历电子合同列表,获取签署人信息  **/
        for(EcInfoBean ecInfo : ecInfoList){
            /** 封装ecmap **/
            Map<String, Object> ecMap = new HashMap<String, Object>();
            ecMap.put(BizConst.EC_NAME_KEY, ecInfo.getEcFileName());
            ecMap.put(BizConst.EC_CODE_KEY, ecInfo.getEcContractNo());
            ecMap.put(BizConst.EC_STATUS, ecInfo.getStatus());
            ecMap.put(BizConst.EC_VIEW_URL, ecInfo.getEcViewUrl());
            ecMap.put(BizConst.EC_DOWNLOAD_URL, ecInfo.getEcDownloadUrl());
            ecMap.put(BizConst.EC_TYPE, ecInfo.getEcType());
            ecMap.put(BizConst.TEMPLATE_SIGN_SEQ, ecInfo.getEcTplId());
            
            /** 签署人签署地址列表  **/
            List<Map<String, String>> ecSignerList = new ArrayList<Map<String,String>>();
            
            /** 电子合同签署人列表  **/
            List<EcInfoSignerBean> ecInfoSignerList = this.listEcSignerByEcInfoId(ecInfo.getId()); 
            for(EcInfoSignerBean ecInfoSignerBean : ecInfoSignerList){
                Map<String, String> ecSignerMap = new HashMap<String, String>();
                if(!ecInfoSignerBean.getEcSignerCode().equals(ecSignerCode)){
                    continue;
                }
                ecSignerMap.put(BizConst.EC_SIGNER_CODE_KEY, ecInfoSignerBean.getEcSignerCode());
                ecSignerMap.put(BizConst.EC_SIGNER_SIGN_URL_KEY, ecInfoSignerBean.getManualSignUrl());
                ecSignerList.add(ecSignerMap);
            }
            if(ecSignerList.size() == 0 && StringUtil.isNotEmpty(ecSignerCode)){
                continue;
            }
            ecMap.put(BizConst.EC_SIGNER_LIST, ecSignerList);
            ecList.add(ecMap);
        }
        selectContractInfoRetBean.setEcList(ecList);
        return selectContractInfoRetBean;
    }

    @Override
    public EcInfoBean selectContractInfoBySignID(String signID) throws IqbException {
        return this.ecInfoBiz.selectContractInfoBySignID(signID);
    }

    @Override
    public EcInfoBean selectContractInfoByDocId(String docId) throws IqbException {
        return this.ecInfoBiz.selectContractInfoByDocId(docId);
    }

    @Override
    public EcInfoSignerBean selectContractInfoByEcSignerCode(JSONObject objs) throws IqbException {
        return this.ecInfoBiz.selectContractInfoByEcSignerCode(objs);
    }

    @Override
    public Integer updateContractEcSignerInfo(EcInfoSignerBean ecInfoSignerBean) throws IqbException {
        return this.ecInfoBiz.updateContractEcSignerInfo(ecInfoSignerBean);
    }

    @Override
    public List<EcInfoSignerBean> listEcSignerByDocId(String docId) throws IqbException {
        return this.ecInfoBiz.listEcSignerByDocId(docId);
    }

    @Override
    public Integer updateContractEcInfo(EcInfoBean ecInfoBean) {
        return this.ecInfoBiz.updateContractEcInfo(ecInfoBean);
    }

    @Override
    public Integer updateEcSignerInfoSetDocId(EcInfoBean ecInfoBean) throws IqbException {
        return this.ecInfoBiz.updateEcSignerInfoSetDocId(ecInfoBean);
    }

    @Override
    public Integer deleteEcInfo(EcInfoBean ecInfoBean) throws IqbException {
        return this.ecInfoBiz.deleteEcInfo(ecInfoBean);
    }

    @Override
    public Integer deleteEcInfoSigner(EcInfoBean ecInfoBean) throws IqbException {
        return this.ecInfoBiz.deleteEcInfoSigner(ecInfoBean);
    }

    @Override
    public List<JSONObject> selectContractInfoForDownload(JSONObject objs) throws IqbException {
        Integer ecSendtime = objs.getInteger(BizConst.EC_TIME);
        if(ecSendtime == null || ecSendtime <=0){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030024);
        }
        EcInfoBean ecInfoBean = new EcInfoBean();
        ecInfoBean.setEcSendtime(ecSendtime);
        return this.ecInfoBiz.selectContractInfoForDownload(ecInfoBean);
    }

    @Override
    public Integer updateEcDownloadTimes(JSONObject objs) throws IqbException {
        String tpSignid = objs.getString(BizConst.EC_TP_SIGNID);
        if(StringUtils.isEmpty(tpSignid)){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030025);
        }
        return this.ecInfoBiz.updateEcDownloadTimes(tpSignid);
    }
    
}
