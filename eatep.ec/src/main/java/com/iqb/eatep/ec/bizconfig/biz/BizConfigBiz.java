package com.iqb.eatep.ec.bizconfig.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.iqb.eatep.ec.bizconfig.bean.BizConfigBean;
import com.iqb.eatep.ec.bizconfig.dao.BizConfigDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * Description: 业务模板业务接口
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
@Component
public class BizConfigBiz extends BaseBiz {

    @Autowired
    private BizConfigDao bizConfigDao;

    public int deleteByPrimaryKey(Integer id) {
        setDb(0, super.MASTER);
        return this.bizConfigDao.deleteByPrimaryKey(id);
    };

    public int insert(BizConfigBean record) {
        setDb(0, super.MASTER);
        return this.bizConfigDao.insert(record);
    };

    public BizConfigBean selectByPrimaryKey(Integer id) {
        setDb(0, super.SLAVE);
        return this.bizConfigDao.selectByPrimaryKey(id);
    };

    public int updateByPrimaryKeySelective(BizConfigBean record) {
        setDb(0, super.MASTER);
        return this.bizConfigDao.updateByPrimaryKeySelective(record);
    };

    public int updateByPrimaryKey(BizConfigBean record) {
        setDb(0, super.MASTER);
        return this.bizConfigDao.updateByPrimaryKey(record);
    };

    public List<BizConfigBean> selectToListOfBean(BizConfigBean record) {
        setDb(0, super.SLAVE);
        PageHelper.startPage(record);
        return this.bizConfigDao.selectToListOfBean(record);
    };

    public List<BizConfigBean> selectToListOfBeanByParameterMap(Map<String, Object> map) {
        setDb(0, super.SLAVE);
        return this.bizConfigDao.selectToListOfBeanByParameterMap(map);
    };
    
    public List<BizConfigBean> selectToListOfUnionBeanByParameterMap(Map<String, Object> map) {
        setDb(0, super.SLAVE);
        return this.bizConfigDao.selectToListOfUnionBeanByParameterMap(map);
    };
}
