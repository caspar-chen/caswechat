package com.ifp.wechat.entity.customer;

/**
 * 视频消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class VideoMessage extends MediaMessage{
	
	/**
	 * 视频消息对象
	 */
	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public VideoMessage(Video video) {
		super();
		this.video = video;
	}

	public VideoMessage() {
		super();
	}

}
