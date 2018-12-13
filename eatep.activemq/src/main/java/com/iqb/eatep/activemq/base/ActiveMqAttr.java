package com.iqb.eatep.activemq.base;

import com.iqb.etep.common.utils.Attr;

/**
 * 
 * Description: mq常量类
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class ActiveMqAttr extends Attr {
    
    /** 符号相关  **/
    public class SymbolConst {
        /** 下划线  **/
        public static final String UNDERLINE = "_";
    }
    
    /** mq类型常量  **/
    public class MqMoldConst {
        /** 阻塞队列  **/
        public static final String BLOCKING_QUEUE = "blocking_queue";
        /** msg key **/
        public static final String MSG = "msg";
        /** 阻塞队列  **/
        public class BlockingQueue {
            /** 延迟队列  **/
            public static final String DELAY_QUEUE = "delay_queue";
        }
    }
    
    /** 状态常量  **/
    public class StatusAttr {
    }

}
