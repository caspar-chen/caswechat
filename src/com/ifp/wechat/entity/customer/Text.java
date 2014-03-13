package com.ifp.wechat.entity.customer;

/**
 * 文本消息对象
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class Text {
	/**
	 * 回复的消息内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Text(String content) {
		super();
		this.content = content;
	}

	public Text() {
		super();
	}
	
}
