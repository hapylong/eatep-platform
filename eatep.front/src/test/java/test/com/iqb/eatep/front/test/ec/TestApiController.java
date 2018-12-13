package test.com.iqb.eatep.front.test.ec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.iqb.eatep.front.test.AbstractConstant;
import test.com.iqb.eatep.front.test.util.HttpClientUtil;

import com.alibaba.fastjson.JSON;

/**
 * 
 * Description: 测试api
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class TestApiController extends AbstractConstant {
    
    protected static final Logger logger = LoggerFactory.getLogger(TestApiController.class);
//    
//    public static void main(String[] args) throws ParseException {
//        String date1 = "2017-01-10";
//        String date2 = "2017-03-07";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d1 = sdf.parse(date1);
//        Date d2 = sdf.parse(date2);
//        long days = (d2.getTime() - d1.getTime()) / 86400000;
//        System.out.println(days);
//    }

    /**
     * 
     * Description: 测试生成合同
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午8:46:50
     */
    @Test
    public void testGenerateEc() throws Exception {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("bizId", "12345678");
//        params.put("orgCode", "1006");
//        params.put("bizType", "1001");
//        params.put("notifyUrl", "http://101.201.151.38:8088/consumer.manage.front/contract4shop/call_back");
//        params.put("taskUrl", "http://101.201.151.38:8088/consumer.manage.front/contract4shop/call_back");
//        List<Map<String, String>> ecSignerList = new ArrayList<Map<String,String>>();
//        Map<String, String> ecSignerMap = new HashMap<String, String>();
//        ecSignerMap.put("ecSignerType", "91");
//        ecSignerMap.put("ecSignerCode", "340104198412034510");
//        ecSignerList.add(ecSignerMap);
//        Map<String, String> ecSignerMap1 = new HashMap<String, String>();
//        ecSignerMap1.put("ecSignerType", "92");
//        ecSignerMap1.put("ecSignerCode", "13898450943");
//        ecSignerMap1.put("ecSignNotifyUrl", "3");
//        ecSignerList.add(ecSignerMap1);
//        params.put("ecSignerList", ecSignerList);
//        params.put("ecTemplateAttr", "{\"year\":\"2008\",\"month\":\"10\",\"day\":\"1\"}");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bizId", "TYLD2002171019003");
        params.put("orgCode", "1006032");
        params.put("bizType", "2002");
        params.put("storeNo", "test-abc2");
        params.put("notifyUrl", "https://www.shandianx.com/consumer.manage.front/unIntcpt-contract4shop/call_back");
        params.put("taskUrl", "https://www.shandianx.com/consumer.manage.front/unIntcpt-contract4shop/contractReturn");
        List<Map<String, String>> ecSignerList = new ArrayList<Map<String,String>>();
        Map<String, String> ecSignerMap = new HashMap<String, String>();
        ecSignerMap.put("ecSignerType", "x");
        ecSignerMap.put("ecSignerCode", "412829198912135225");
        ecSignerList.add(ecSignerMap);
        Map<String, String> ecSignerMap1 = new HashMap<String, String>();
        ecSignerMap1.put("ecSignerType", "s");
        ecSignerMap1.put("ecSignerCode", "13522580626");
        ecSignerMap1.put("ecSignNotifyUrl", "http://www.shandianx.com/asset.lundong.china.web/views/user/contract/signContract.html");
        ecSignerList.add(ecSignerMap1);
        Map<String, String> ecSignerMap2 = new HashMap<String, String>();
        ecSignerMap2.put("ecSignerType", "3");
        ecSignerMap2.put("ecSignerCode", "1006032");
        ecSignerList.add(ecSignerMap2);
        Map<String, String> ecSignerMap3 = new HashMap<String, String>();
        ecSignerMap3.put("ecSignerType", "2");
        ecSignerMap3.put("ecSignerCode", "1029001");
        ecSignerList.add(ecSignerMap3);
        params.put("ecSignerList", ecSignerList);
        params.put("ecTemplateAttr", "{\"WORD_TABLE_STAGES\":\"[[\\\"第1期\\\",\\\"2017年12月19日\\\",\\\"2000.00\\\"],[\\\"第2期\\\",\\\"2018年1月19日\\\",\\\"2000.00\\\"]]\",\"carNo\":\"88\",\"carType\":\"8\",\"city\":\"太原市\",\"contractYear\":\"3\",\"customerBankName\":\"农业银行\",\"customerBankNo\":\"6228480402564890018\",\"customerName\":\"(新车)太原市轮动方程汽车租赁有限公司\",\"dPRatio\":\"0.00\",\"date\":\"2017年10月19日\",\"day\":\"19\",\"downMPayment\":\"零元整\",\"downPayment\":\"0.00\",\"engineType\":\"8\",\"feeAmount\":\"1992.00\",\"feeMAmt\":\"壹仟玖佰玖拾贰元整\",\"financialAddress\":\"天津市>生态城动漫中路126动漫大厦C区C层209\",\"financialName\":\"宝腾融租\",\"financialPhone\":\"13261541870\",\"financialUserName\":\"王晓慧\",\"fuelForm\":\"999\",\"fuelOilNumber\":\"/\",\"limitDate\":\"2020年10月19日\",\"mFRatio\":\"0.83\",\"marMgin\":\"零元整\",\"margin\":\"0.00\",\"mgRatio\":\"0.00\",\"monthAccrual\":\"27.67\",\"monthCorpus\":\"277.78\",\"monthInterest\":\"305.44\",\"monthMAccrual\":\"贰拾柒元陆角柒分\",\"monthMCorpus\":\"贰佰柒拾柒元柒角捌分\",\"monthMInterest\":\"叁佰零伍元肆角肆分\",\"monthuInterest\":\"0\",\"monthuMInterest\":\"零\",\"mtAccrual\":\"27.67\",\"mtCorpus\":\"277.78\",\"oItems\":\"36\",\"orderAmt\":\"10000.00\",\"orderId\":\"TYLD2002171019001\",\"orderItems\":\"36\",\"orderMAmt\":\"壹万元整\",\"preAmt\":\"1992.00\",\"preMAmt\":\"壹仟玖佰玖拾贰元整\",\"province\":\"山西省\",\"registrationNo\":\"8\",\"sFRatio\":\"0.00\",\"seatNum\":\"88\",\"serviceFee\":\"零元整\",\"storeName\":\"(新车)太原市轮动\",\"storeNo\":\"tyld\",\"supplier\":\"8\",\"vendor\":\"/8\",\"vendorNo\":\"8\",\"yearRatio\":\"9.96\"}");
//        params.put("ecTemplateAttr", "{\"carColor1\":\"123\",\"carNo\":\"88\",\"carType\":\"8\",\"city\":\"太原市\",\"contractYear\":\"3\",\"customerBankName\":\"农业银行\",\"customerBankNo\":\"6228480402564890018\",\"customerName\":\"(新车)太原市轮动方程汽车租赁有限公司\",\"dPRatio\":\"0.00\",\"date\":\"2017年10月19日\",\"day\":\"19\",\"downMPayment\":\"零元整\",\"downPayment\":\"0.00\",\"engineType\":\"8\",\"feeAmount\":\"1992.00\",\"feeMAmt\":\"壹仟玖佰玖拾贰元整\",\"financialAddress\":\"天津市>生态城动漫中路126动漫大厦C区C层209\",\"financialName\":\"宝腾融租\",\"financialPhone\":\"13261541870\",\"financialUserName\":\"王晓慧\",\"fuelForm\":\"999\",\"fuelOilNumber\":\"/\",\"limitDate\":\"2020年10月19日\",\"mFRatio\":\"0.83\",\"marMgin\":\"零元整\",\"margin\":\"0.00\",\"mgRatio\":\"0.00\",\"monthAccrual\":\"27.67\",\"monthCorpus\":\"277.78\",\"monthInterest\":\"305.44\",\"monthMAccrual\":\"贰拾柒元陆角柒分\",\"monthMCorpus\":\"贰佰柒拾柒元柒角捌分\",\"monthMInterest\":\"叁佰零伍元肆角肆分\",\"monthuInterest\":\"0\",\"monthuMInterest\":\"零\",\"mtAccrual\":\"27.67\",\"mtCorpus\":\"277.78\",\"oItems\":\"36\",\"orderAmt\":\"10000.00\",\"orderId\":\"TYLD2002171019001\",\"orderItems\":\"36\",\"orderMAmt\":\"壹万元整\",\"preAmt\":\"1992.00\",\"preMAmt\":\"壹仟玖佰玖拾贰元整\",\"province\":\"山西省\",\"registrationNo\":\"8\",\"sFRatio\":\"0.00\",\"seatNum\":\"88\",\"serviceFee\":\"零元整\",\"storeName\":\"(新车)太原市轮动\",\"storeNo\":\"tyld\",\"supplier\":\"8\",\"vendor\":\"/8\",\"vendorNo\":\"8\",\"yearRatio\":\"9.96\"}");
//        
        
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-ecTemplateApi/generateEc", json);
        logger.info("返回结果:{}", result);
    }
    
    @Test
    public void testJudgeSignContract() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orgCode", "1006");
        params.put("bizType", "1001");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-bizConfig/judgeSignContract", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 测试提交合同
     *      {"bizId":"TSHC2002170620003","bizType":"2002","orgCode":"1006023","status":"1"}
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午8:52:55
     */
    @Test
    public void testSubmitEc() throws Exception {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("bizId", "aaa");
//        params.put("orgCode", "1006");
//        params.put("bizType", "1001");    
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("bizId", "TYLD2002171019003");
        params.put("orgCode", "1006032");
        params.put("bizType", "2002");
        
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-ecTemplateApi/submitEc", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 测试查询合同信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月1日 下午8:53:21
     */
    @Test
    public void testSelectContractInfo() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        
//        params.put("bizId", "aaa");
//        params.put("orgCode", "1");
//        params.put("bizType", "1101");
//        params.put("ecSignerCode", "2");
        
        params.put("bizId", "NHFLD2002171009003");
        params.put("orgCode", "1006034");
        params.put("bizType", "2002");
//        params.put("ecSignerCode", "13522580626");
        
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-ecTemplateApi/selectContractInfo", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 测试 为合同下载查询合同信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午5:22:44
     */
    @Test
    public void testSelectContractInfoForDownload() throws Exception {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("ecTime", 1507799160);
        
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-ecTemplateApi/selectContractInfoForDownload", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 更新下载次数
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年10月12日 下午5:37:43
     */
    @Test
    public void testUpdateEcDownloadTimes() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("tpSignid", "1507886467883YF372");
        
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-ecTemplateApi/updateEcDownloadTimes", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     *  
     * Description: 测试actMQ
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年3月2日 下午1:50:08
     */
    @Test
    public void testActMQ() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("bizId", "aaa");
        params.put("orgCode", "1");
        params.put("bizType", "1101");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-ecTemplateApi/testActMQ", json);
        logger.info("返回结果:{}", result);
    }
    
    @Test
    public void testQueryMongo() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-logFromMongo/testErrorLog", json);
        logger.info("返回结果:{}", result);
    }
    
    @Test
    public void testSysLogin() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userCode", "sysadmin");
        params.put("userPassword", "111111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "sysLogin/login", json);
        logger.info("返回结果:{}", result);
    }
    
    @Test
    public void testSsqManualSignNotify() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", "100000");
        params.put("docId", "1497519252500YSS12");
        params.put("user", "13522580626");
        
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "unIntcpt-ecTemplateApi/ssqManualSignNotify", json);
        logger.info("返回结果:{}", result);
    }

}
