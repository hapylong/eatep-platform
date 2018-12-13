package com.iqb.eatep.activemq;

/**
 * 
 * Description: 通用mq服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月17日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IActiveMqTask {
    
    public Object run(String str);

}
