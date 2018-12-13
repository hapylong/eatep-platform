package com.iqb.eatep.ec.contract.ecinfo.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.contract.bizretbean.SelectContractInfoRetBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoSignerBean;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 电子合同信息服务接口
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
public interface IEcInfoService {
    
    /**
     * 
     * Description: 保存合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:16:55
     */
    public Integer saveEcInfo(EcInfoBean ecInfoBean) throws IqbException;
    
    /**
     * 
     * Description: 删除合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月7日 上午11:34:27
     */
    public Integer deleteEcInfo(EcInfoBean ecInfoBean) throws IqbException;
    
    /**
     * 
     * Description: 删除电子合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月7日 上午11:40:59
     */
    public Integer deleteEcInfoSigner(EcInfoBean ecInfoBean) throws IqbException;
    
    /**
     * 
     * Description: 获取电子合同信息列表
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:16:55
     */
    public List<EcInfoBean> listEcInfo(String bizId, Integer bizType, String orgCode) throws IqbException;
    
    /**
     * 
     * Description: 保存合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:16:55
     */
    public Integer saveEcSignerInfo(EcInfoSignerBean ecInfoSignerBean) throws IqbException;
    
    /**
     * 
     * Description: 获取电子合同签署人列表
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午4:47:42
     */
    public List<EcInfoSignerBean> listEcSignerInfo(String bizId, Integer bizType, String orgCode) throws IqbException;
    
    /**
     * 
     * Description: 根据电子合同id获取ec签署人列表
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午6:01:10
     */
    public List<EcInfoSignerBean> listEcSignerByEcInfoId(Integer ecInfoId) throws IqbException;
    
    /**
     * 
     * Description: 根据电子合同docId获取ec签署人列表
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午3:28:09
     */
    public List<EcInfoSignerBean> listEcSignerByDocId(String docId) throws IqbException;
    
    /**
     * 
     * Description: 查询合同信息接口
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 上午11:29:03
     */
    public SelectContractInfoRetBean selectContractInfo(Map objs) throws IqbException;
    
    /**
     * 
     * Description: 根据合同signID查询合同信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 上午11:29:03
     */
    public EcInfoBean selectContractInfoBySignID(String signID) throws IqbException;
    
    /**
     * 
     * Description: 根据合同docId查询合同信息
     * @param
     * @return EcInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午2:34:59
     */
    public EcInfoBean selectContractInfoByDocId(String docId) throws IqbException;
    
    /**
     * 
     * Description: 根据ec签署方代码查询合同信息
     * @param
     * @return EcInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午2:56:03
     */
    public EcInfoSignerBean selectContractInfoByEcSignerCode(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 修改ec签署人信息
     * @param
     * @return EcInfoSignerBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午3:20:17
     */
    public Integer updateContractEcSignerInfo(EcInfoSignerBean ecInfoSignerBean) throws IqbException;
    public Integer updateEcSignerInfoSetDocId(EcInfoBean ecInfoBean) throws IqbException;

    /**
     * 
     * Description: 修改ec电子合同表信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月28日 下午3:37:56
     */
    public Integer updateContractEcInfo(EcInfoBean ecInfoBean);

    /**
     * 
     * Description: 为合同下载查询合同信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午4:49:23
     */
    public List<JSONObject> selectContractInfoForDownload(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 更新合同下载次数
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午5:13:54
     */
    public Integer updateEcDownloadTimes(JSONObject objs) throws IqbException;

    
}
