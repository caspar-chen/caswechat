package com.caspar.caswechat.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.caspar.caswechat.user.entity.UserWechat;
import com.caspar.caswechat.util.general.ConstantWeChat;
import com.caspar.caswechat.util.general.HttpRequestUtil;
import com.caspar.caswechat.util.general.PropertyUtil;
import com.caspar.caswechat.util.general.StringUtil;
import com.caspar.caswechat.web.entity.AccessTokenOAuth;
import com.caspar.caswechat.web.service.OAuthService;

/**
 * @author caspar.chen
 * @date 2017-5-15
 */
@Service
public class OAuthServiceImpl implements OAuthService{

	private static final Logger log = LoggerFactory
			.getLogger(OAuthService.class);

	/**
	 * wechat oauth url
	 */
	public static String OAUTH = PropertyUtil.get("url_oauth");

	/**
	 * 通过oauth获取用户详细信息
	 */
	public static String GET_USER_INFO_OAUTH = PropertyUtil
			.get("url_oauth_user_info");

	/**
	 * 获取oauth网页认证的token
	 */
	public static String GET_ACCESS_TOKEN_OAUTH = PropertyUtil
			.get("url_oauth_access_token");

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
	@Override
	public String getOauthUrl(String redirectUrl, String charset,
			String scope) {
		String url = "";
		try {
			url = OAUTH
					.replace("APPID", ConstantWeChat.APPID)
					.replace("REDIRECT_URI",
							URLEncoder.encode(redirectUrl, charset))
					.replace("SCOPE", scope);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * 获取Access_Token（oAuth认证,此access_token与基础支持的access_token不同）
	 * 
	 * @param code
	 *            用户授权后得到的code
	 * @return AccessTokenOAuth对象
	 */
	@Override
	public AccessTokenOAuth getOAuthAccessToken(String code) {
		String url = GET_ACCESS_TOKEN_OAUTH
				.replace("APPID", ConstantWeChat.APPID)
				.replace("SECRET", ConstantWeChat.APPSECRET)
				.replace("CODE", code);

		JSONObject jsonObject = HttpRequestUtil.createDefault().doGetToJsonObject(url);

		AccessTokenOAuth accessTokenOAuth = null;
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.getString("errcode"))
					&& !"0".equals(jsonObject.get("errcode"))) {
				log.error("获取access_token失败 " + jsonObject.toString());
			} else {
				accessTokenOAuth = JSONObject.toJavaObject(jsonObject,
						AccessTokenOAuth.class);
			}
		}
		return accessTokenOAuth;
	}

	/**
	 * 通过oauth获取用户详细信息
	 * 
	 * @param token
	 * @param openid
	 * @return UserWechat对象
	 */
	@Override
	public UserWechat getUserInfoOAuth(String token, String openid) {
		UserWechat user = null;
		if (token != null) {
			String url = GET_USER_INFO_OAUTH.replace("ACCESS_TOKEN", token)
					.replace("OPENID", openid);

			JSONObject jsonObject = HttpRequestUtil.createDefault().doGetToJsonObject(url);
			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.getString("errcode"))
						&& !"0".equals(jsonObject.get("errcode"))) {
					log.error("获取用户信息失败 : " + jsonObject.toString());
				} else {
					user = JSONObject.toJavaObject(jsonObject, UserWechat.class);
				}
			}
		}
		return user;
	}
}
