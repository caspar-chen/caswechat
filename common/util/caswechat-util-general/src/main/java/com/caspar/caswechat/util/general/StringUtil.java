package com.caspar.caswechat.util.general;

import java.util.Formatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * String 工具类
 * 
 * @author caspar.chen
 * @version 1.0.0, 2016-9-26
 */
public class StringUtil extends StringUtils {

	/**
	 * 为null 或空字符串 或字符串“null”
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("null")    = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = true
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * @see StringUtils#isNotBlank
	 */
	public static boolean isEmpty(String str) {
		return (isBlank(str) || "null".equalsIgnoreCase(str));
	}

	/**
	 * 不为null 并且 不是空字符串 并且 不是字符串“null”
	 * @see StringUtil#isEmpty
	 */
	public static boolean isNotEmpty(String str) {
		return (!isEmpty(str));
	}

	/**
	 * 获取32位随机uuid,全大写，没有横杠"-"
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.toUpperCase().replace("-", "");
	}

    /**
     * 获取当前时间戳
     * @return
     */
	public static Long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
	
	/**
	 * byte 转为 16进制
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	/**
	 * md5加密，编码为utf-8
	 * @param param 
	 * @param source
	 * @return: String
	 */
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
			md.update(source.getBytes("UTF-8"));
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
