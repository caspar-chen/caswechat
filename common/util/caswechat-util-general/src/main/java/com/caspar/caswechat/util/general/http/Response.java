package com.caspar.caswechat.util.general.http;

import org.apache.http.HttpStatus;

/**
 * 
 * 
 * @author caspar
 * @since 2017-5-15
 * @version v1.0.0
 */
public class Response {
	
	private int status;
	private String content;

	public Response(int status, String content) {
		this.status = status;
		this.content = content;
	}

	public boolean isOK() {
		return HttpStatus.SC_OK == this.status;
	}

	public boolean isNotOK() {
		return !isOK();
	}

	public int getStatus() {
		return status;
	}

	public String getContent() {
		return content;
	}
}