package com.caspar.caswechat.menu.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.caspar.caswechat.menu.entity.WechatMenu;
import com.caspar.caswechat.menu.service.WechatMenuService;
import com.caspar.caswechat.start.service.AccessTokenService;
import com.caspar.caswechat.util.general.HttpRequestUtil;
import com.caspar.caswechat.util.general.PropertyUtil;
import com.caspar.caswechat.util.general.StringUtil;

/**
 * @author caspar.chen
 * @date 2017-6-14
 */
@Service
public class WechatMenuServiceImpl implements WechatMenuService {

	private static final Logger log = LoggerFactory
			.getLogger(WechatMenuServiceImpl.class);

	@Autowired
	AccessTokenService accessTokenService;

	// 菜单创建（POST） 限100（次/天）
	final static String URL_MENU_ADD = PropertyUtil.get("url_menu_add");

	// 菜单查询
	final static String URL_MENU_GET = PropertyUtil.get("url_menu_get");

	// 菜单删除
	final static String URL_MENU_DEL = PropertyUtil.get("url_menu_del");

	@Override
	public boolean createMenu(String text) {
		String token = accessTokenService.getAccessTokenStr();
		boolean result = false;
		if (token != null) {
			String url = URL_MENU_ADD.replace("ACCESS_TOKEN", token);
			String responseStr = HttpRequestUtil.createDefault().doPost(url, text);
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			if (null != jsonObject) {
				if (jsonObject.getInteger("errcode") == 0) {
					result = true;
				}else{
					log.error("创建菜单失败："+jsonObject.toString());
				}
			}
		}
		return result;
		
	}

	@Override
	public boolean createMenu(WechatMenu menu) {
		return createMenu(JSONObject.toJSONString(menu));
	}

	@Override
	public WechatMenu getMenu() {
		String token = accessTokenService.getAccessTokenStr();
		WechatMenu wm = null;
		if (token != null) {
			String url = URL_MENU_GET.replace("ACCESS_TOKEN", token);
			JSONObject jsonObject = HttpRequestUtil.createDefault().doGetToJsonObject(url);
			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.getString("errcode"))
						&& !"0".equals(jsonObject.get("errcode"))) {
					log.error("获取菜单信息失败 : " + jsonObject.toString());
				} else {
					wm = JSONObject.parseObject(jsonObject.getString("menu"),
							WechatMenu.class);
				}
			}
		}
		return wm;
	}

	@Override
	public boolean deleteMenu() {
		String token = accessTokenService.getAccessTokenStr();
		boolean result = false;
		if (token != null) {
			String url = URL_MENU_DEL.replace("ACCESS_TOKEN", token);
			JSONObject jsonObject = HttpRequestUtil.createDefault().doGetToJsonObject(url);
			if (null != jsonObject) {
				if (jsonObject.getInteger("errcode") == 0) {
					result = true;
				}else{
					log.error("删除菜单失败："+jsonObject.toString());
				}
			}
		}
		return result;
	}

}
