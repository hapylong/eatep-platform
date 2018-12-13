package com.iqb.eatep.ec.bizconfig.dao;

import java.util.List;
import java.util.Map;

import com.iqb.eatep.ec.bizconfig.bean.BizConfigBean;

/**
 * Description: 业务模板数据接口
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
public interface BizConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BizConfigBean record);

    BizConfigBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BizConfigBean record);

    int updateByPrimaryKey(BizConfigBean record);

    List<BizConfigBean> selectToListOfBean(BizConfigBean record);

    List<BizConfigBean> selectToListOfBeanByParameterMap(Map<String, Object> map);
    
    List<BizConfigBean> selectToListOfUnionBeanByParameterMap(Map<String, Object> map);
}
