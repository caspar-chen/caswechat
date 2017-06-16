package com.caspar.caswechat.menu.service;

import com.caspar.caswechat.menu.entity.WechatMenu;

/**
 * TODO 有个性化菜单时没考虑
 * @author caspar.chen
 * @date 2017-6-14
 */
public interface WechatMenuService {

	boolean createMenu(String text);
	
	boolean createMenu(WechatMenu menu);
	
	WechatMenu getMenu();
	
	boolean deleteMenu();
	
}
