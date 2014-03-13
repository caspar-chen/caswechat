package com.ifp.wechat.entity.menu;

/**
 * view 类型的按钮
 * @author caspar.chen
 * 
 */
public class ViewButton extends Button {

	/**
	 * 按钮类型
	 */
	private String type;
	
	/**
	 * 按钮url
	 */
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ViewButton(String type, String url) {
		super();
		this.type = type;
		this.url = url;
	}

	public ViewButton() {
		super();
	}
}
