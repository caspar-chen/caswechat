package com.caspar.caswechat.start.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.caspar.caswechat.start.entity.AccessToken;
import com.caspar.caswechat.start.service.AccessTokenService;
import com.caspar.caswechat.util.general.HttpRequestUtil;
import com.caspar.caswechat.util.general.PropertyUtil;
import com.caspar.caswechat.util.general.StringUtil;
import com.caspar.caswechat.util.redis.RedisHelper;

/**
 * AccessToken服务
 * 
 * @author caspar.chen
 * @date 2017-5-12
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

	@Autowired
	private RedisHelper redisHelper;

	private static final Logger log = LoggerFactory
			.getLogger(AccessTokenServiceImpl.class);

	/**
	 * accesstoken对象存储在redis中的key值
	 */
	private final static String accessTokenkey = "wechat_at";

	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	private final static String URL_ACCESS_TOKEN = PropertyUtil
			.get("url_access_token");

	
	
	/**
	 * 获取access_token对象
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public AccessToken getAccessToken() {
		//缓存在redis中，设置过期时间为2小时以内，因为token过期时间是7200秒=2小时，而且一天只有200次请求次数
		String tokenStr = redisHelper.getString(accessTokenkey);
		if(StringUtil.isNotEmpty(tokenStr)){
			return JSONObject.parseObject(tokenStr, AccessToken.class);
		}
		String url = URL_ACCESS_TOKEN.replace("APPID",
				PropertyUtil.get("appId")).replace("APPSECRET",
				PropertyUtil.get("appSecret"));
		JSONObject jsonObject = HttpRequestUtil.createDefault().doGetToJsonObject(url);
		AccessToken accessToken = null;
		// 如果请求成功
		if (jsonObject != null) {
			try {
				System.out.println(jsonObject.toJSONString());
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
				//set 到redis
				redisHelper.setString(accessTokenkey, 3000, JSONObject.toJSONString(accessToken));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败:" + jsonObject.toString());
			}
		}
		return accessToken;
	}

	/**
	 * 获取token值
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return token值
	 */
	public String getAccessTokenStr() {
		AccessToken at = getAccessToken();
		if (null != at) {
			return at.getToken();
		} else {
			return null;
		}
	}
}
