package com.caspar.caswechat.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caspar.caswechat.start.service.AccessTokenService;
import com.caspar.caswechat.user.entity.UserOpenIdList;
import com.caspar.caswechat.user.entity.UserTag;
import com.caspar.caswechat.user.entity.UserWechat;
import com.caspar.caswechat.user.service.UserWechatService;
import com.caspar.caswechat.util.general.HttpRequestUtil;
import com.caspar.caswechat.util.general.PropertyUtil;
import com.caspar.caswechat.util.general.StringUtil;
import com.caspar.caswechat.util.general.WechatUtil;

/**
 * @author caspar.chen
 * @date 2017-5-15
 */
@Service
public class UserWechatServiceImpl implements UserWechatService {

	private static final Logger log = LoggerFactory
			.getLogger(UserWechatServiceImpl.class);

	@Autowired
	private AccessTokenService accessTokenService;

	/**
	 * 获取用户详细信息
	 */
	public static String GET_USER_INFO = PropertyUtil.get("url_user_info_get");

	/**
	 * 获取用户openid列表
	 */
	public static String GET_USER_OPENID_LIST = PropertyUtil
			.get("url_user_openid_list");

	public static String USER_TAG_ADD = PropertyUtil.get("url_user_tag_add");
	public static String USER_TAG_GET = PropertyUtil.get("url_user_tag_get");
	public static String USER_TAG_UPD = PropertyUtil.get("url_user_tag_upd");
	public static String USER_TAG_DEL = PropertyUtil.get("url_user_tag_del");

	public static String USER_TAG_USER = PropertyUtil.get("url_user_tag_user");
	public static String USER_TAG_USER_BATCH_ADD = PropertyUtil
			.get("url_user_tag_user_batch_add");
	public static String USER_TAG_USER_BATCH_DEL = PropertyUtil
			.get("url_user_tag_user_batch_del");
	public static String USER_TAG_LIST_BY_USER = PropertyUtil
			.get("url_user_tag_list_by_user");

	@Override
	public UserWechat getUserInfo(String openid) {
		String token = accessTokenService.getAccessTokenStr();
		UserWechat user = null;
		if (token != null) {
			String url = GET_USER_INFO.replace("ACCESS_TOKEN", token).replace(
					"OPENID", openid);
			JSONObject jsonObject = HttpRequestUtil.createDefault()
					.doGetToJsonObject(url);

			if (null != jsonObject) {
				if (WechatUtil.isError(jsonObject)) {
					log.error("获取用户信息失败 : " + jsonObject.toString());
				} else {
					user = JSONObject
							.toJavaObject(jsonObject, UserWechat.class);
				}
			}

		}
		return user;
	}

	@Override
	public UserOpenIdList getUserOpenIdList(String nextOpenId) {
		UserOpenIdList userOpenIdList = new UserOpenIdList();
		String token = accessTokenService.getAccessTokenStr();

		if (token != null) {
			nextOpenId = StringUtil.isEmpty(nextOpenId) ? "" : nextOpenId;
			String url = GET_USER_OPENID_LIST.replace("ACCESS_TOKEN", token)
					.replace("NEXT_OPENID", nextOpenId);

			JSONObject jsonObject = HttpRequestUtil.createDefault()
					.doGetToJsonObject(url);

			if (null != jsonObject) {
				if (WechatUtil.isError(jsonObject)) {
					log.error("获取关注用户列表失败 errcode: : " + jsonObject.toString());
				} else {
					userOpenIdList.setTotal(jsonObject.getInteger("total"));
					userOpenIdList.setCount(jsonObject.getInteger("count"));
					userOpenIdList.setNextOpenid(jsonObject
							.getString("next_openid"));
					JSONArray openIdArr = jsonObject.getJSONObject("data")
							.getJSONArray("openid");
					String[] ids = openIdArr.toArray(new String[openIdArr
							.size()]);
					userOpenIdList.setOpenIds(ids);
				}
			}

		}
		return userOpenIdList;
	}

	@Override
	public List<UserWechat> getUserList(String nextOpenId) {
		List<UserWechat> list = new ArrayList<UserWechat>();

		// 获取关注用户openid列表
		UserOpenIdList userOpenId = getUserOpenIdList(nextOpenId);

		if (userOpenId == null || userOpenId.getOpenIds().length <= 0) {
			return null;
		}
		for (int i = 0; i < userOpenId.getOpenIds().length; i++) {
			// 根据openid查询用户信息
			UserWechat user = getUserInfo(userOpenId.getOpenIds()[i]);
			if (user != null) {
				list.add(user);
			}
		}
		return list;
	}

	@Override
	public UserTag createTag(String tag) {
		String token = accessTokenService.getAccessTokenStr();
		UserTag userTag = null;

		// 组件post json字符串
		String postJson = "{\"tag\" : {\"name\" : \"" + tag + "\"}}";

		if (token != null) {
			String url = USER_TAG_ADD.replace("ACCESS_TOKEN", token);
			String result = HttpRequestUtil.createDefault().doPost(url,
					postJson);

			if (StringUtil.isNotEmpty(result)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (WechatUtil.isError(jsonObject)) {
					log.error("创建标签失败 : " + jsonObject.toString());
				} else {
					userTag = JSONObject
							.toJavaObject(jsonObject, UserTag.class);
				}
			}
		}
		return userTag;
	}

	@Override
	public List<UserTag> getTags() {
		String token = accessTokenService.getAccessTokenStr();
		List<UserTag> tagList = null;
		if (token != null) {
			String url = USER_TAG_GET.replace("ACCESS_TOKEN", token);
			JSONObject jsonObject = HttpRequestUtil.createDefault()
					.doGetToJsonObject(url);
			if (null != jsonObject) {
				if (WechatUtil.isError(jsonObject)) {
					log.error("获取标签失败 : " + jsonObject.toString());
				} else {
					tagList = JSONArray.parseArray(
							jsonObject.getString("tags"), UserTag.class);
				}
			}

		}
		return tagList;
	}

	@Override
	public boolean updateTag(UserTag userTag) {
		String token = accessTokenService.getAccessTokenStr();
		// 组件post json字符串
		String postJson = "{\"tag\" : {\"id\" : \"" + userTag.getId()
				+ "\",\"name\" : \"" + userTag.getName() + "\"}}";
		boolean bo = false;
		if (token != null) {
			String url = USER_TAG_UPD.replace("ACCESS_TOKEN", token);
			String result = HttpRequestUtil.createDefault().doPost(url,
					postJson);

			if (StringUtil.isNotEmpty(result)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (WechatUtil.isError(jsonObject)) {
					log.error("修改标签失败 : " + jsonObject.toString());
				} else {
					bo = true;
				}
			}
		}
		return bo;
	}

	@Override
	public boolean deleteTag(int tagId) {
		String token = accessTokenService.getAccessTokenStr();
		// 组件post json字符串
		String postJson = "{\"tag\" : {\"id\" : \"" + tagId + "\"}}";
		boolean bo = false;
		if (token != null) {
			String url = USER_TAG_DEL.replace("ACCESS_TOKEN", token);
			String result = HttpRequestUtil.createDefault().doPost(url,
					postJson);

			if (StringUtil.isNotEmpty(result)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (WechatUtil.isError(jsonObject)) {
					log.error("删除标签失败 : " + jsonObject.toString());
				} else {
					bo = true;
				}
			}
		}
		return bo;
	}

	@Override
	public UserOpenIdList getTagUserOpenIdList(int tagId, String nextOpenId) {
		UserOpenIdList userOpenIdList = new UserOpenIdList();
		String token = accessTokenService.getAccessTokenStr();
		if (token != null) {
			nextOpenId = StringUtil.isEmpty(nextOpenId) ? "" : nextOpenId;
			String url = USER_TAG_USER.replace("ACCESS_TOKEN", token);
			// 组件post json字符串
			String postJson = "{\"tagid\" : " + tagId + ",\"next_openid\" : \""
					+ nextOpenId + "\"}";

			String result = HttpRequestUtil.createDefault().doPost(url,
					postJson);
			System.out.println(result);
			if (StringUtil.isNotEmpty(result)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (WechatUtil.isError(jsonObject)) {
					log.error("获取标签用户列表失败: " + jsonObject.toString());
				} else {
					int count = jsonObject.getInteger("count");
					userOpenIdList.setCount(count);
					if (count != 0) {
						userOpenIdList.setNextOpenid(jsonObject
								.getString("next_openid"));
						JSONArray openIdArr = jsonObject.getJSONObject("data")
								.getJSONArray("openid");
						String[] ids = openIdArr.toArray(new String[openIdArr
								.size()]);
						userOpenIdList.setOpenIds(ids);
					}
				}
			}

		}
		return userOpenIdList;
	}

	@Override
	public boolean batchAddtagUser(int tagId, String[] openIds) {
		String token = accessTokenService.getAccessTokenStr();
		// 组件post json字符串
		StringBuffer postJson = new StringBuffer("{\"openid_list\":[");
		for (int i = 0; i < openIds.length; i++) {
			postJson.append("\"").append(openIds[i]).append("\",");
		}
		postJson.delete(postJson.length() - 1, postJson.length());
		postJson.append("],\"tagid\":" + tagId + "}");

		boolean bo = false;
		if (token != null) {
			String url = USER_TAG_USER_BATCH_ADD.replace("ACCESS_TOKEN", token);
			String result = HttpRequestUtil.createDefault().doPost(url,
					postJson.toString());
			if (StringUtil.isNotEmpty(result)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (WechatUtil.isError(jsonObject)) {
					log.error("批量为用户打标签失败 : " + jsonObject.toString());
				} else {
					bo = true;
				}
			}
		}
		return bo;
	}

	@Override
	public boolean batchRemovetagUser(int tagId, String[] openIds) {
		String token = accessTokenService.getAccessTokenStr();
		// 组件post json字符串
		StringBuffer postJson = new StringBuffer("{\"openid_list\":[");
		for (int i = 0; i < openIds.length; i++) {
			postJson.append("\"").append(openIds[i]).append("\",");
		}
		postJson.delete(postJson.length() - 1, postJson.length());
		postJson.append("],\"tagid\":" + tagId + "}");

		boolean bo = false;
		if (token != null) {
			String url = USER_TAG_USER_BATCH_DEL.replace("ACCESS_TOKEN", token);
			String result = HttpRequestUtil.createDefault().doPost(url,
					postJson.toString());

			if (StringUtil.isNotEmpty(result)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (WechatUtil.isError(jsonObject)) {
					log.error("批量为用户取消标签失败 : " + jsonObject.toString());
				} else {
					bo = true;
				}
			}
		}
		return bo;
	}

	@Override
	public Integer[] getUserTag(String openId) {
		String token = accessTokenService.getAccessTokenStr();
		// 组件post json字符串
		String postJson = "{\"openid\":\"" + openId + "\"}";
		if (token != null) {
			String url = USER_TAG_LIST_BY_USER.replace("ACCESS_TOKEN", token);
			String result = HttpRequestUtil.createDefault().doPost(url,
					postJson);

			if (StringUtil.isNotEmpty(result)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (WechatUtil.isError(jsonObject)) {
					log.error("获取用户标签失败 : " + jsonObject.toString());
				} else {
					JSONArray ja = jsonObject.getJSONArray("tagid_list");
					if (ja != null && ja.size() >= 1) {
						Integer[] tags = new Integer[ja.size()];
						for (int i = 0; i < ja.size(); i++) {
							tags[i] = ja.getIntValue(i);
						}
						return tags;
					}
				}
			}
		}
		return null;
	}

}
