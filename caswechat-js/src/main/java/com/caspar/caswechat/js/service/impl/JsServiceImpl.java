package com.caspar.caswechat.js.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.caspar.caswechat.js.entity.JSSign;
import com.caspar.caswechat.js.service.JsService;
import com.caspar.caswechat.start.service.AccessTokenService;
import com.caspar.caswechat.util.general.HttpRequestUtil;
import com.caspar.caswechat.util.general.PropertyUtil;
import com.caspar.caswechat.util.general.StringUtil;
import com.caspar.caswechat.util.redis.RedisHelper;

/**
 * @author caspar.chen
 * @date 2017-6-2
 */
@Service
public class JsServiceImpl implements JsService{

	@Autowired
	private AccessTokenService accessTokenService;
	
	@Autowired
	private RedisHelper redisHelper;
	
	private final static String jsApiTicketKey = "wechat_jsticket";
	
	private static final Logger log = LoggerFactory
			.getLogger(JsServiceImpl.class);
	
	@Override
	public String getJsApiTicket(String accessToken) {
		String jsApiTicket = redisHelper.getString(jsApiTicketKey);
		if(StringUtil.isNotEmpty(jsApiTicket)){
			return jsApiTicket;
		}
		String url = PropertyUtil.get("url_js_ticket_get")
				.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpRequestUtil.createDefault().doGetToJsonObject(url);
		if (jsonObject != null) {
			if (StringUtil.isNotEmpty(jsonObject.getString("errcode"))
					&& jsonObject.getIntValue("errcode")!=0) {
				log.error("获取ticket失败 : " + jsonObject.toString());
			} else {
				jsApiTicket = jsonObject.getString("ticket");
				redisHelper.setString(jsApiTicketKey, 7000, jsApiTicket);
			}
		}
    	return jsApiTicket;
	}
	
	@Override
	public String getJsApiTicket() {
		return getJsApiTicket(accessTokenService.getAccessTokenStr());
	}

	@Override
	public JSSign getJsSign(String accessToken, String jsapiTicket, String url) {
		String nonceStr = StringUtil.getUUID();
        Long timestamp = StringUtil.getTimestamp();
        String string1;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                  "&noncestr=" + nonceStr +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = StringUtil.byteToHex(crypt.digest());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        JSSign js = new JSSign();
        js.setAppId(PropertyUtil.get("appId"));
        js.setJsapiTicket(jsapiTicket);
        js.setNonceStr(nonceStr);
        js.setSignature(signature);
        js.setTimestamp(timestamp);
        js.setUrl(url);
		return js;
	}

	@Override
	public JSSign getJsSign(String url) {
		String accessToken = accessTokenService.getAccessTokenStr();
		String jsapiTicket = getJsApiTicket();
		return getJsSign(accessToken, jsapiTicket, url);
	}

}
