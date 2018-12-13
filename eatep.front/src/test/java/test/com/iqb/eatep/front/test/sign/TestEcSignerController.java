package test.com.iqb.eatep.front.test.sign;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.iqb.eatep.front.test.AbstractConstant;
import test.com.iqb.eatep.front.test.ec.TestApiController;
import test.com.iqb.eatep.front.test.util.HttpClientUtil;

import com.alibaba.fastjson.JSON;
import com.iqb.etep.common.utils.JSONUtil;

public class TestEcSignerController extends AbstractConstant{
    protected static final Logger logger = LoggerFactory
            .getLogger(TestApiController.class);
    @Test
    public void testGetAllSignerRegistered() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("group", 1);
        params.put("pageNum", 1);
        params.put("pageSize", 10);
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL
                + "unIntcpt-ec_participant/get_group_by_type", json);
        logger.info("返回结果:{}", result);
    }

    @Test
    public void testgetSignerRegisteredByCondition() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "1");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL
                + "unIntcpt-signner/getSignerRegisteredByCondition", json);
        logger.info("返回结果:{}", result);
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("group", 1);
        params.put("pageNum", 1);
        params.put("pageSize", 10);
        params.put("org_code", "1");
        params.put("phone_num", "1");
        System.out.println(JSONUtil.objToJson(params));
    }
}
