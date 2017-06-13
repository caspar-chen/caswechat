package com.caspar.caswechat.util.general;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 集合工具类
 * 
 * @author caspar.chen
 * @version 1.0.0, 2016-9-26
 */
public class ArrayUtil extends ArrayUtils{

	/**
	 * List不为空，且至少有一条记录
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	/**
	 * List为空，或list里面没有任何记录
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		boolean flag = false;
		if (list == null || list.size() <= 0) {
			flag = true;
		}
		return flag;
	}
}
