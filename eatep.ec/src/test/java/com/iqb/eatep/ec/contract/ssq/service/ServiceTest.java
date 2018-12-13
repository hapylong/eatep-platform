package com.iqb.eatep.ec.contract.ssq.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.contract.ssq.service.Constants.CERT_IDENT_TYPE;
import com.iqb.eatep.ec.contract.ssq.service.Constants.CONTRACT_NEEDVIDEO;
import com.iqb.eatep.ec.contract.ssq.service.Constants.DEVICE_TYPE;
import com.iqb.eatep.ec.contract.ssq.service.Constants.DOWNLOAD_TYPE;
import com.iqb.eatep.ec.contract.ssq.service.Constants.USER_TYPE;
import com.iqb.eatep.ec.contract.ssq.service.impl.BestSignServiceImpl;
import com.iqb.eatep.ec.util.HttpUtil;
import com.iqb.eatep.ec.util.ImageUtil;

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

@SuppressWarnings("unused")
public class ServiceTest {
	
	@Test
    public void testRegUser() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
        Integer userType = USER_TYPE.PERSONAL.value();
        
        // uid 1f26e87e589a4c379c86f023a4ee1537
        String ecSignerEmail = "1434943506@qq.com";
        String ecSignerPhone = "15732124062";
        String ecSignerName = "白亚鹏";
        
        // uid 29b67f00e446461c9933bbf73c936839
        
		String ecSignerEmail2 = "920557931@qq.com";
        String ecSignerPhone2 = "18501216390";
        String ecSignerName2 = "吴百豹";
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ecSignerType", userType);        
        map.put("ecSignerEmail", ecSignerEmail);
        map.put("ecSignerPhone", ecSignerPhone);
        map.put("ecSignerName", ecSignerName);
        
        //map.put("ecSignerEmail", ecSignerEmail2);
        //map.put("ecSignerPhone", ecSignerPhone2);
        //map.put("ecSignerName", ecSignerName2);
        try {
        	//调用用户注册
			service.regUser(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testUploadUserImage() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());		
		
		Integer userType = USER_TYPE.PERSONAL.value();
        
        String ecSignerEmail = "1434943506@qq.com";
        String ecSignerPhone = "15732124062";
        String ecSignerName = "白亚鹏";
        
        String ecSignerEmail2 = "920557931@qq.com";
        String ecSignerPhone2 = "18501216390";
        String ecSignerName2 = "吴百豹";
        
        String img = "E:/ssq/sdk/src/main/resources/seal.jpg";
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("ecSignerType", userType);        
        map.put("ecSignerEmail", ecSignerEmail);
        map.put("ecSignerPhone", ecSignerPhone);
        map.put("ecSignerName", ecSignerName);
        
        //map.put("ecSignerEmail", ecSignerEmail2);
        //map.put("ecSignerPhone", ecSignerPhone2);
        //map.put("ecSignerName", ecSignerName2);
        
        
        
        try {
        	map.put("ecSignerImgDataBlob", ImageUtil.fileToBytes(new File(img)));
			service.uploadUserImage(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
		
	@Test
    public void testCertificateApply() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		String ecSignerEmail = "1434943506@qq.com";
        String ecSignerPhone = "15732124062";
        String ecSignerName = "白亚鹏";
        
        String ecSignerEmail2 = "920557931@qq.com";
        String ecSignerPhone2 = "18501216390";
        String ecSignerName2 = "吴百豹";
        
        String ecSignerCertPwd = "15732124062";    
        Integer ecSignerCertType = 0;
        String ecSignerCertNo = "130131199008088888";
        String ecSignerProvince = "北京市";
        String ecSignerCity = "北京市";
        String ecSignerAddress = "海淀区";
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("ecSignerEmail", ecSignerEmail);
        map.put("ecSignerPhone", ecSignerPhone);
        map.put("ecSignerName", ecSignerName);
        
        //map.put("ecSignerEmail", ecSignerEmail2);
        //map.put("ecSignerPhone", ecSignerPhone2);
        //map.put("ecSignerName", ecSignerName2);
        
        map.put("ecSignerCertPwd", ecSignerCertPwd);
        map.put("ecSignerCertType", ecSignerCertType);
        map.put("ecSignerCertNo", ecSignerCertNo);
        map.put("ecSignerProvince", ecSignerProvince);
        map.put("ecSignerCity", ecSignerCity);
        map.put("ecSignerAddress", ecSignerAddress);
		
        try {
			service.certificateApplyForPersonal(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testContractUpload() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		//signId 1488512880613APNJ2
		//docId	 1488512880613RFTV1
		String file = "E:/ssq/sdk/src/main/resources/租房合同四.docx";
		
		Map<String, Object> sendUser = new HashMap<String, Object>();
		sendUser.put("ecSignerType", USER_TYPE.ENTERPRISE.value());
		sendUser.put("ecSignerEmail", "mayongming@iqianbang.com");
		sendUser.put("ecSignerPhone", "18811080261");		
		sendUser.put("ecSignerName", "北京爱钱帮财务科技有限公司");
		sendUser.put("ecSenderType", 0);
		sendUser.put("ecTheme", "合同签署");
		sendUser.put("ecEffectiveDays", 99);
		
		List<Map<String, Object>> receiveUserList = new ArrayList<Map<String, Object>>();
		Map<String, Object> receiveUser = new HashMap<String, Object>();
		receiveUser.put("ecSignerType", USER_TYPE.PERSONAL.value());
		receiveUser.put("ecSignerEmail", "1434943506@qq.com");
		receiveUser.put("ecSignerPhone", "15732124062");		
		receiveUser.put("ecSignerName", "白亚鹏");
		receiveUserList.add(receiveUser);
		
		Map<String, Object> receiveUser2 = new HashMap<String, Object>();
		receiveUser2.put("ecSignerType", USER_TYPE.PERSONAL.value());
		receiveUser2.put("ecSignerEmail", "920557931@qq.com");
		receiveUser2.put("ecSignerPhone", "18501216390");		
		receiveUser2.put("ecSignerName", "吴百豹");
		receiveUserList.add(receiveUser2);
		
		Map<String, Object> map = new HashMap<String, Object>();
        
		map.put("ecFileName", "合同.docx");
        map.put("sendUser", sendUser);
        map.put("receiveUserList", receiveUserList);
		
        try {
        	map.put("ecFileBlob", ImageUtil.fileToBytes(new File(file)));
			service.contractUpload(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testContractUpload2() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		List<Map<String, Object>> receiveUserList = new ArrayList<Map<String, Object>>();
		Map<String, Object> receiveUser = new HashMap<String, Object>();
		receiveUser.put("ecSignerType", USER_TYPE.PERSONAL.value());
		receiveUser.put("ecSignerEmail", "920557931@126.com");
		receiveUser.put("ecSignerPhone", "18501216390");		
		receiveUser.put("ecSignerName", "吴百豹");
		receiveUserList.add(receiveUser);
		
		Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("tpSignid", "1488512880613APNJ2");
        map.put("receiveUserList", receiveUserList);
		
        try {
			service.contractUploadAdd(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testAutoSign() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		String ecSignerEmail = "1434943506@qq.com";
		String ecSignerPhone = "15732124062";
        String tpSignid = "1488512880613APNJ2";
		
        List<Map<String, Object>> coordinateList = new ArrayList<Map<String, Object>>();
		Map<String, Object> coordinate = new HashMap<String, Object>();
		coordinate.put("ecSealPageNum", 1);
		coordinate.put("ecSealPositionX", 50);
		coordinate.put("ecSealPositionY", 30);
		coordinateList.add(coordinate);
		
		Map<String, Object> map = new HashMap<String, Object>();
        
		map.put("ecSignerEmail", ecSignerEmail);
		map.put("ecSignerPhone", ecSignerPhone);
        map.put("tpSignid", tpSignid);
        map.put("coordinateList", coordinateList);
        
        try {
			service.autoSign(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testGetSignPage() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		String ecSignerEmail = "920557931@qq.com";
		String ecSignerPhone = "18501216390";
		String tpSignid = "1488512880613APNJ2";		
		
		List<Map<String, Object>> coordinateList = new ArrayList<Map<String, Object>>();
		Map<String, Object> coordinate = new HashMap<String, Object>();
		coordinate.put("ecSealPageNum", 2);
		coordinate.put("ecSealPositionX", 50);
		coordinate.put("ecSealPositionY", 30);
		coordinateList.add(coordinate);
		
		Integer deviceType = DEVICE_TYPE.PC.value();
		String tpReturnUrl = "http://www.baidu.com/";
		
		Map<String, Object> map = new HashMap<String, Object>();
        
		map.put("ecSignerEmail", ecSignerEmail);
		map.put("ecSignerPhone", ecSignerPhone);
        map.put("tpSignid", tpSignid);
        map.put("coordinateList", coordinateList);
        map.put("tpReturnUrl", tpReturnUrl);
        map.put("signDeviceType", deviceType);
        
        try {
			service.getSignPage(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testEndContract() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		Map<String, Object> map = new HashMap<String, Object>();        
        map.put("tpSignid", "1488512880613APNJ2");

        try {
			service.endContract(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testContractInfo() {
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		Map<String, Object> map = new HashMap<String, Object>();        
        map.put("tpSignid", "149422374627345NL2");
		
        try {
			JSONObject objs = service.contractInfo(map);
			objs.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testViewContract() {
		//https://www.ssqsign.com/openpage/ViewContract.page?mid=E0000000000000000009&sign=xHRoVdlpVH1JMY7dgBqrSzsjF1pSvVzNLtVXa%2BVRM%2FE08zLKqtyG6tAKbWKy8CeGnlZTvReDskrkpzW1QGGwsePrlTodYqJHhatdFxPUO8RU%2FMo%2BjQ0YYrJjZw0lF%2Fm%2B%2Fv7IlMLan1uPKuqa10zKBnpVQUlPq2oSogaw89x0NvU%3D&fsdid=1488360356744H77L2&docid=1488360356744XMTM1
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		Map<String, Object> map = new HashMap<String, Object>();        
        map.put("tpSignid", "1488512880613APNJ2");
        map.put("tpDocid", "1488512880613RFTV1");
		
        try {
			service.viewContract(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Test
    public void testContractDownload() {
		//PDF https://www.ssqsign.com/openpage/contractDownloadMobile.page?mid=E0000000000000000009&sign=LYYt9xfiKOp3Ymro4JDpyg8%2FLSSl2TBoRLgCrAE2dIuJtPYCPX8PZn%2FSqcA8YZe1HBc50Vd7HEilitZs%2FvgHx6ufbZZ32acj%2BjyxC66SLS%2F1oA21EVxLGyZOhaxG4J6n1CIAPfUSr922nSQbAtkstkpm%2FdG2i0ngtmd6liaNlCM%3D&fsdid=1488360356744H77L2&status=3
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		Map<String, Object> map = new HashMap<String, Object>();        
        map.put("tpSignid", "1488512880613APNJ2");
		
        try {
			service.contractDownload(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    
	
	@Test
    public void testContractDownloadLocal() {
		//PDF https://www.ssqsign.com/openpage/contractDownloadMobile.page?mid=E0000000000000000009&sign=LYYt9xfiKOp3Ymro4JDpyg8%2FLSSl2TBoRLgCrAE2dIuJtPYCPX8PZn%2FSqcA8YZe1HBc50Vd7HEilitZs%2FvgHx6ufbZZ32acj%2BjyxC66SLS%2F1oA21EVxLGyZOhaxG4J6n1CIAPfUSr922nSQbAtkstkpm%2FdG2i0ngtmd6liaNlCM%3D&fsdid=1488360356744H77L2&status=3
		BestSignServiceImpl service = new BestSignServiceImpl();
		//service.setHttpUtil(new HttpUtil());
		
		Map<String, Object> map = new HashMap<String, Object>();        
        map.put("tpSignid", "1488512880613APNJ2");
		
        try {
			System.out.print(service.contractDownloadLocal(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    
}
