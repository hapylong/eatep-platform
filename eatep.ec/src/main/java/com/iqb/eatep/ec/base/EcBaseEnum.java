package com.iqb.eatep.ec.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Description: 基础枚举类型
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public enum EcBaseEnum {
    
    /** 日志通知相关  **/
    LOG_EC_SIGN("es", "EcSignLogBean"),
    LOG_EC_NOTIFY("en", "EcNotifyBean");
    
    private String name;
    
    private String value;
    
    private EcBaseEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EcBaseEnum getBaseEnumByName(EcBaseEnum baseEnum){
        if(map.get(baseEnum.getName()) != null){
            return map.get(baseEnum.getName());
        }
        return null;
    }
    
    private static Map<String, EcBaseEnum> map = new HashMap<String, EcBaseEnum>();
    
    static{
        EnumSet<EcBaseEnum> currEnumSet = EnumSet.allOf(EcBaseEnum.class);
        
        for(EcBaseEnum baseEnum : currEnumSet){
            map.put(baseEnum.getName(), baseEnum);
        }
    }

}
