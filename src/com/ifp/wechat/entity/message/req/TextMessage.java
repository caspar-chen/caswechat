package com.ifp.wechat.entity.message.req;


/**
 * 文本消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class TextMessage extends BaseMessage {
	/**
	 * 回复的消息内容
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public TextMessage(String content) {
		super();
		Content = content;
	}
}