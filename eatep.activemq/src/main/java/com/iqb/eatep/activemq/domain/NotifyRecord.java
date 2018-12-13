/**
 * @Copyright (c) http://www.iqianbang.com/ All rights reserved.
 * @Description: TODO
 * @date 2016年7月21日 下午1:17:03
 * @version V1.0
 */

package com.iqb.eatep.activemq.domain;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.activemq.base.ActiveMqAttr.MqMoldConst;
import com.iqb.eatep.activemq.base.ActiveMqAttr.MqMoldConst.BlockingQueue;
import com.iqb.eatep.activemq.base.BaseEntity;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class NotifyRecord extends BaseEntity {
    private static final long serialVersionUID = 8037183963345182548L;
    private Integer lastNotifyTime;// 最后通知时间
    private int notifyTimes = 0;;// 通知次数
    private int limitNotifyTimes = 5;// 通知上线次数
    private String url;// 通知地址
    private String msg;// 内容
    private int status = 101;;// 状态
    private int notifyType = 0;// 通知类型
    private String mqMold = MqMoldConst.BLOCKING_QUEUE;
    private String mqMoldType = BlockingQueue.DELAY_QUEUE;
    private Class<?> activeMqTask;
    private String remark;

    public Integer getLastNotifyTime() {
        return lastNotifyTime;
    }

    public void setLastNotifyTime(Integer lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
    }

    public int getNotifyTimes() {
        return notifyTimes;
    }

    public void setNotifyTimes(int notifyTimes) {
        this.notifyTimes = notifyTimes;
    }

    public int getLimitNotifyTimes() {
        return limitNotifyTimes;
    }

    public void setLimitNotifyTimes(int limitNotifyTimes) {
        this.limitNotifyTimes = limitNotifyTimes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public String getMqMold() {
        return mqMold;
    }

    public void setMqMold(String mqMold) {
        this.mqMold = mqMold;
    }

    public String getMqMoldType() {
        return mqMoldType;
    }

    public void setMqMoldType(String mqMoldType) {
        this.mqMoldType = mqMoldType;
    }
    
    public Class<?> getActiveMqTask() {
        return activeMqTask;
    }

    public void setActiveMqTask(Class<?> activeMqTask) {
        this.activeMqTask = activeMqTask;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        try {
            return JSONObject.toJSONString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
