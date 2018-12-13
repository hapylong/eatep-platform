package com.iqb.eatep.ec.contract.ssq.sign.biz;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.iqb.eatep.ec.base.EcAttr.DictConst;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.contract.ssq.service.BestSignService;
import com.iqb.eatep.ec.contract.ssq.sign.bean.EcSignerEntity;
import com.iqb.eatep.ec.contract.ssq.sign.dao.EcSignFactorDao;
import com.iqb.eatep.ec.contract.ssq.sign.db.pojo.OrgInfo;
import com.iqb.eatep.ec.contract.ssq.sign.db.pojo.SSQRequestUniqueResultPojo;
import com.iqb.etep.common.base.biz.BaseBiz;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.SysUserSession;

/**
 * 
 * Description: ec签署人biz服务
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
@Component
public class EcSignFactorBiz extends BaseBiz {
    
    private static Logger logger = LoggerFactory.getLogger(EcSignFactorBiz.class);
    
    @Autowired
    private EcSignFactorDao ecSignFactorDao;

    @Autowired
    private BestSignService bestSignServiceImpl;

    @Autowired
    private SysUserSession  session;
    /**
     * 
     * Description: 根据模板id获取ec签署方列表
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午3:40:42
     */
    public List<EcSignerEntity> listEcSignFactorByTemplateId(String templateId) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.listEcSignFactorByTemplateId(templateId);
    }

    /**
     * 
     * Description: 通过签署方code获取签署方信息
     * @param
     * @return EcSignFactorBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午5:17:16
     */
    public EcSignerEntity getEcSignFactorByFactorCode(String factorCode) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.getEcSignFactorByFactorCode(factorCode);
    }

    /**
     * 
     * Description: 插入签署方信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午5:58:34
     */
    public Integer insertFactorInfo(Map<String, Object> map) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.insertFactorInfo(map);
    }

    /**
     * 
     * Description: 更新签署方信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午2:33:39
     */
    public Integer updateFactorInfo(Map<String, Object> map) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.updateFactorInfo(map);
    }
    
    /**
     * 
     * Description: 获取发件人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午6:04:56
     */
    public EcSignerEntity getSenderInfo() {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.getSenderInfo();
    }

    public List<OrgInfo> getGroupSignerRegisteredPage(JSONObject objs) {
        super.setDb(0, super.MASTER);
        PageHelper.startPage(getPagePara(objs));
        return this.ecSignFactorDao.getGroupSignerRegisteredPage(objs);
    }

    public List<OrgInfo> getGroupOrgRegisteredPage(JSONObject objs) {
        super.setDb(0, super.MASTER);
        PageHelper.startPage(getPagePara(objs));
        return this.ecSignFactorDao.getGroupOrgRegisteredPage(objs);
    }

    public int persistSignatureStamp(String ecSignerCertNo, String ecSignerPhone, String name, byte[] bytes) throws IqbException {
        try {
            super.setDb(0, super.MASTER);
            OrgInfo cpsp = this.ecSignFactorDao.getPersistConsumerInfoByCertNo(ecSignerCertNo, ecSignerPhone);
            
            /** 根据手机号判重  **/
            EcSignerEntity queryByEmail = this.ecSignFactorDao.getEcSignerInfoByEmail(cpsp.getEcSignerEmail());
            if(queryByEmail != null){
                throw new IqbException(EcReturnInfo.EC_BIZ_01030023);
            }
            
            EcSignerEntity ee = new EcSignerEntity();
            ee.setEcSignerAddress(cpsp.getEcSignerAddress());
            ee.setEcSignerCertNo(cpsp.getEcSignerCertNo());
            ee.setEcSignerCity(cpsp.getEcSignerCity());
            ee.setEcSignerCode(cpsp.getEcSignerCode());
            ee.setEcSignerName(cpsp.getEcSignerName());
            ee.setEcSignerPhone(cpsp.getEcSignerPhone());
            ee.setEcSignerProvince(cpsp.getEcSignerProvince());
            ee.setEcSignerPicName(name);
            ee.setEcSignerImgDataBlob(bytes);
            ee.setEcSignerType(DictConst.EC_SIGNER_TYPE_X);
            ee.setIsUploadSignImg(NOT_UPLOAD_SIGN_IMG);
            ee.setEcSignerStatus(NOT_EC_REGISTERE);
            ee.setOpenBank(cpsp.getOpenBank());
            ee.setBankCardNum(cpsp.getBankCardNum());
            ee.setCreateTime(Integer.parseInt((System.currentTimeMillis()/1000) + ""));
            ee.setCreater(session.getUserCode());
            ee.setEcSignerCertPwd(EC_SIGNER_CERT_PWD);
            Integer i = ecSignFactorDao.persistEcSignerEntity(ee);
            return i;
        } catch (Throwable e) {
            if(e instanceof IqbException){
                throw e;
            }
            
            logger.error("异常堆栈信息", e);
            logger.info("异常堆栈信息", e);
            e.printStackTrace();
            return -1;
        }
    }
    
    public int persistOrgSignatureStamp(String ecSignerCode, String name, byte[] bytes) throws IqbException {
        try {
            super.setDb(0, super.MASTER);
            OrgInfo cpsp = this.ecSignFactorDao.getPersistConsumerInfoByCode(ecSignerCode);

            /** 根据手机号判重  **/
            EcSignerEntity queryByEmail = this.ecSignFactorDao.getEcSignerInfoByEmail(cpsp.getEcSignerEmail());
            if(queryByEmail != null){
                throw new IqbException(EcReturnInfo.EC_BIZ_01030023);
            }
            
            EcSignerEntity ee = new EcSignerEntity();
            ee.setBizRegNum(cpsp.getBizRegNum());
            ee.setEcSignerAddress(cpsp.getEcSignerAddress());
            ee.setEcSignerCertNo(cpsp.getEcSignerCertNo());
            ee.setEcSignerProvince(cpsp.getEcSignerProvince());
            ee.setEcSignerCity(cpsp.getEcSignerCity());
            ee.setEcSignerCode(ecSignerCode);
            ee.setEcSignerEmail(cpsp.getEcSignerEmail());
            ee.setEcSignerName(cpsp.getEcSignerName());
            ee.setEcSignerPhone(cpsp.getEcSignerPhone());
            ee.setEnterpriseLinkMan(cpsp.getEnterpriseLinkMan());
            ee.setOrganizationCode(cpsp.getOrganizationCode());
            ee.setSocialCreditCode(cpsp.getSocialCreditCode());
            ee.setEcSignerPicName(name);
            ee.setEcSignerImgDataBlob(bytes);
            ee.setTaxRegNum(cpsp.getTaxRegNum());
            ee.setEcSignerType(cpsp.getEcSignerType());
            ee.setIsUploadSignImg(NOT_UPLOAD_SIGN_IMG);
            ee.setEcSignerStatus(NOT_EC_REGISTERE);
            ee.setBusinessLicenseNum(cpsp.getBusinessLicenseNum());
            ee.setStoreName(cpsp.getStoreName());
            ee.setOpenBank(cpsp.getOpenBank());
            ee.setBankCardNum(cpsp.getBankCardNum());
            ee.setServiceCall(cpsp.getServiceCall());
            ee.setCreateTime(Integer.parseInt((System.currentTimeMillis()/1000) + ""));
            ee.setCreater(session.getUserCode());
            ee.setEcSignerCertPwd(EC_SIGNER_CERT_PWD);
            Integer i = ecSignFactorDao.persistEcOrgEntity(ee);
            
            return i;
        } catch (Throwable e) {
            if(e instanceof IqbException){
                throw e;
            }

            logger.error("异常堆栈信息", e);
            logger.info("异常堆栈信息", e);
            e.printStackTrace();
            return -1;
        }
    }
    
    private boolean SSQResponseMessage(JSONObject obj) {
        return !obj.getJSONObject("info").get("message").equals(SSQ_SUCCESS);
    }
    private static final String SSQ_SUCCESS = "success";

    private boolean SSQPersionRequest(SSQRequestUniqueResultPojo surp) {
        try {
            boolean ra = SSQResponseMessage(bestSignServiceImpl.regUser(surp.getRegisterRequestMap()));
            boolean rb = SSQResponseMessage(bestSignServiceImpl.uploadUserImage(surp.getImgUploadRequestMap()));
            boolean rc = SSQResponseMessage(bestSignServiceImpl.certificateApplyForPersonal(surp.getPersonApplyCertificateMap()));
            if (ra || rb || rc) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("异常堆栈信息", e);
            logger.info("异常堆栈信息", e);
            e.printStackTrace();
            return false;
        }
    }

    private boolean SSQOrgRequest(SSQRequestUniqueResultPojo surp) {
        try {
            boolean ra = SSQResponseMessage(bestSignServiceImpl.regUser(surp.getRegisterRequestMap()));
            boolean rb = SSQResponseMessage(bestSignServiceImpl.uploadUserImage(surp.getImgUploadRequestMap()));
            boolean rc = SSQResponseMessage(bestSignServiceImpl.certificateApplyForEnterprise(surp.getOrgApplyCertificateMap()));
            if (ra || rb || rc) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("异常堆栈信息", e);
            logger.info("异常堆栈信息", e);
            System.err.println(e);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateGroupSignerRegisteredInfo(JSONObject objs) {
        try {
            super.setDb(0, super.MASTER);
            this.ecSignFactorDao.updateGroupSignerRegisteredInfoDirectly(objs);
            SSQRequestUniqueResultPojo surp = this.ecSignFactorDao.findByCode(objs);
            if (surp == null || surp.getImgUploadRequestMap().isEmpty() || surp.getRegisterRequestMap().isEmpty()) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000005);
            }
            if (!SSQPersionRequest(surp)) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000005);
            } else {
                this.ecSignFactorDao.updateStateValueByCode(objs.getString("ecSignerCertNo"));
            }
            return 1;
        } catch (Throwable e) {
            logger.error("异常堆栈信息", e);
            logger.info("异常堆栈信息", e);
            e.printStackTrace();
            return -1;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateGroupOrgRegisteredInfo(JSONObject objs) {
        try {
            super.setDb(0, super.MASTER);
            this.ecSignFactorDao.updateGroupSignerRegisteredInfoDirectly(objs);
            SSQRequestUniqueResultPojo surp = this.ecSignFactorDao.findOrgInfoByCode(objs.getString("ecSignerCode"));
            if (surp == null || surp.getImgUploadRequestMap().isEmpty() || surp.getRegisterRequestMap().isEmpty()) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000005);
            }
            if (!SSQOrgRequest(surp)) {
                throw new IqbException(EcReturnInfo.EC_COMMON_01000005);
            } else {
                this.ecSignFactorDao.updateStateValueByCode(objs.getString("ecSignerCode"));
            }
            return 1;
        } catch (Throwable e) {
            logger.error("异常堆栈信息", e);
            logger.info("异常堆栈信息", e);
            e.printStackTrace();
            return -1;
        }
    }

    public OrgInfo getImageGroupSignerRegisteredInfo(Integer id,
                                                     String signType) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.getImageGroupSignerRegisteredInfo(id,
                signType);
    }

    public OrgInfo getImageGroupOrgRegisteredInfo(Integer id, String signType) {
        // TODO Auto-generated method stub
        return this.ecSignFactorDao
                .getImageGroupOrgRegisteredInfo(id, signType);
    }

    /**
     * 
     * Description: 更新签署人表中上上签用户id
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 上午11:45:26
     */
    public Integer updateFactorSsqUid(Map userInfo) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.updateFactorSsqUid(userInfo);
    }

    private static final String NOT_UPLOAD_SIGN_IMG     = "0";
    private static final int    NOT_EC_REGISTERE        = 0;
    private static final String EC_SIGNER_CERT_PWD      = "123456";

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OrgInfo> getGroupSignerLoadAndSave(JSONObject objs) {
        super.setDb(0, super.MASTER);
        JSONObject objsCopy = objs;
        List<String> customerCodeList = this.ecSignFactorDao.getCustomerCodeListByConditions(objs);
        if (!customerCodeList.isEmpty()) {
            objsCopy.put("id_list", customerCodeList);
        }
        PageHelper.startPage(getPagePara(objsCopy));
        return this.ecSignFactorDao.getPersistConsumerInfoList(objsCopy);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OrgInfo> getGroupOrgLoadAndSave(JSONObject objs) {
        super.setDb(0, super.MASTER);
        JSONObject objsCopy = objs;
        List<String> customerCodeList = this.ecSignFactorDao.getOrgCodeListByConditions(objs);
        if (!customerCodeList.isEmpty()) {
            objsCopy.put("id_list", customerCodeList);
        }
        
        PageHelper.startPage(getPagePara(objsCopy));
        return this.ecSignFactorDao.getPersistOrgInfoList(objsCopy);
    }

    /**
     * 
     * Description: 更新用户签章信息
     * @param
     * @return int
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月26日 下午3:32:56
     */
    public int updateSignatureStamp(String ecSignerCertNo, String name, byte[] bytes) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.updateSignatureStamp(ecSignerCertNo, name, bytes);
    }
    
    /**
     * 
     * Description: 更新用户签章信息
     * @param
     * @return int
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月26日 下午3:32:56
     */
    public int updateOrgSignatureStamp(String ecSignerCode, String name, byte[] bytes) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.updateOrgSignatureStamp(ecSignerCode, name, bytes);
    }

    /**
     * 
     * Description: 根据code删除签署方信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年6月22日 上午9:59:03
     */
    public Integer delEcSignFactorByFactorCode(String ecSignerCode) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.delEcSignFactorByFactorCode(ecSignerCode);
    }

    /**
     * 
     * Description: 删除签署方信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年6月29日 下午3:07:40
     */
    public Integer deleteSignFactor(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.deleteSignFactor(objs);
    }

    /**
     * 
     * Description: 获取商户列表
     * @param
     * @return List<Map<Integer,String>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年7月14日 上午11:39:39
     */
    public List<Map<String, String>> getAllOrgInfo() {
        super.setDb(0, super.MASTER);
        return this.ecSignFactorDao.getAllOrgInfo();
    }

}
