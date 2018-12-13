package com.iqb.eatep.ec.bizconfig.dao;

import java.util.List;
import java.util.Map;

import com.iqb.eatep.ec.bizconfig.bean.BizConfigTemplateBean;
/**
 * Description: 业务模板-合同模板数据接口
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
public interface BizConfigTemplateDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BizConfigTemplateBean record);

    BizConfigTemplateBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BizConfigTemplateBean record);

    int updateByPrimaryKey(BizConfigTemplateBean record);
    
    List<Map<String, Object>> selectToListOfMap(BizConfigTemplateBean record);
}