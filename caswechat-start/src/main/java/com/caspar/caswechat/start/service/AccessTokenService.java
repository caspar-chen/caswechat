package com.caspar.caswechat.start.service;

import com.caspar.caswechat.start.entity.AccessToken;

/**
 * AccessToken服务
 * 
 * @author caspar.chen
 * @date 2017-5-12
 */
public interface AccessTokenService {

	/**
	 * 获取access_token对象
	 * 
	 */
	public AccessToken getAccessToken();

	/**
	 * 获取token值
	 * 
	 * @return token值
	 */
	public String getAccessTokenStr();
}
