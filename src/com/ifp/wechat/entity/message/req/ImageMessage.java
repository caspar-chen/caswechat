package com.ifp.wechat.entity.message.req;
/**
 * 图片消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class ImageMessage extends MediaMessage{

	/**
	 * 图片链接
	 */
	private String picUrl;
	
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public ImageMessage(String picUrl) {
		super();
		this.picUrl = picUrl;
	}
	
}
