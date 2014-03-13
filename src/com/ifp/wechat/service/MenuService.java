package com.ifp.wechat.service;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ifp.wechat.entity.menu.Menu;
import com.ifp.wechat.util.WeixinUtil;

/**
 * 菜单创建
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class MenuService {

	public static Logger log = Logger.getLogger(MenuService.class);
	
	/**
	 * 菜单创建（POST） 限100（次/天）
	 */
	public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * @param jsonMenu json格式
	 * @return 状态 0 表示成功、其他表示失败
	 */
	public static Integer createMenu(String jsonMenu) {
		String token = WeixinUtil.getToken();
		if (token != null) {
			return createMenu(jsonMenu, token);
		}
		return null;
	}

	/**
	 * 创建菜单
	 * @param menu 菜单实例
	 * @return 0表示成功，其他值表示失败
	 */
	public static Integer createMenu(Menu menu) {
		String token = WeixinUtil.getToken();
		if (token != null) {
			return createMenu(menu, token);
		}
		return null;
	}

	/**
	 * 创建菜单
	 * @param jsonMenu 菜单的json格式
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(String jsonMenu, String accessToken) {

		int result = 0;
		// 拼装创建菜单的url
		String url = MENU_CREATE.replace("ACCESS_TOKEN", accessToken);
		// 调用接口创建菜单
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:" + jsonObject.getInt("errcode")
						+ "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 创建菜单
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		return createMenu(jsonMenu, accessToken);
	}

}
