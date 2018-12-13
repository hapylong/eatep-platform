package com.iqb.eatep.ec.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * Description: 常用服务工具类
 * @author baiyapeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月12日    baiyapeng       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("rawtypes")
public class CommonUtil {
	public static byte[] getFileContent(String file) throws IOException {
		File f = new File(file);
		FileInputStream s = new FileInputStream(f);
		byte[] buffer = new byte[(int) f.length()];
		try {
			s.read(buffer);
		} catch (IOException e) {
			throw e;
		} finally {
			s.close();
		}
		return buffer;
	}
	
	/**
	 * 判断一个对象是不是空. (null, "", "0", "false", 0, false 这些都是空)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(final Object value) {
		if (null == value || "".equals(value) || "0".equals(value) || "0.0".equals(String.valueOf(value))) {
			return true;
		}
		
		//a. 转成字符串检测
		String test = String.valueOf(value).replace(" ", "").toLowerCase();
		while (true) {
			int a = test.indexOf("00");
			if (a < 0) {
				break;
			}
			test = test.replace("00", "0");
		}
		if ("".equals(test) || "false".equals(test) || "0".equals(test) || "0.0".equals(test)) {
			return true;
		}
		
		Class<?> clazz = value.getClass();
		
		//b. 检测数组
		if (clazz.isArray()) {
			return java.lang.reflect.Array.getLength(value) == 0;
		}
		
		//c. 检测method
		String[] methodTestList = {"size", "length"};
		for (int i = 0; i < methodTestList.length; i++) {
			String testName = methodTestList[i];
			Method method;
			try {
				method = clazz.getMethod(testName);
			} catch (NoSuchMethodException e) {
				continue;
			} catch (SecurityException e) {
				continue;
			}
			try {
				test = String.valueOf(method.invoke(value));
			} catch (IllegalAccessException e) {
				continue;
			} catch (IllegalArgumentException e) {
				continue;
			} catch (InvocationTargetException e) {
				continue;
			}
			return "0".equals(test);
		}
		
		return false;
	}
	
	/**
	 * isInteger
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInteger(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * isLong
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isLong(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * isFloat
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isFloat(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		try {
			Float.parseFloat(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * isEmail
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmail(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		
		int a = value.indexOf("@");
		if (a <= 0) {
			return false;
		}
		String[] items = value.split("@");
		if (items.length != 2) {
			return false;
		}
		
		String name = items[0];
		Pattern pattern = Pattern.compile("[A-Za-z0-9_\\-\\.]+");
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			return false;
		}
		
		String domain = items[1];
		if (!isDomain(domain)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * isPhone
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isPhone(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	/**
	 * isMobile
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isMobile(final String value) {
		if (!isPhone(value)) {
			return false;
		}
		if (value.length() != 11) {
			return false;
		}
		String s = value.substring(0, 2);
		Pattern pattern = Pattern.compile("(13|14|15|17|18)");
		Matcher matcher = pattern.matcher(s);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * isQQ (6 ~ 20位纯数字)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isQQ(final String value) {
		if (value == null || value.length() < 6 || value.length() > 20) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			return false;
		}
		if (value.substring(0, 1).equals("0")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 是否是域名
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDomain(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		String[] domainItems = value.split("\\.");
		if (domainItems.length < 2) {
			return false;
		}
		Pattern pattern = Pattern.compile("[A-Za-z0-9\\-]+");
		Matcher matcher;
		for (int i = 0; i < domainItems.length; i++) {
			String s = domainItems[i];
			if (s.length() < 1) {
				return false;
			}
			matcher = pattern.matcher(s);
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否是ip
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isIp(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		String[] items = value.split("\\.");
		if (items.length != 4) {
			return false;
		}
		for (int i = 0; i < items.length; i++) {
			String s = items[i];
			try {
				int n = Integer.parseInt(s);
				if (n < 0 || n > 255) {
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * isDateTime 
	 * date format: (yyyymmdd, yyyy-mm-dd, yyyy/mm/dd, yymmdd, yy-mm-dd, yy/mm/dd)
	 * time format: (mmddss, mm:dd:ss, mmdd, mm:dd)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDateTime(final String value) {
		if (value == null || value.length() < 1) {
			return false;
		}
		int a = value.indexOf(" ");
		if (a <= 0) {
			if (isDate(value) || isTime(value)) {
				return true;
			}
		}
		else {
			String d = value.substring(0, a);
			String t = value.substring(a + 1);
			if (isDate(d) && isTime(t)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * isDate. (yyyymmdd, yyyy-mm-dd, yyyy/mm/dd, yymmdd, yy-mm-dd, yy/mm/dd)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDate(final String value) {
		if (value == null) {
			return false;
		}
		String[] dateRegxList = {
			"[0-9]{4}[0-9]{2}[0-9]{2}",
			"[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}",
			"[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}",
			
			"[0-9]{2}[0-9]{2}[0-9]{2}",
			"[0-9]{2}-[0-9]{1,2}-[0-9]{1,2}",
			"[0-9]{2}/[0-9]{1,2}/[0-9]{1,2}",
			
		};
		for (int i = 0; i < dateRegxList.length; i++) {
			String regx = dateRegxList[i];
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(value);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * isTime. (mmddss, mm:dd:ss, mmdd, mm:dd)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isTime(final String value) {
		if (value == null) {
			return false;
		}
		String[] dateRegxList = {
			"[0-9]{4}[0-9]{2}[0-9]{2}",
			"[0-9]{4}:[0-9]{1,2}:[0-9]{1,2}",
			
			"[0-9]{2}[0-9]{2}",
			"[0-9]{1,2}:[0-9]{1,2}",
		};
		for (int i = 0; i < dateRegxList.length; i++) {
			String regx = dateRegxList[i];
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(value);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否是身份证
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isIdentity(final String value) {
		if (value == null) {
			return false;
		}
		if (value.length() == 15) {
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(value);
			if (!matcher.matches()) {
				return false;
			}
			String partA = value.substring(0, 6);
			String partB = value.substring(6, 12);
			String partC = value.substring(12);
			if (!isInteger(partA) || Integer.parseInt(partA) < 100000) {
				return false;
			}
			if (!isInteger(partB) || !isDate(partB)) {
				return false;
			}
			if (!isInteger(partC)) {
				return false;
			}
			return true;
		}
		else if (value.length() == 18) {
			String valueA = value.substring(0, 17);
			String valueB = value.substring(17);
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(valueA);
			if (!matcher.matches()) {
				return false;
			}
			String partA = valueA.substring(0, 6);
			String partB = valueA.substring(6, 14);
			String partC = valueA.substring(14);
			if (!isInteger(partA) || Integer.parseInt(partA) < 100000) {
				return false;
			}
			if (!isInteger(partB) || !isDate(partB)) {
				return false;
			}
			if (!isInteger(partC)) {
				return false;
			}
			
			//计算校验码
			String partD = getIdentityVCode(valueA);
			
			return valueB.equals(partD);
		}
		return false;
	}
	
	/**
	 * 获取18位身份证号码最后一位校验码
	 * 
	 * @param value
	 * @return
	 */
	public static String getIdentityVCode(final String value) {
		if (value == null || value.length() < 17) {
			return null;
		}
		
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			return null;
		}
		
		int[] nums = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
		int m = 0;
		String s = value.substring(0, 17);
		for (int i = 0; i < s.length(); i++) {
			String c = s.substring(i, i + 1);
			int n = Integer.parseInt(c);
			int n2 = nums[i];
			m += n * n2;
		}
		m = m % 11;
		
		String[] codes = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
		String code = codes[m];
		return code;
	}
	
	/**
	 * 获取字节数组的一部分
	 * 
	 * @param data
	 * @param start
	 * @param size
	 * @return
	 */
	public static byte[] slice(byte[] data, int start, int size) {
		int end = start + size - 1;
		if (end > data.length - 1) {
			end = data.length - 1;
		}
		int len = end - start + 1;
		byte[] buffer = new byte[len];
		System.arraycopy(data, start, buffer, 0, len);
		return buffer;
	}
	
	/**
	 * 把字符串数组用分隔符连接成一个字符串
	 * 
	 * @param items
	 * @param split
	 * @return
	 */
	public static String join(String[] items, String split) {
		if (items.length == 0) {
			return "";
		}
		StringBuffer s = new StringBuffer();
		int i;
		for (i = 0; i < items.length - 1; i++) {
			s.append(items[i]);
			s.append(split);
		}
		s.append(items[i]);
		return s.toString();
	}
	
	/**
	 * 获取随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int rand(int min, int max) {
		return (int)((max - min + 1) * Math.random() + min);
	}
	
	/**
	 * 获取随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long rand(long min, long max) {
		return (long)((max - min + 1) * Math.random() + min);
	}
	
	public static byte[] getResource(String path) throws IOException {
		InputStream s = CommonUtil.class.getResourceAsStream(path);
		
		ArrayList<byte[]> bufferList = new ArrayList<byte[]> ();
		byte[] buffer = new byte[4096];
		int read = 0;
		int total = 0;
		while ((read = s.read(buffer)) > 0) {
			byte[] b = new byte[read];
			System.arraycopy(buffer, 0, b, 0, read);
			bufferList.add(b);
			total += read;
		}
		s.close();
		
		byte[] result = new byte[total];
		int pos = 0;
		for (int i = 0; i < bufferList.size(); i++) {
			byte[] b = bufferList.get(i);
			System.arraycopy(b, 0, result, pos, b.length);
			pos += b.length;
		}
		
		return result;
	}
	
	/**
	 * 数据是否是pdf格式
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isPdf(final byte[] data) {
		//检查pdf数据格式
		int returnPosition = 0;
    	StringBuilder headerLineBuilder = new StringBuilder();
    	for (int i = 0; i < Math.min(data.length, 16); i++) {
    		if (data[i] == 10 || data[i] == 13) {
    			returnPosition = i;
    			break;
    		}
    		headerLineBuilder.append((char)(data[i]));
    	}
    	if (0 == returnPosition) {
    		return false;
    	}
    	String headerLine = headerLineBuilder.toString();
    	Pattern p1 = Pattern.compile("\\%PDF-[0-9]+?\\.[0-9]+?");
    	if (!p1.matcher(headerLine).matches()) {
    		return false;
    	}
    	return true;
	}	
	
	public static void removeNullValue(Map map){   
        Set set = map.keySet();   
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {   
            Object obj = (Object) iterator.next();   
            Object value =(Object)map.get(obj);   
            remove(value, iterator);   
        }   
    }   
       
    /** 
     * 移除map中的空值 
     * 
     * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。  
     * Iterator 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变， 
     * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出 java.util.ConcurrentModificationException 异常。 
     * 所以 Iterator 在工作的时候是不允许被迭代的对象被改变的。 
     * 但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性。 
     * @param obj 
     * @param iterator 
     */   
    private static void remove(Object obj,Iterator iterator){   
        if(obj instanceof String){   
            String str = (String)obj;  
            if(isEmptyForMap(str)){ 
                iterator.remove();   
            }   
             
        }else if(obj instanceof Collection){   
            Collection col = (Collection)obj;   
            if(col==null||col.isEmpty()){   
                iterator.remove();   
            }   
               
        }else if(obj instanceof Map){   
            Map temp = (Map)obj;   
            if(temp==null||temp.isEmpty()){   
                iterator.remove();   
            }   
               
        }else if(obj instanceof Object[]){   
            Object[] array =(Object[])obj;   
            if(array==null||array.length<=0){   
                iterator.remove();   
            }   
        }else{   
            if(obj==null){   
                iterator.remove();   
            }   
        }   
    }   
       
    public static boolean isEmptyForMap(Object obj){  
        return obj == null || obj.toString().length() == 0;  
    }  
}
