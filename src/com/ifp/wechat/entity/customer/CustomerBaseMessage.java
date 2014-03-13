package com.ifp.wechat.entity.customer;

/**
 * 发送客服消息基类
 * @author caspar.chen
 * @version 1.0
 */
public class CustomerBaseMessage {
	
	/**
	 * 普通用户openid
	 */
	private String touser;
	
	/**
	 * 消息类型
	 */
	private String msgtype;


	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
}
