package com.ifp.wechat.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.ifp.wechat.constant.ConstantWeChat;
import com.ifp.wechat.entity.AccessTokenOAuth;
import com.ifp.wechat.entity.user.UserWeiXin;
import com.ifp.wechat.util.StringUtil;
import com.ifp.wechat.util.WeixinUtil;

/**
 * oAuth服务
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class OAuthService {

	public static Logger log = Logger.getLogger(OAuthService.class);
	
	/**
	 * wechat oauth url
	 */
	public static String OAUTH = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	/**
	 * 通过oauth获取用户详细信息
	 */
	public static String GET_USER_INFO_OAUTH = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取oauth网页认证的token
	 */
	public static String GET_ACCESS_TOKEN_OAUTH = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	
	/**
	 * 获得Oauth认证的URL
	 * @param redirectUrl	跳转的url
	 * @param charset	字符集格式
	 * @param scope	OAUTH scope
	 * @return oauth url
	 */
	public static String getOauthUrl(String redirectUrl,String charset,String scope){
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
	public static AccessTokenOAuth getOAuthAccessToken(String code) {
		String url = GET_ACCESS_TOKEN_OAUTH
				.replace("APPID", ConstantWeChat.APPID)
				.replace("SECRET", ConstantWeChat.APPSECRET)
				.replace("CODE", code);

		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);

		AccessTokenOAuth accessTokenOAuth = null;

		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.get("errcode"))
					&& jsonObject.get("errcode") != "0") {
				log.error("获取access_token失败 errcode:"
						+ jsonObject.getInt("errcode") + "，errmsg:"
						+ jsonObject.getString("errmsg"));
			} else {
				accessTokenOAuth = new AccessTokenOAuth();
				accessTokenOAuth.setAccessToken(jsonObject
						.getString("access_token"));
				accessTokenOAuth.setExpiresIn(jsonObject.getInt("expires_in"));
				accessTokenOAuth.setRefreshToken(jsonObject
						.getString("refresh_token"));
				accessTokenOAuth.setOpenid(jsonObject.getString("openid"));
				accessTokenOAuth.setScope(jsonObject.getString("scope"));
			}
		}
		return accessTokenOAuth;
	}

	/**
	 * 通过oauth获取用户详细信息
	 * 
	 * @param token
	 * @param openid
	 * @return UserWeiXin对象
	 */
	public static UserWeiXin getUserInfoOauth(String token, String openid) {
		UserWeiXin user = null;
		if (token != null) {

			String url = GET_USER_INFO_OAUTH.replace("ACCESS_TOKEN", token)
					.replace("OPENID", openid);

			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);

			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode"))
						&& jsonObject.get("errcode") != "0") {
					log.error("获取用户信息失败 errcode:"
							+ jsonObject.getInt("errcode") + "，errmsg:"
							+ jsonObject.getString("errmsg"));
				} else {
					user = new UserWeiXin();
					user.setOpenid(jsonObject.getString("openid"));
					user.setNickname(jsonObject.getString("nickname"));
					user.setSex(jsonObject.getInt("sex"));
					user.setCity(jsonObject.getString("city"));
					user.setCountry(jsonObject.getString("country"));
					user.setProvince(jsonObject.getString("province"));
					user.setLanguage(jsonObject.getString("language"));
					user.setPrivilege(jsonObject.getString("privilege"));
				}
			}
		}
		return user;
	}
}
