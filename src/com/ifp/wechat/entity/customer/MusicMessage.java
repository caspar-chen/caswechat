package com.ifp.wechat.entity.customer;

/**
 * 音乐消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class MusicMessage extends CustomerBaseMessage{
	
	/**
	 * 音乐对象
	 */
	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public MusicMessage(Music music) {
		super();
		this.music = music;
	}

	public MusicMessage() {
		super();
	}
	
}
