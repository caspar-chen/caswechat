package com.ifp.wechat.entity;

/**
 * 网页授权接口调用凭证 OAuth2.0
 * @author caspar.chen
 * @version 1.0
 */
public class AccessTokenOAuth {
	/**
	 * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 */
	private String accessToken;
	
	/**
	 * access_token接口调用凭证超时时间，单位（秒）
	 */
	private int expiresIn;
	
	/**
	 * 用户刷新access_token
	 */
	private String refreshToken;
	
	/**
	 * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
	 */
	private String openid;
	
	/**
	 * 用户授权的作用域，使用逗号（,）分隔
	 */
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public AccessTokenOAuth(String accessToken, int expiresIn,
			String refreshToken, String openid, String scope) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.openid = openid;
		this.scope = scope;
	}

	public AccessTokenOAuth() {
		super();
	}

}
