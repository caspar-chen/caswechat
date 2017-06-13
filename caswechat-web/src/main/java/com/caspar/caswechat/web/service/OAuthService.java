package com.caspar.caswechat.web.service;

import com.caspar.caswechat.user.entity.UserWechat;
import com.caspar.caswechat.web.entity.AccessTokenOAuth;

/**
 * OAuth服务
 * 
 * @author caspar.chen
 * @date 2017-5-15
 */
public interface OAuthService {

	/**
	 * 获得Oauth认证的URL
	 * 
	 * @param redirectUrl
	 *            跳转的url
	 * @param charset
	 *            字符集格式
	 * @param scope
	 *            OAUTH scope
	 * @return oauth url
	 */
	public String getOauthUrl(String redirectUrl, String charset, String scope);

	/**
	 * 获取Access_Token（oAuth认证,此access_token与基础支持的access_token不同）
	 * 
	 * @param code
	 *            用户授权后得到的code
	 * @return AccessTokenOAuth对象
	 */
	public AccessTokenOAuth getOAuthAccessToken(String code);

	/**
	 * 通过oauth获取用户详细信息
	 * 
	 * @param token
	 * @param openid
	 * @return UserWechat对象
	 */
	public UserWechat getUserInfoOAuth(String token, String openid);
}
