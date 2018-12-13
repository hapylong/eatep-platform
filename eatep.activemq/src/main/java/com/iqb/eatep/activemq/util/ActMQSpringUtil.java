package com.iqb.eatep.activemq.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * SpringBean工具类
 * @author baiyanbing
 */
@Component
public class ActMQSpringUtil implements ApplicationContextAware{
	private static ApplicationContext ctx;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}
	/**
	 * 通过bean名称获取bean实例
	 * @param id
	 * @return
	 */
	public static Object getBean(String id){
		try{
			return ctx.getBean(id);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("SpringBeanUtil 初始化失败");
			return null;
		}
	}
	
	/**
	 * 通过类名获取bean实例
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz){
		try{
			return ctx.getBean(clazz);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("SpringBeanUtil 初始化失败");
			return null;
		}
	}
	
	/**
	 * 
	 * Description: 获取request对象
	 * @param
	 * @return HttpServletRequest
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月5日 上午11:07:22
	 */
	public static HttpServletRequest getRequest(){
	    return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 
	 * Description: 获取response对象
	 * @param
	 * @return HttpServletResponse
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月5日 上午11:08:11
	 */
	public static HttpServletResponse getResponse(){
	    return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
}
