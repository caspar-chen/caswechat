package com.caspar.caswechat.js.service;

import com.caspar.caswechat.js.entity.JSSign;

/**
 * @author caspar.chen
 * @date 2017-6-2
 */
public interface JsService {

	/**
	 * 获得js api 的票据
	 * @param accessToken
	 * @return
	 */
	String getJsApiTicket(String accessToken);
	
	String getJsApiTicket();
	
	/**
	 * 生成wechat js签名
	 * @param accessToken
	 * @param jsapiTicket
	 * @param url
	 * @return
	 */
	JSSign getJsSign(String accessToken,String jsapiTicket,String url);
	
	JSSign getJsSign(String url);
	
}
