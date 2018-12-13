package com.iqb.eatep.ec.contract.ssq.sign.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.base.EcAttr.DictConst;
import com.iqb.eatep.ec.contract.ssq.sign.bean.EcSignerEntity;
import com.iqb.eatep.ec.contract.ssq.sign.biz.EcSignFactorBiz;
import com.iqb.eatep.ec.contract.ssq.sign.db.pojo.OrgInfo;
import com.iqb.eatep.ec.contract.ssq.sign.service.EcSignFactorService;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.StringUtil;
import com.iqb.etep.sysmanage.dict.service.ISysDictService;

/**
 * 
 * Description: ec签署人服务实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月9日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("rawtypes")
@Service
public class EcSignFactorServiceImpl implements EcSignFactorService {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(EcSignFactorServiceImpl.class);
    
    /** ec签署方biz服务  **/
    @Autowired
    private EcSignFactorBiz ecSignFactorBiz;

    @Autowired
    private ISysDictService sysDictService;
    
    @Override
    public List<EcSignerEntity> listEcSignFactorByTemplateId(String templateId) throws IqbException {
        logger.info("根据模板id获取签署方列表，传入模板id：{}", templateId);
        if(StringUtil.isEmpty(templateId)){
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecSignFactorBiz.listEcSignFactorByTemplateId(templateId);
    }

    @Override
    public EcSignerEntity getEcSignFactorByFactorCode(String factorCode) throws IqbException {
        logger.info("通过签署方code获取签署方信息，传入签署方code：{}", factorCode);
        if(StringUtil.isEmpty(factorCode)){
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecSignFactorBiz.getEcSignFactorByFactorCode(factorCode);
    }

    @Override
    public Object insertFactorInfo(Object objs) throws IqbException {
        if(objs == null){
            logger.error("保存签署方信息传入数据为空");
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        if(objs instanceof String){
            logger.info("保存签署方信息传入参数：{}", objs);
            JSONObject bizJsonObjs;
            try {
                bizJsonObjs = JSONObject.parseObject((String) objs);
            } catch (Exception e) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000002);
            }
            return this.insertFactorInfo(bizJsonObjs);
        }
        logger.info("保存签署方信息传入参数：{}", JSONObject.toJSONString(objs));
        return this.insertFactorInfo(JSONObject.toJSONString(objs));
    }
    
    /**
     * 
     * Description: 保存签署方信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午5:58:09
     */
    private Object insertFactorInfo(Map<String, Object> map) throws IqbException {
        logger.info("保存签署方信息传入参数：{}", JSONObject.toJSONString(map));
        if(map == null){
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecSignFactorBiz.insertFactorInfo(map);
    }

    @Override
    public Object updateFactorInfo(Object objs) throws IqbException {
        if(objs == null){
            logger.error("更新签署方信息传入数据为空");
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        if(objs instanceof String){
            logger.info("更新签署方信息传入参数：{}", objs);
            JSONObject bizJsonObjs;
            try {
                bizJsonObjs = JSONObject.parseObject((String) objs);
            } catch (Exception e) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000002);
            }
            return this.updateFactorInfo(bizJsonObjs);
        }
        logger.info("更新签署方信息传入参数：{}", JSONObject.toJSONString(objs));
        return this.updateFactorInfo(JSONObject.toJSONString(objs));
    }

    /**
     * 
     * Description: 更新签署方信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午2:30:58
     */
    private Object updateFactorInfo(Map<String, Object> map) throws IqbException {
        logger.info("更新签署方信息传入参数：{}", JSONObject.toJSONString(map));
        if(map == null){
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecSignFactorBiz.updateFactorInfo(map);
    }

    private static final String COM_IQB_EC_SIGN_TYPE = "sign_type";
    private static final String COM_IQB_EC_SIGN_STATE = "sign_state";

    private static final int    COM_IQB_EC_SIGN_TYPE_SIGNER_LIST   = 1;
    private static final int    COM_IQB_EC_SIGN_TYPE_SIGNER_UPDATE = 0;

    private static String       COM_IQB_EC_SIGN_TYPE_ORG           = "2,3";
    private static final int    COM_IQB_EC_SIGN_TYPE_ORG_LIST      = 1;
    private static final int    COM_IQB_EC_SIGN_TYPE_ORG_UPDATE    = 0;


    private String getSignerCode() {
        return DictConst.EC_SIGNER_TYPE_X;
    }

    private String getOrgCode() {
        return COM_IQB_EC_SIGN_TYPE_ORG;
    }

    private enum TypeEnum {
        BORROWER_LIST(1), ORG_LIST(2), BORROWER_UPDATE(3), ORG_UPDATE(4), UNKNOWN(0);
        private int value;

        private TypeEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * Description: 判断需要查询 的 Group<线上借款人> or Group<门店>
     * 
     * @param
     * @return boolean 返回值为 True->Group<线上借款人> |||||| False->Group<门店>
     * @throws
     * @Author adam Create Date: 2017年2月22日 上午11:03:16
     */
    private TypeEnum checkGroupType(String group, Integer type) {
        if (group.equals(getSignerCode())) {
            if (type == COM_IQB_EC_SIGN_TYPE_SIGNER_LIST) {
                return TypeEnum.BORROWER_LIST;
            } else if (type == COM_IQB_EC_SIGN_TYPE_SIGNER_UPDATE) {
                return TypeEnum.BORROWER_UPDATE;
            } else {
                return TypeEnum.UNKNOWN;
            }
        } else if (group.equals(getOrgCode())) {
            if (type == COM_IQB_EC_SIGN_TYPE_ORG_LIST) {
                return TypeEnum.ORG_LIST;
            } else if (type == COM_IQB_EC_SIGN_TYPE_ORG_UPDATE) {
                return TypeEnum.ORG_UPDATE;
            } else {
                return TypeEnum.UNKNOWN;
            }
        } else {
            return TypeEnum.UNKNOWN;
        }
    }

    private boolean checkGroupType(String type) {
        return type.equals(getSignerCode());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PageInfo<OrgInfo> getGroupByType(JSONObject objs) throws IqbException {
        logger.info("********** EcSignFactorServiceImpl[getAllSignerRegistered] start **********");
        if (objs.get(COM_IQB_EC_SIGN_TYPE) == null) {
            throw new IqbException(EcReturnInfo.EC_POI_01010001);
        }
        switch(checkGroupType(ObjectUtils.toString(objs.get(COM_IQB_EC_SIGN_TYPE)), Integer.parseInt(ObjectUtils.toString(objs.get(COM_IQB_EC_SIGN_STATE)))).getValue()) {
            case 0 :
                throw new IqbException(EcReturnInfo.EC_POI_01010001);
            case 1 :
                return new PageInfo<OrgInfo>(this.ecSignFactorBiz.getGroupSignerRegisteredPage(objs));
            case 2 :
                return new PageInfo<OrgInfo>(this.ecSignFactorBiz.getGroupOrgRegisteredPage(objs));
            case 3 :
                return new PageInfo<OrgInfo>(this.ecSignFactorBiz.getGroupSignerLoadAndSave(objs));
            case 4 :
                return new PageInfo<OrgInfo>(this.ecSignFactorBiz.getGroupOrgLoadAndSave(objs));
            default:
                throw new IqbException(EcReturnInfo.EC_POI_01010001);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateGroupByType(JSONObject objs) throws IqbException {
        if (objs.get(COM_IQB_EC_SIGN_TYPE) == null) {
            throw new IqbException(EcReturnInfo.EC_POI_01010001);
        }
        if (checkGroupType(ObjectUtils.toString(objs.get(COM_IQB_EC_SIGN_TYPE)))) {
            if (StringUtil.isEmpty(objs.getString("ec_signer_img_name_update"))
                    || StringUtil.isEmpty(objs.getString("ec_signer_img_type_update"))
                    || StringUtil.isEmpty(objs.getString("ec_signer_pic_name_update"))
                    || StringUtil.isEmpty(objs.getString("is_default_signer_img_update"))
                    || StringUtil.isEmpty(objs.getString("ecSignerCertNo"))) {
                throw new IqbException(EcReturnInfo.EC_POI_01010001);
            }
            if (this.ecSignFactorBiz.updateGroupSignerRegisteredInfo(objs) < 0) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000005);
            }
        } else {
            if (StringUtil.isEmpty(objs.getString("ec_signer_img_name_update"))
                    || StringUtil.isEmpty(objs.getString("ec_signer_img_type_update"))
                    || StringUtil.isEmpty(objs.getString("ec_signer_pic_name_update"))
                    || StringUtil.isEmpty(objs.getString("is_default_signer_img_update"))
                    || StringUtil.isEmpty(objs.getString("ecSignerCode"))) {
                throw new IqbException(EcReturnInfo.EC_POI_01010001);
            }
            if (this.ecSignFactorBiz.updateGroupOrgRegisteredInfo(objs) < 0) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000005);
            }
        }
    }

    @Override
    public int persistSignatureStamp(String ecSignerCertNo, String ecSignerPhone, String name, byte[] bytes) throws IqbException {
        EcSignerEntity ecSignerEntity = this.ecSignFactorBiz.getEcSignFactorByFactorCode(ecSignerCertNo);
        if(ecSignerEntity != null){
            this.ecSignFactorBiz.delEcSignFactorByFactorCode(ecSignerCertNo);
        }
        return this.ecSignFactorBiz.persistSignatureStamp(ecSignerCertNo, ecSignerPhone, name, bytes);
    }
    
    @Override
    public int persistOrgSignatureStamp(String ecSignerCode, String name, byte[] bytes) throws IqbException {
        EcSignerEntity ecSignerEntity = this.ecSignFactorBiz.getEcSignFactorByFactorCode(ecSignerCode);
        if(ecSignerEntity != null){
            this.ecSignFactorBiz.delEcSignFactorByFactorCode(ecSignerCode);
        }
        return this.ecSignFactorBiz.persistOrgSignatureStamp(ecSignerCode, name, bytes);
    }
    
    @Override
    public void getSignatureStampById(JSONObject objs) throws IqbException {

    }

    @Override
    public OrgInfo getImageGroupByType(Integer id, String signType)
            throws IqbException {
        if (id == null || signType == null) {
            throw new IqbException(EcReturnInfo.EC_POI_01010001);
        }
        if (checkGroupType(signType + "")) {
            return this.ecSignFactorBiz.getImageGroupSignerRegisteredInfo(id,
                    signType);
        }
        return this.ecSignFactorBiz
                .getImageGroupOrgRegisteredInfo(id, signType);
    }

    @Override
    public Object updateFactorSsqUid(Map userInfo) throws IqbException {
        if(userInfo == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030015);
        }
        
        return this.ecSignFactorBiz.updateFactorSsqUid(userInfo);
    }

    @Override
    public EcSignerEntity getSenderInfo() throws IqbException {
        return this.ecSignFactorBiz.getSenderInfo();
    }

    @Override
    public Integer deleteSignFactor(JSONObject objs) throws IqbException {
        return this.ecSignFactorBiz.deleteSignFactor(objs);
    }

    @Override
    public List<Map<String, String>> getAllOrgInfo() {
        return this.ecSignFactorBiz.getAllOrgInfo();
    }

}
