package com.ifp.wechat.entity.message.req;

/**
 * 视频消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class VideoMessage extends BaseMessage{
	
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	 */
	private String thumbMediaId;

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public VideoMessage(String thumbMediaId) {
		super();
		this.thumbMediaId = thumbMediaId;
	}
	
	
}
