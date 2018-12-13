package com.iqb.eatep.ec.contract.ecinfo.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcBaseBiz;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoBean;
import com.iqb.eatep.ec.contract.ecinfo.bean.EcInfoSignerBean;
import com.iqb.eatep.ec.contract.ecinfo.dao.EcInfoDao;

/**
 * 
 * Description: 电子合同biz服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月27日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class EcInfoBiz extends EcBaseBiz {

    @Autowired
    private EcInfoDao ecInfoDao;
    
    /**
     * 
     * Description: 保存电子合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:20:18
     */
    public Integer saveEcInfo(EcInfoBean ecInfoBean) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.saveEcInfo(ecInfoBean);
    }

    /**
     * 
     * Description: 保存电子合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 下午3:20:27
     */
    public Integer saveEcSignerInfo(EcInfoSignerBean ecInfoSignerBean) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.saveEcSignerInfo(ecInfoSignerBean);
    }

    /**
     * 
     * Description: 根据电子合同表id获取签署人信息
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午12:01:21
     */
    public List<EcInfoSignerBean> listEcSignerByEcInfoId(Integer ecInfoId) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.listEcSignerByEcInfoId(ecInfoId);
    }

    /**
     * 
     * Description: 获取电子合同信息列表
     * @param
     * @return List<EcInfoBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:22:09
     */
    public List<EcInfoBean> listEcInfo(String bizId, Integer bizType, String orgCode) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.listEcInfo(bizId, bizType, orgCode);
    }

    /**
     * 
     * Description: 获取电子合同签署人信息列表
     * @param
     * @return List<EcInfoBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:22:09
     */
    public List<EcInfoSignerBean> listEcSignerInfo(String bizId, Integer bizType, String orgCode) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.listEcSignerInfo(bizId, bizType, orgCode);
    }

    /**
     * 
     * Description: 根据signID查询合同信息
     * @param
     * @return EcInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:39:12
     */
    public EcInfoBean selectContractInfoBySignID(String signID) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.selectContractInfoBySignID(signID);
    }

    /**
     * 
     * Description: 根据docId查询合同信息
     * @param
     * @return EcInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:41:20
     */
    public EcInfoBean selectContractInfoByDocId(String docId) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.selectContractInfoByDocId(docId);
    }

    /**
     * 
     * Description: 根据签署人代码获取签署人信息
     * @param
     * @return EcInfoSignerBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:43:05
     */
    public EcInfoSignerBean selectContractInfoByEcSignerCode(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.selectContractInfoByEcSignerCode(objs);
    }

    /**
     * 
     * Description: 更新电子合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午2:55:37
     */
    public Integer updateContractEcSignerInfo(EcInfoSignerBean ecInfoSignerBean) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.updateContractEcSignerInfo(ecInfoSignerBean);
    }

    /**
     * 
     * Description: 根据docId查询电子合同签署方信息
     * @param
     * @return List<EcInfoSignerBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午3:01:21
     */
    public List<EcInfoSignerBean> listEcSignerByDocId(String docId) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.listEcSignerByDocId(docId);
    }

    /**
     * 
     * Description: 更新电子合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午3:03:47
     */
    public Integer updateContractEcInfo(EcInfoBean ecInfoBean) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.updateContractEcInfo(ecInfoBean);
    }

    /**
     * 
     * Description: 更新电子合同签署人信息插入docId和signId
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月3日 下午3:28:53
     */
    public Integer updateEcSignerInfoSetDocId(EcInfoBean ecInfoBean) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.updateEcSignerInfoSetDocId(ecInfoBean);
    }

    /**
     * 
     * Description: 删除电子合同信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月7日 上午11:35:17
     */
    public Integer deleteEcInfo(EcInfoBean ecInfoBean) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.deleteEcInfo(ecInfoBean);
    }
    
    /**
     * 
     * Description: 删除电子合同签署人信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月7日 上午11:40:04
     */
    public Integer deleteEcInfoSigner(EcInfoBean ecInfoBean) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.deleteEcInfoSigner(ecInfoBean);
    }

    /**
     * 
     * Description: 为合同下载查询合同信息
     * @param
     * @return List<JSONObject>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午4:56:52
     */
    public List<JSONObject> selectContractInfoForDownload(EcInfoBean ecInfoBean) {
        super.setDb(0, super.SLAVE);
        return this.ecInfoDao.selectContractInfoForDownload(ecInfoBean);
    }

    /**
     * 
     * Description: 更新合同下载次数
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午5:18:54
     */
    public Integer updateEcDownloadTimes(String tpSignid) {
        super.setDb(0, super.MASTER);
        return this.ecInfoDao.updateEcDownloadTimes(tpSignid);
    }

}
