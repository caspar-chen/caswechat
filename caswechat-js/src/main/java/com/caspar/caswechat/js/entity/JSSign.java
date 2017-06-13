package com.caspar.caswechat.js.entity;


/**
 * @author caspar.chen
 * @date 2017-6-2
 */
public class JSSign {
	
	private String appId;
	private String jsapiTicket;
	private String nonceStr;
	private Long timestamp;
	private String signature;
	private String url;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getJsapiTicket() {
		return jsapiTicket;
	}
	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "JSSign [appId=" + appId + ", jsapiTicket=" + jsapiTicket
				+ ", nonceStr=" + nonceStr + ", timestamp=" + timestamp
				+ ", signature=" + signature + ", url=" + url + "]";
	}
}
