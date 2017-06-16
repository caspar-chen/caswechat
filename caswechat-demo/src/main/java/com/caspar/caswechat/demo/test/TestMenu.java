package com.caspar.caswechat.demo.test;

import com.caspar.caswechat.demo.util.SpringContextUtil;
import com.caspar.caswechat.menu.constant.WechatMenuType;
import com.caspar.caswechat.menu.entity.WechatButton;
import com.caspar.caswechat.menu.entity.WechatMenu;
import com.caspar.caswechat.menu.service.WechatMenuService;

/**
 * @author caspar.chen
 * @date 2017-6-14
 */
public class TestMenu {

	private static final WechatMenuService s = SpringContextUtil.getBean(WechatMenuService.class);
	
	public static void main(String[] args) {
		//创建菜单
		createMenu();
		
		//获取菜单
		System.out.println(s.getMenu().toString());
		
		//删除菜单
		//System.out.println(s.deleteMenu());
	}
	
	static void createMenu(){
		
		WechatButton btn = new WechatButton();
		btn.setName("百度");
		btn.setType(WechatMenuType.view.toString());
		btn.setUrl("http://m.baidu.com");
		
		WechatButton btn2 = new WechatButton();
		btn2.setName("按钮牛");
		btn2.setType(WechatMenuType.click.toString());
		btn2.setKey("click_btn2");
		
		
		WechatButton sb31 = new WechatButton();
		sb31.setName("子按钮111");
		sb31.setType(WechatMenuType.click.toString());
		sb31.setKey("click_btn31");
		
		WechatButton btn3 = new WechatButton();
		btn3.setName("有子按钮的");
		btn3.setType(WechatMenuType.click.toString());
		btn3.setKey("click_btn3");
		btn3.setSub_button(new WechatButton[]{sb31});
		
		WechatMenu m = new WechatMenu(new WechatButton[] {btn,btn2,btn3});
		System.out.println(s.createMenu(m));
	}
}
