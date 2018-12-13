package com.iqb.eatep.ec.contract.ecinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoSignerBean;

/**
 * 
 * Description: 电子合同dao接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月27日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface EcInfoDao {

    /**
     * 
     * Description: 保存电子合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:22:05
     */
    public Integer saveEcInfo(EcInfoBean ecInfoBean);

    /**
     * 
     * Description: 保存电子合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:22:20
     */
    public Integer saveEcSignerInfo(EcInfoSignerBean ecInfoSignerBean);

    /**
     * 
     * Description: 根据电子合同表id获取签署人信息
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午12:01:54
     */
    public List<EcInfoSignerBean> listEcSignerByEcInfoId(Integer ecInfoId);

    /**
     * 
     * Description: 获取电子合同信息列表
     * @param
     * @return List<EcInfoBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:22:51
     */
    public List<EcInfoBean> listEcInfo(@Param("bizId")String bizId, @Param("bizType")Integer bizType, @Param("orgCode")String orgCode);

    /**
     * 
     * Description: 获取电子合同签署人信息列表
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:37:29
     */
    public List<EcInfoSignerBean> listEcSignerInfo(@Param("bizId")String bizId, @Param("bizType")Integer bizType, @Param("orgCode")String orgCode);

    /**
     * 
     * Description: 根据signID查询合同信息
     * @param
     * @return EcInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:40:00
     */
    public EcInfoBean selectContractInfoBySignID(String signID);

    /**
     * 
     * Description: 根据docId查询合同信息
     * @param
     * @return EcInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:41:38
     */
    public EcInfoBean selectContractInfoByDocId(String docId);

    /**
     * 
     * Description: 根据签署人代码获取签署人信息
     * @param
     * @return EcInfoSignerBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:43:44
     */
    public EcInfoSignerBean selectContractInfoByEcSignerCode(JSONObject objs);

    /**
     * 
     * Description: 更新电子合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:56:06
     */
    public Integer updateContractEcSignerInfo(EcInfoSignerBean ecInfoSignerBean);

    /**
     * 
     * Description: 根据docId查询电子合同签署方信息
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午3:02:06
     */
    public List<EcInfoSignerBean> listEcSignerByDocId(String docId);

    /**
     * 
     * Description: 更新电子合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午3:04:53
     */
    public Integer updateContractEcInfo(EcInfoBean ecInfoBean);

    /**
     * 
     * Description: 更新电子合同签署人信息插入docId和signId
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月3日 下午3:29:34
     */
    public Integer updateEcSignerInfoSetDocId(EcInfoBean ecInfoBean);

    /**
     * 
     * Description: 删除电子合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月7日 上午11:35:41
     */
    public Integer deleteEcInfo(EcInfoBean ecInfoBean);
    
    /**
     * 
     * Description: 删除电子合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月7日 上午11:38:46
     */
    public Integer deleteEcInfoSigner(EcInfoBean ecInfoBean);

    /**
     * 
     * Description: 为合同下载查询合同信息
     * @param
     * @return List<JSONObject>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午4:57:19
     */
    public List<JSONObject> selectContractInfoForDownload(EcInfoBean ecInfoBean);

    /**
     * 
     * Description: 更新合同下载次数
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午5:19:02
     */
    public Integer updateEcDownloadTimes(String tpSignid);

}
