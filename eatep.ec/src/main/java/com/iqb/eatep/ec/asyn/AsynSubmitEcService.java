package com.iqb.eatep.ec.asyn;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.activemq.ActiveMqService;
import com.iqb.eatep.activemq.IActiveMqTask;
import com.iqb.eatep.activemq.base.ActiveMqAttr.MqMoldConst;
import com.iqb.eatep.ec.base.EcAttr.BizConst;
import com.iqb.eatep.ec.base.EcAttr.StatusAttr;
import com.iqb.eatep.ec.contract.ssq.dosign.service.IDoSignService;
import com.iqb.etep.common.exception.IqbException;

/**
 * 
 * Description: 异步提交合同
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年3月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class AsynSubmitEcService implements IActiveMqTask {

    @Autowired
    private IDoSignService doSignServiceImpl;

    @Autowired
    private ActiveMqService activeMqService;

    @PostConstruct
    public void init() throws InterruptedException{
        while(this.activeMqService == null){
            Thread.sleep(1000);
        }
        this.activeMqService.setMqCommonTask(this.getClass());
    }
    
    /**
     * 
     * Description: 合同生成
     * @param
     * @return GenerateContractRetBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月2日 下午5:54:25
     */
    public Object submitEc(Map<String, Object> reqObjs) throws IqbException {
        Map<String, Object> mqMap = new HashMap<String, Object>();
        this.activeMqService.setMqCommonTask(this.getClass());
        mqMap.put(MqMoldConst.MSG, reqObjs);
        this.activeMqService.addMsg(mqMap);
        reqObjs.put(BizConst.EC_STATUS, StatusAttr.COMMON_YES);
        return reqObjs;
    }
    
    @Override
    public Object run(String str) {
        try {
            return this.doSignServiceImpl.submitEc(JSONObject.parseObject(str));
        } catch (IqbException e) {
            e.printStackTrace();
        }
        return str;
    }
}
