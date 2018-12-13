package com.iqb.eatep.ec.contract.ssq.sign.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.contract.ssq.sign.bean.EcSignerEntity;
import com.iqb.eatep.ec.contract.ssq.sign.db.pojo.OrgInfo;
import com.iqb.eatep.ec.contract.ssq.sign.db.pojo.SSQRequestUniqueResultPojo;

/**
 * 
 * Description: Ec签署人dao服务
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
public interface EcSignFactorDao {

    /**
     * 
     * Description: 根据模板id获取ec签署方列表
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午3:42:03
     */
    public List<EcSignerEntity> listEcSignFactorByTemplateId(String templateId);

    /**
     * 
     * Description: 通过签署方code获取签署方信息
     * @param
     * @return EcSignFactorBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午5:17:43
     */
    public EcSignerEntity getEcSignFactorByFactorCode(String factorCode);

    /**
     * 
     * Description: 插入签署方信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月13日 下午6:03:42
     */
    public Integer insertFactorInfo(Map<String, Object> map);

    /**
     * 
     * Description: 更新签署方信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月14日 下午2:34:05
     */
    public Integer updateFactorInfo(Map<String, Object> map);

    /**
     * Description: 分页条件查询已经注册的线上借款人信息
     * 
     * @param
     * @return List<EcSignerEntity>
     * @throws
     * @Author adam Create Date: 2017年2月22日 上午10:08:13
     */
    public List<OrgInfo> getGroupSignerRegisteredPage(JSONObject objs);

    /**
     * Description: 分页条件查询已经注册的门店信息
     * 
     * @param
     * @return List<EcSignerEntity>
     * @throws
     * @Author adam Create Date: 2017年2月22日 上午11:33:36
     */
    public List<OrgInfo> getGroupOrgRegisteredPage(JSONObject objs);

    /**
     * 
     * Description: 上传签名图章
     * 
     * @param
     * @return void
     * @throws
     * @Author adam Create Date: 2017年2月22日 上午10:11:01
     */
    public int persistSignatureStampDirectly(@Param("ecSignerCertNo") String ecSignerCertNo, @Param("name") String name, @Param("bytes") byte[] bytes);

    public int updateGroupSignerRegisteredInfoDirectly(JSONObject objs);

    public int updateGroupOrgRegisteredInfoDirectly(JSONObject objs);

    public OrgInfo getImageGroupByType(@Param("id") Integer id,
            @Param("signType") String sign_type);

    public OrgInfo getImageGroupSignerRegisteredInfo(@Param("id") Integer id,
            @Param("sign_type") String signType);

    public OrgInfo getImageGroupOrgRegisteredInfo(@Param("id") Integer id,
            @Param("sign_type") String signType);

    public EcSignerEntity getSenderInfo();

    /**
     * 
     * Description: 更新签署人表中上上签用户id
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 上午11:46:53
     */
    public Integer updateFactorSsqUid(Map userInfo);

    public List<String> getCustomerCodeListByConditions(JSONObject objs);

    public List<OrgInfo> getPersistConsumerInfoList(JSONObject objs);
    
    public OrgInfo getPersistConsumerInfoByCertNo(@Param("ecSignerCertNo")String ecSignerCertNo, @Param("ecSignerPhone")String ecSignerPhone);
    
    public OrgInfo getPersistConsumerInfoByCode(String ecSignerCertNo);

    public Integer persistEcSignerEntity(EcSignerEntity ee);

    public List<String> getOrgCodeListByConditions(JSONObject objs);

    public List<OrgInfo> getPersistOrgInfoList(JSONObject objsCopy);

    public SSQRequestUniqueResultPojo findByCode(JSONObject objs);

    public Integer updateStateValueByCode(@Param("ecSignerCertNo") String ecSignerCertNo);

    public Integer persistEcOrgEntity(EcSignerEntity ee);

    /**
     * 
     * Description: 更新用户签章信息
     * @param
     * @return int
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月26日 下午3:33:11
     */
    public int updateSignatureStamp(@Param("ecSignerCertNo")String ecSignerCertNo, @Param("name")String name, @Param("bytes")byte[] bytes);
    
    /**
     * 
     * Description: 更新企业签章信息
     * @param
     * @return int
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月26日 下午3:33:11
     */
    public int updateOrgSignatureStamp(@Param("ecSignerCode")String ecSignerCode, @Param("name")String name, @Param("bytes")byte[] bytes);

    /**
     * 
     * Description: 
     * @param
     * @return SSQRequestUniqueResultPojo
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月27日 下午2:34:13
     */
    public SSQRequestUniqueResultPojo findOrgInfoByCode(String string);
    
    /**
     * 
     * Description: 通过手机号获取签署人信息
     * @param
     * @return EcSignerEntity
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年6月19日 上午9:45:14
     */
    public EcSignerEntity getEcSignerInfoByPhone(String ecSignerPhone);
    
    /**
     * 
     * Description: 通过邮箱获取签署人信息
     * @param
     * @return EcSignerEntity
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年6月19日 上午9:45:14
     */
    public EcSignerEntity getEcSignerInfoByEmail(String ecSignerEmail);

    /**
     * 
     * Description: 根据code删除签署方信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年6月22日 上午9:59:27
     */
    public Integer delEcSignFactorByFactorCode(String ecSignerCode);

    /**
     * 
     * Description: 删除签署方信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年6月29日 下午3:02:32
     */
    public Integer deleteSignFactor(JSONObject objs);

    /**
     * 
     * Description: 获取商户列表
     * @param
     * @return List<Map<Integer,String>>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年7月14日 上午11:40:09
     */
    public List<Map<String, String>> getAllOrgInfo();

}
