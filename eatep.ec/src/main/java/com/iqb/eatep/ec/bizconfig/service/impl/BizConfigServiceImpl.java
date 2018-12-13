package com.iqb.eatep.ec.bizconfig.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.eatep.ec.base.EcAttr.StatusAttr;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.bizconfig.bean.BizConfigBean;
import com.iqb.eatep.ec.bizconfig.biz.BizConfigBiz;
import com.iqb.eatep.ec.bizconfig.service.BizConfigService;
import com.iqb.eatep.ec.util.CommonUtil;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.Attr.CommonConst;
import com.iqb.etep.common.utils.StringUtil;
import com.iqb.etep.common.utils.SysUserSession;
import com.iqb.etep.sysmanage.dict.service.ISysDictService;
import com.iqb.etep.sysmanage.organization.service.ISysOrganizationService;

/**
 * Description: 业务模板-服务接口实现类
 * 
 * @author baiaypeng
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    baiaypeng       1.0        1.0 Version 
 * </pre>
 */
@Service
public class BizConfigServiceImpl implements BizConfigService {

    private static final Logger logger = LoggerFactory.getLogger(BizConfigServiceImpl.class);
    private static final String dictTypeCode = "COMM_BIZ_SUBTYPE";
    /** 机构编码  **/
    private static final String ORGCODE = "orgCode";
    /** 业务类型  **/
    private static final String BIZTYPE = "bizType";
    /** 是否需要签署  **/
    private static final String ISNEEDSIGN = "isNeedSign";
    /** 是否包含下级  **/
    private static final String ISINCLUDEJUNIOR = "isIncludeJunior";
    
    @Autowired
    private BizConfigBiz ecBizConfigBiz;
    
    @Autowired
    private ISysOrganizationService sysOrganizationService;
    
    @Autowired
    private ISysDictService sysDictService;
    
    @Autowired
    private SysUserSession session;

    @Override
    public int deleteByPrimaryKey(Integer id) throws IqbException {
        logger.info("业务模板删除参数：{}", JSONObject.toJSONString(id));
        if (CommonUtil.isEmpty(id)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecBizConfigBiz.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BizConfigBean record) throws IqbException {
        logger.info("业务模板添加参数：{}", JSONObject.toJSONString(record));
        if (CommonUtil.isEmpty(record)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        long now_second = System.currentTimeMillis() / 1000;
        record.setCreateTime((int)(now_second));
        record.setUpdateTime((int)(now_second));
        record.setCreater(session.getUserCode());
        record.setUpdater(session.getUserCode());
        record.setDeleteFlag("0");// 未删除状态
        record.setVersion(Integer.parseInt(CommonConst.Version)); // 版本
        
        return this.ecBizConfigBiz.insert(record);
    }

    @Override
    public BizConfigBean selectByPrimaryKey(Integer id) throws IqbException {
        logger.info("业务模板主键查询参数：{}", JSONObject.toJSONString(id));
        if (CommonUtil.isEmpty(id)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return this.ecBizConfigBiz.selectByPrimaryKey(id);
    }

    @Override
    public synchronized int updateByPrimaryKey(BizConfigBean record) throws IqbException {
    	
        logger.info("业务模板修改参数：{}", JSONObject.toJSONString(record));
        if (CommonUtil.isEmpty(record)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        long now_second = System.currentTimeMillis() / 1000;
        record.setUpdateTime((int)(now_second));
        record.setUpdater(session.getUserCode());
        return this.ecBizConfigBiz.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<BizConfigBean> selectToListOfBean(BizConfigBean record) throws IqbException {
        logger.info("业务模板分页查询参数：{}", JSONObject.toJSONString(record));
        if (CommonUtil.isEmpty(record)) {
            throw new IqbException(EcReturnInfo.EC_COMMON_01000001);
        }
        return new PageInfo<BizConfigBean> (this.ecBizConfigBiz.selectToListOfBean(record));
    }

    @Override
    public List<BizConfigBean> selectToListOfBeanByParameterMap(Map<String, Object> map)
            throws IqbException {
        logger.info("业务模板查询参数：{}", JSONObject.toJSONString(map));
        if (CommonUtil.isEmpty(map)) {
            map = new HashMap<String, Object>();
        }
        return this.ecBizConfigBiz.selectToListOfBeanByParameterMap(map);
    }
    
    @Override
	public List<BizConfigBean> selectToListOfUnionBeanByParameterMap(
			Map<String, Object> map) throws IqbException {
    	logger.info("业务模板查询参数：{}", JSONObject.toJSONString(map));
        if (CommonUtil.isEmpty(map)) {
            map = new HashMap<String, Object>();
        }
        return this.ecBizConfigBiz.selectToListOfUnionBeanByParameterMap(map);
	}

	@Override
	public List<Map<String, Object>> selectOrgToListOfMap(JSONObject objs)
			throws IqbException {
		return this.sysOrganizationService.selectOrgToListOfMap(objs);
	}

	@Override
	public List<Map<String, Object>> selectBizToListOfMap(JSONObject objs)
			throws IqbException {
		objs.put("dictTypeCode", dictTypeCode);
		return this.sysDictService.selectSysDictTypeToListOfMapFormRedis(objs);
	}

    @Override
    public Object judgeSignContract(JSONObject objs) throws IqbException {
        /** 数据的完整性校验  **/
        if(objs == null){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030020);
        }
        if(StringUtil.isEmpty(objs.getString(BIZTYPE))){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030021);
        }
        if(StringUtil.isEmpty(objs.getString(ORGCODE))){
            throw new IqbException(EcReturnInfo.EC_BIZ_01030022);
        }
        
        /** 初始化返回信息  **/
        Map<String, String> retMap = this.initJudgeSignContractRet();
        
        /** 查询业务模板列表  **/
        List<BizConfigBean> bizConfigList = this.ecBizConfigBiz.selectToListOfUnionBeanByParameterMap(objs);
        if(CollectionUtils.isEmpty(bizConfigList)){
            return retMap;
        }
        
        /** 解析返回结果  **/
        BizConfigBean bizConfigBean = bizConfigList.get(0);
        if(bizConfigBean == null){
            return retMap;
        }
        retMap.put(ISNEEDSIGN, StatusAttr.COMMON_YES);
        retMap.put(ISINCLUDEJUNIOR, bizConfigBean.getIsIncludeJunior());
        return retMap;
    }
    
    /**
     * 
     * Description: 初始化返回信息
     * @param
     * @return Map<?,?>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月24日 上午11:21:39
     */
    private Map<String, String> initJudgeSignContractRet(){
        /** 初始化数据  **/
        String isNeedSign = StatusAttr.COMMON_NO; 
        String isIncludeJunior = StatusAttr.COMMON_NO; 
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put(ISNEEDSIGN, isNeedSign);
        retMap.put(ISINCLUDEJUNIOR, isIncludeJunior);
        return retMap;
    }
}
