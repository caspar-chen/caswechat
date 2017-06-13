package com.caspar.caswechat.util.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * @return
	 */
	public static String replaceEmoji(String source,String symbol) {  
        if(StringUtil.isEmpty(source)){
        	return null;
        }
        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
        Matcher emojiMatcher = emoji.matcher(source);
        if ( emojiMatcher.find()) {
            source = emojiMatcher.replaceAll(symbol);
        }
       return source;  
    }
}
