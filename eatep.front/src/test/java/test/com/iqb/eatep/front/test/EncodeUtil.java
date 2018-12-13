package test.com.iqb.eatep.front.test;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.security.InvalidKeyException;


import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.iqb.eatep.ec.contract.ssq.service.Constants;

/**
 * 
 * Description: 编码与加密服务工具类
 * @author baiyapeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月12日    baiyapeng       1.0        1.0 Version 
 * </pre>
 */
public class EncodeUtil {
	
	/**
	 * md5
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5(byte[] data) throws NoSuchAlgorithmException {
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        byte[] btInput = data;
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
	
	/**
	 * sha1
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String sha1(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.update(data);
		byte messageDigest[] = digest.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
	}
	
	/**
	 * rsaSign
	 * 
	 * @param data 
	 * @param privateKey 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 */
	public static byte[] rsaSign(byte[] data, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		// 解密由base64编码的私钥
        byte[] privateKeyBytes = Base64.decodeBase64(privateKey);
        
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance("SHA1withRSA");  
        signature.initSign(priKey);  
        signature.update(data);  
  
        return signature.sign();  
    }
	
	/**
	 * 转换字符集到utf8
	 * 
	 * @param src
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String convertToUtf8(String src) throws UnsupportedEncodingException {
		if (src == null || src.length() == 0) {
			return src;
		}
		byte[] srcData = null;
		if (Constants.ENV_CHARSET == null || Constants.ENV_CHARSET.length() < 1) {
			srcData = src.getBytes();
		} else {
			srcData = src.getBytes(Constants.ENV_CHARSET);
		}
		return new String(srcData, "UTF-8");
	}
	
	/**
	 * 从utf8转换字符集
	 * 
	 * @param src
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String convertFromUtf8(String src) throws UnsupportedEncodingException {
		if (src == null || src.length() == 0) {
			return src;
		}
		byte[] srcData = src.getBytes("UTF-8");
		if (Constants.ENV_CHARSET == null || Constants.ENV_CHARSET.length() < 1) {
			return new String(srcData);
		} else {
			return new String(srcData, Constants.ENV_CHARSET);
		}
	}
	
	/**
	 * 获取签名数据
	 * 
	 * @param args
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSignData(final String ...args) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		int len = args.length;
		for (int i = 0; i < args.length; i++) {
			buffer.append(convertToUtf8(args[i]));
			if (i < len - 1) {
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 获取签名串
	 * 
	 * @param pem
	 * @param args
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws SignatureException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static String getRsaSign(final String pem, final String...args) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException {
		String signDataString = getSignData(args);
		byte[] data = signDataString.getBytes("UTF-8");
		return Base64.encodeBase64String(EncodeUtil.rsaSign(data, pem));
	}	
	

	/**
	 * getBytes
	 * 
	 * @param requestBody
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] getBytes(final String requestBody) throws UnsupportedEncodingException {
		String newRequestBody = convertToUtf8(requestBody);
		return newRequestBody.getBytes("UTF-8");
	}
	
	/**
	 * 获取requestBody的md5值
	 * 
	 * @param requestBody
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getRequestMd5(final String requestBody) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String newRequestBody = convertToUtf8(requestBody);
		byte[] data = newRequestBody.getBytes("UTF-8");
		return md5(data);
	}	
	
	/**
	 * urlEncode
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(final String data) throws UnsupportedEncodingException {
		String newData = convertToUtf8(data);
		return URLEncoder.encode(newData, "UTF-8");
	}
}
