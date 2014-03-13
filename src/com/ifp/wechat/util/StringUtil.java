package com.ifp.wechat.util;

import java.util.UUID;

/**
 * String 工具类
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class StringUtil {

	public static boolean isEmpty(Object value) {
		return (value == null || "".equals(value));
	}

	public static boolean isNotEmpty(Object value) {
		return (!isEmpty(value));
	}

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.toUpperCase().replace("-", "");
	}

	public static String getMD5(String source) {
		if (source == null || source == "") {
			return null;
		}
		String str = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source.getBytes());
			byte tmp[] = md.digest();
			char chstr[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				chstr[k++] = hexDigits[byte0 >>> 4 & 0xf];
				chstr[k++] = hexDigits[byte0 & 0xf];
			}
			str = new String(chstr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

}
