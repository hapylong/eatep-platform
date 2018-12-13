package com.iqb.eatep.crm.customer;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

/**
 * 
 * Description: 保证金费率testcase
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年11月2日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomerDepositRateTestCase{

    private String uri = "http://192.168.1.194/eatep.house.front";


    @Test
    public void getCustomerDepositRateInfo() {
        uri = uri + "/customer/unIntcpt-getChannelListByOrgCode";

        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Charset", "UTF-8");

        Map map = new HashMap();
        map.put("orgCode", "1001");

        HttpEntity entity = new HttpEntity(JSON.toJSON(map), headers);
        ResponseEntity response = restTemplate.exchange(uri, HttpMethod.POST, entity, Map.class);
        LinkedHashMap responseMap = (LinkedHashMap) response.getBody();
        System.out.print("*******************" + responseMap);
        assertTrue(true);
    }
}
