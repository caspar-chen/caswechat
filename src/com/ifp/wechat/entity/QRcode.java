package com.ifp.wechat.entity;

/**
 * 二维码
 * @author caspar.chen
 * @version 1.0
 */
public class QRcode {
	/**
	 * 该二维码有效时间，以秒为单位。 最大不超过1800
	 */
	private String expireSeconds;
	
	/**
	 * 二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
	 */
	private String actionName;
	
	/**
	 * 二维码详细信息
	 */
	private String actionInfo;
	
	/**
	 * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 */
	private String sceneId;
	
	/**
	 * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	 */
	private String ticket;

	public String getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(String expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public QRcode(String expireSeconds, String actionName, String actionInfo,
			String sceneId, String ticket) {
		super();
		this.expireSeconds = expireSeconds;
		this.actionName = actionName;
		this.actionInfo = actionInfo;
		this.sceneId = sceneId;
		this.ticket = ticket;
	}

	public QRcode() {
		super();
	}

}
