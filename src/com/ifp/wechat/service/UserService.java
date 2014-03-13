package com.ifp.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ifp.wechat.entity.user.UserWeiXin;
import com.ifp.wechat.util.DateFormart;
import com.ifp.wechat.util.StringUtil;
import com.ifp.wechat.util.WeixinUtil;
/**
 * 用户管理
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class UserService {

	public static Logger log = Logger.getLogger(UserService.class);

	/**
	 * 获取用户详细信息
	 */
	public static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取用户openid列表
	 */
	public static String GET_USER_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

	
	/**
	 * 获取用户详细信息
	 * 
	 * @param openid
	 * @return UserWeiXin 用户详细信息
	 */
	public static UserWeiXin getUserInfo(String openid) {
		String token = WeixinUtil.getToken();

		UserWeiXin user = null;

		if (token != null) {
			String url = GET_USER_INFO.replace("ACCESS_TOKEN", token).replace(
					"OPENID", openid);
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);

			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode"))
						&& jsonObject.get("errcode") != "0") {
					log.error("获取用户信息失败 errcode:"
							+ jsonObject.getInt("errcode") + "，errmsg:"
							+ jsonObject.getString("errmsg"));
				} else {
					user = new UserWeiXin();
					user.setSubscribe(jsonObject.getInt("subscribe"));
					user.setOpenid(jsonObject.getString("openid"));
					user.setNickname(jsonObject.getString("nickname"));
					user.setSex(jsonObject.getInt("sex"));
					user.setCity(jsonObject.getString("city"));
					user.setCountry(jsonObject.getString("country"));
					user.setProvince(jsonObject.getString("province"));
					user.setLanguage(jsonObject.getString("language"));
					user.setHeadimgurl(jsonObject.getString("headimgurl"));
					long subscibeTime = jsonObject.getLong("subscribe_time");
					Date st = DateFormart
							.paserYYYY_MM_DD_HHMMSSToDate(subscibeTime);
					user.setSubscribe_time(st);

				}
			}

		}
		return user;
	}

	/**
	 * 获取关注者OpenID列表
	 * 
	 * @return List<String> 关注者openID列表
	 */
	public static List<String> getUserOpenIdList() {
		String token = WeixinUtil.getToken();
		List<String> list = null;
		if (token != null) {
			String url = GET_USER_OPENID_LIST.replace("ACCESS_TOKEN", token)
					.replace("NEXT_OPENID", "");

			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);

			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode"))
						&& jsonObject.get("errcode") != "0") {
					log.error("获取关注用户列表失败 errcode:"
							+ jsonObject.getInt("errcode") + "，errmsg:"
							+ jsonObject.getString("errmsg"));
				} else {
					list = new ArrayList<String>();
					JSONObject data = jsonObject.getJSONObject("data");
					String openidStr = data.getString("openid");
					openidStr = openidStr.substring(1, openidStr.length() - 1);
					openidStr = openidStr.replace("\"", "");
					String openidArr[] = openidStr.split(",");
					for (int i = 0; i < openidArr.length; i++) {
						list.add(openidArr[i]);
					}
				}
			}

		}
		return list;
	}

	/**
	 * 获取关注者列表
	 * 
	 * @return List<UserWeiXin> 关注者列表信息
	 */
	public static List<UserWeiXin> getUserList() {
		List<UserWeiXin> list = new ArrayList<UserWeiXin>();

		// 获取关注用户openid列表
		List<String> listStr = getUserOpenIdList();

		if (listStr == null || listStr.size() == 0) {
			return null;
		}
		for (int i = 0; i < listStr.size(); i++) {
			// 根据openid查询用户信息
			UserWeiXin user = getUserInfo(listStr.get(i));
			if (user != null) {
				list.add(user);
			}
		}
		return list;
	}

}
