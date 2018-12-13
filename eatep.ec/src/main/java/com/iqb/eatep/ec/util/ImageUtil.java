package com.iqb.eatep.ec.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;

public class ImageUtil {
	
	public static final String TYPE_JPG = "Jpg";
    public static final String TYPE_PNG = "png";
	
	/**
	 * 将签章图片进行Base64编码处理
	 * 
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public static String imageToBase64(File file) throws IOException {
	    byte[] data = null;
	    // 读取图片字节数组
        InputStream in = new FileInputStream(file);
        data = new byte[in.available()];
        in.read(data);
        in.close();
	    // 对字节数组Base64编码
	    return  Base64.encodeBase64String(data);// 返回Base64编码过的字节数组字符串
	}
	
	/**
	 * 将合同文件进行字节化
	 * 
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public static byte[] fileToBytes(File file) throws IOException {
	    byte[] data = null;
	    // 读取图片字节数组
        InputStream in = new FileInputStream(file);
        data = new byte[in.available()];
        in.read(data);
        in.close();
	    return data;
	}

	/**
	 * 根据文件流判断图片类型
	 * 
	 * @param fis
	 * @return
	 * @throws IOException 
	 */
    public static String getPicType(byte[] data) throws IOException {
        //读取文件的前几个字节来判断图片格式
        byte[] b = new byte[4];
    	b[0] = data[0];
    	b[1] = data[1];
    	b[2] = data[2];
    	b[3] = data[3];
        String type = bytesToHexString(b).toUpperCase();
        if (type.contains("FFD8FF")) {
            return TYPE_JPG;
        } else if (type.contains("89504E47")) {
            return TYPE_PNG;
        }else{
        	throw new IllegalArgumentException("image type do not support");
        }
    }
    
    /**
	 * byte数组转换成16进制字符串
	 * 
	 * @param path
	 * @return
	 * @throws IOException 
	 */
    public static String bytesToHexString(byte[] src) {    
    	StringBuilder stringBuilder = new StringBuilder();    
        if (src == null || src.length <= 0) {    
        	return null;    
        }    
        for (int i = 0; i < src.length; i++) {    
           int v = src[i] & 0xFF;    
           String hv = Integer.toHexString(v);    
           if (hv.length() < 2) {    
               stringBuilder.append(0);    
           }    
           stringBuilder.append(hv);    
        }    
        return stringBuilder.toString();    
    }
}
