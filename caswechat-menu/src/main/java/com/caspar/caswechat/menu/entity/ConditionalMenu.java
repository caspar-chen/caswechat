package com.caspar.caswechat.menu.entity;
/**
 * 个性化菜单
 * @author caspar.chen
 * @date 2017-6-14
 */
public class ConditionalMenu {

	private String menuid; 
	private WechatMenu menu;
	private MenuMatchRule matchrule;
	private WechatMenu conditionalmenu;
	
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public WechatMenu getMenu() {
		return menu;
	}
	public void setMenu(WechatMenu menu) {
		this.menu = menu;
	}
	public MenuMatchRule getMatchrule() {
		return matchrule;
	}
	public void setMatchrule(MenuMatchRule matchrule) {
		this.matchrule = matchrule;
	}
	public WechatMenu getConditionalmenu() {
		return conditionalmenu;
	}
	public void setConditionalmenu(WechatMenu conditionalmenu) {
		this.conditionalmenu = conditionalmenu;
	}
	
}
