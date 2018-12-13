package com.iqb.eatep.ec.base;

/**
 * 异常类处理基类
 */
public class InterfaceException extends Exception {
    
    private static final long serialVersionUID = -4808804277171982514L;
    
    protected String excptionStr;
    
    public InterfaceException(String str) {
        this.excptionStr = str;
    }
    
}