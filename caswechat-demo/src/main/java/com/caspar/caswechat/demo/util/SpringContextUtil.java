package com.caspar.caswechat.demo.util;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author caspar.chen
 * @date 2017-6-14
 */
public class SpringContextUtil {

	public static <T> T getBean(Class<T> requiredType){
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.setValidating(false);
		context.load("classpath*:spring.xml");
		context.refresh();
		T t = context.getBean(requiredType);
		context.close();
		return t; 
	}
	
}
