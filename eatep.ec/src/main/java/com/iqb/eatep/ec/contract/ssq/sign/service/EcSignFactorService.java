package com.iqb.eatep.ec.contract.ssq.sign.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.contract.ssq.sign.bean.EcSignerEntity;
import com.iqb.eatep.ec.contract.ssq.sign.db.pojo.OrgInfo;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: ec签署人服务接口
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
public interface EcSignFactorService {
    
    /**
     * 
     * Description: 获取ec合同签署方列表
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午3:36:42
     */
    public List<EcSignerEntity> listEcSignFactorByTemplateId(String templateId)
            throws IqbException;
    
    /**
     * 
     * Description: 通过签署方code获取签署方信息
     * @param
     * @return EcSignFactorBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午5:15:59
     */
    public EcSignerEntity getEcSignFactorByFactorCode(String factorCode)
            throws IqbException;
    
    /**
     * 
     * Description: 获取发件人信息
     * @param
     * @return EcSignerEntity
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午6:03:01
     */
    public EcSignerEntity getSenderInfo()
            throws IqbException;
    
    /**
     * 
     * Description: 新增签署方信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午5:52:08
     */
    public Object insertFactorInfo(Object objs) throws IqbException;
    
    /**
     * 
     * Description: 更新上上签uid
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午5:05:00
     */
    public Object updateFactorSsqUid(Map userInfo) throws IqbException;
    
    /**
     * 
     * Description: 更新签署方信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午2:28:22
     */
    public Object updateFactorInfo(Object objs) throws IqbException;


    public PageInfo<OrgInfo> getGroupByType(JSONObject objs) throws IqbException;

    public void getSignatureStampById(JSONObject objs) throws IqbException;

    public int persistSignatureStamp(String ecSignerCertNo, String ecSignerPhone, String name, byte[] bytes) throws IqbException;
    
    public int persistOrgSignatureStamp(String ecSignerCode, String name, byte[] bytes) throws IqbException;

    public void updateGroupByType(JSONObject objs) throws IqbException;

    public OrgInfo getImageGroupByType(Integer id, String signType)
            throws IqbException;

    public Integer deleteSignFactor(JSONObject objs) throws IqbException;

    public List<Map<String, String>> getAllOrgInfo();
}
