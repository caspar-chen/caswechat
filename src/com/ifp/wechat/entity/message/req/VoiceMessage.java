package com.ifp.wechat.entity.message.req;

/**
 * 语音消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class VoiceMessage extends BaseMessage {
	/**
	 * 语音格式，如amr，speex等
	 */
	private String Format;

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public VoiceMessage(String format) {
		super();
		Format = format;
	}

}
