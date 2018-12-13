package com.iqb.eatep.ec.contract.ssq.template.dao;

/**
 * 
 * Description: 合同编号服务dao
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年10月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface ContractNoDao {

    /**
     * 
     * Description: 根据门店编码查询合同序号
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月13日 下午3:39:20
     */
    public Integer getContractSeq(String storeNo);

    /**
     * 
     * Description: 合同序号加一
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月13日 下午3:41:24
     */
    public Integer addContractSeq(String storeNo);

    /**
     * 
     * Description: 增加合同编号信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月13日 下午4:20:05
     */
    public Integer addContractNo(String storeNo);

}
