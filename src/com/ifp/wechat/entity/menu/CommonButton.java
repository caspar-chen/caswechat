package com.ifp.wechat.entity.menu;

/**
 * 一般按钮
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class CommonButton extends Button {
	
	/**
	 * 按钮类型
	 */
	private String type;
	
	/**
	 * 按钮key值
	 */
	private String key;

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CommonButton(String type, String key) {
		super();
		this.type = type;
		this.key = key;
	}

	public CommonButton() {
		super();
	}

}
