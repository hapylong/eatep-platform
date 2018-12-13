package com.iqb.eatep.activemq.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.iqb.etep.common.base.CommonReturnInfo;
import com.iqb.etep.common.base.IReturnInfo;

/**
 * 
 * Description: mq返回码
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public enum ActiveMqReturnInfo implements IReturnInfo{
    
    /** 通用异常0100  **/
    MQ_COMMON_01000001("mq_common_01000001", "传入信息为空", "传入信息为空"),
    MQ_COMMON_01000002("mq_common_01000002", "JSON格式转换异常", "JSON格式转换异常"),
    MQ_COMMON_01000003("mq_common_01000003", "放置mq消费信息异常", "放置mq消费信息异常"),

    /** mq路由异常0101  **/
    MQ_ROUTE_01010001("ec_poi_01010001", "没有找到对应的mq服务", "没有找到对应的mq服务");
    
    
    
    /** 响应代码 **/
    private String retCode = "";
    
    /** 提示信息-用户提示信息 **/
    private String retUserInfo = "";
    
    /** 响应码含义-实际响应信息 **/
    private String retFactInfo = "";
        
    /** 
     * @param retCode 响应代码
     * @param retFactInfo 响应码含义-实际响应信息 
     * @param retUserInfo  提示信息-用户提示信息
     */
    private ActiveMqReturnInfo(String retCode, String retFactInfo, String retUserInfo) {
        this.retCode = retCode;
        this.retFactInfo = retFactInfo;
        this.retUserInfo = retUserInfo;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetFactInfo() {
        return retFactInfo;
    }

    public void setRetFactInfo(String retFactInfo) {
        this.retFactInfo = retFactInfo;
    }

    public String getRetUserInfo() {
        return retUserInfo;
    }

    public void setRetUserInfo(String retUserInfo) {
        this.retUserInfo = retUserInfo;
    }
    
    /**
     * 通过响应代码 获取对应的ReturnInfo
     * @param retCode-返回码
     * @return 响应枚举类型
     */
    public IReturnInfo getReturnCodeInfoByCode(IReturnInfo returnInfo) {
        if (map.get(returnInfo.getRetCode()) != null) {
            return map.get(returnInfo.getRetCode());
        } else {
            return CommonReturnInfo.BASE00000099;
        }
    }
    
    /**
     * 重写toString
     */
    public String toString() {
        return new StringBuffer("{retCode:").append(retCode)
                .append(";retFactInfo(实际响应信息):").append(retFactInfo)
                .append(";retUserInfo(客户提示信息):").append(retUserInfo).append("}").toString();
    }

    /**存放全部枚举的缓存对象*/
    private static Map<String,ActiveMqReturnInfo> map = new HashMap<String,ActiveMqReturnInfo>();
    
    /**将所有枚举缓存*/
    static{
        EnumSet<ActiveMqReturnInfo> currEnumSet = EnumSet.allOf(ActiveMqReturnInfo.class);
        
        for (ActiveMqReturnInfo retCodeType : currEnumSet) {
            map.put(retCodeType.getRetCode(), retCodeType);
        }
    }

}
