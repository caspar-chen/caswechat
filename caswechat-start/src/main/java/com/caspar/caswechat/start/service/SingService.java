package com.caspar.caswechat.start.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证签名
 * @author caspar.chen
 * @date 2017-5-15
 */
public interface SingService {
	/**
	 * 认证微信签名
	 * 
	 * @param token
	 * @param request
	 * @return 是否验证成功
	 */
	boolean checkSignature(HttpServletRequest request);

	/**
	 * 认证微信签名
	 * 
	 * @param token
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return 是否验证成功
	 */
	boolean checkSignature(String signature,
			String timestamp, String nonce);
}
