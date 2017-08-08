package com.caspar.caswechat.util.general;

import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.caspar.hoe.StringHoe;

/**
 * 微信工具类
 * @author caspar.chen
 * @date 2017-5-24
 */
public class WechatUtil {

	/**
	 * 替换emoji表情
	 * @param source	要替换的字符串
	 * @param symbol	需要替换的符号
	 * @return 替换好后的字符串
	 */
	public static String replaceEmoji(String source,String symbol) {  
        if(StringHoe.isEmpty(source)){
        	return null;
        }
        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
        Matcher emojiMatcher = emoji.matcher(source);
        if ( emojiMatcher.find()) {
            source = emojiMatcher.replaceAll(symbol);
        }
       return source;  
    }
	
	/**
	 * 检查返回结果是否正确
	 * @param jsonObject
	 * @return 如果正确，则返回true,否则返回false
	 */
	public static boolean isSuccess(JSONObject jsonObject){
		if(jsonObject == null){
			return false;
		}
		if (StringHoe.isNotEmpty(jsonObject.getString("errcode"))
				&& !(jsonObject.getIntValue("errcode") == 0)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 检查返回结果是否错误
	 * @param jsonObject
	 * @return 如果错误，则返回true,否则返回false
	 */
	public static boolean isError(JSONObject jsonObject){
		return !isSuccess(jsonObject);
	}
	
	/**
	 * byte 转为 16进制
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
}
