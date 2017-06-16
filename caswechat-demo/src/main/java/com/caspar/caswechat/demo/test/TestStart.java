package com.caspar.caswechat.demo.test;

import com.caspar.caswechat.demo.util.SpringContextUtil;
import com.caspar.caswechat.start.service.WechatServerService;

/**
 * @author caspar.chen
 * @date 2017-6-14
 */
public class TestStart {
	
	private static final WechatServerService s = SpringContextUtil.getBean(WechatServerService.class);
	
	public static void main(String[] args) {
		//服务器ip列表
		System.out.println(s.getServerIpList());
	}
	
}
