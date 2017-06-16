package com.caspar.caswechat.menu.entity;

import java.util.Arrays;

/**
 * 菜单<br>
 * 1、自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。<br>
 * 2、一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。<br>
 * 3、创建自定义菜单后，菜单的刷新策略是，在用户进入公众号会话页或公众号profile页时，如果发现上一次拉取菜单的请求在5分钟以前，就会拉取一下菜单，如果菜单有更新，就会刷新客户端的菜单。测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。
 * @author caspar
 * @since 2017-6-14
 * @version   v1.0.0
 */
public class WechatMenu {
	/**
	 * 菜单按钮
	 */
	private WechatButton[] button;

	public WechatButton[] getButton() {
		return button;
	}


	public void setButton(WechatButton[] button) {
		this.button = button;
	}


	public WechatMenu(WechatButton[] button) {
		super();
		this.button = button;
	}


	public WechatMenu() {
		super();
	}


	@Override
	public String toString() {
		return "WechatMenu [button=" + Arrays.toString(button) + "]";
	}  
}
