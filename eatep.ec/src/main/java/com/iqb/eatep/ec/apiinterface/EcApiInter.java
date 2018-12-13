package com.iqb.eatep.ec.apiinterface;

import java.util.Map;

/**
 * 
 * Description: 电子合同接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月23日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface EcApiInter {
    
    /**
     * 
     * Description: 获取签署人信息.
     * @param
     * @return Map<String,String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月23日 下午9:21:58
     */
    public Map<String, Object> getEcSignerInfo(String signerCode);

}
