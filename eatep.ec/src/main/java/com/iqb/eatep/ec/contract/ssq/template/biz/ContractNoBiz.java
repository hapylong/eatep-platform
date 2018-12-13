package com.iqb.eatep.ec.contract.ssq.template.biz;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.eatep.ec.contract.ssq.template.dao.ContractNoDao;
import com.iqb.etep.common.base.biz.BaseBiz;

/**
 * 
 * Description: 合同编号服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年10月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class ContractNoBiz extends BaseBiz {
    
    private DecimalFormat df=new DecimalFormat("000");
    
    private String CONTRACT_SEQ_INIT = "001";
    
    @Autowired
    private ContractNoDao contractNoDao; 
    
    /**
     * 
     * Description: 根据门店编码查询合同序号
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月13日 下午3:40:23
     */
    public String getContractSeq(String storeNo){
        super.setDb(0, MASTER);
        Integer i = contractNoDao.getContractSeq(storeNo);
        if(i != null){
            return df.format(i);
        }
        contractNoDao.addContractNo(storeNo);
        return CONTRACT_SEQ_INIT;
    }
    
    /**
     * 
     * Description: 合同序号加一
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月13日 下午3:40:55
     */
    public Integer addContractSeq(String storeNo){
        super.setDb(0, MASTER);
        return contractNoDao.addContractSeq(storeNo);
    }

}
