package test.com.iqb.eatep.front.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcAttr.ThirdEcConst.SSQKeysConst;
import com.iqb.eatep.ec.contract.ssq.service.Constants.DOWNLOAD_TYPE;
import com.iqb.eatep.ec.util.httputil.SendHttpsUtil;

/**
 * 
 * Description: 上上签服务接口测试类
 * @author baiyapeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月12日    baiyapeng       1.0        1.0 Version 
 * </pre>
 */
public class ServiceTest {
    
    private BestSignServiceImpl service = new BestSignServiceImpl();
	
	@Test
    public void testContractInfo() {
		Map<String, Object> map = new HashMap<String, Object>();        
        map.put("tpSignid", "14942292140468JR32");
        try {
			JSONObject objs = service.contractInfo(map);
			System.out.println(objs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
	public void simulationSSQNotifyForAutoSign(){
	    JSONObject objs = new JSONObject();
        objs.put(SSQKeysConst.SSQ_RET_DOCID_KEY, "1496312863976W4VU2");
        objs.put(SSQKeysConst.SSQ_RET_USER_KEY, "13522580626");
        objs.put(SSQKeysConst.SSQ_RET_CODE_KEY, SSQKeysConst.SSQ_SUCC_CODE);
        SendHttpsUtil.postMsg4GetMap("http://localhost/eatep.front/unIntcpt-ecTemplateApi/ssqManualSignNotify", JSONObject.toJSONString(objs));
	}
	
	@Test
	public void testContractDownload(){
	    String url = "";
	    try {
            url = service.contractDownload("150763925783642XO2", DOWNLOAD_TYPE.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    System.out.println(url);
	}
	
}
