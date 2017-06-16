package com.caspar.caswechat.demo.test;

import com.caspar.caswechat.demo.util.SpringContextUtil;
import com.caspar.caswechat.user.service.UserWechatService;

/**
 * @author caspar.chen
 * @date 2017-6-14
 */
public class TestUser {

	private static final UserWechatService us = SpringContextUtil.getBean(UserWechatService.class);
	
	public static void main(String[] args) {
		//用户openid列表
		System.out.println(us.getUserOpenIdList());
				
		//用户列表
		System.out.println(us.getUserList());
		
		//用户详细信息，根据openid
		System.out.println(us.getUserInfo("o-ZkQt2XvzCGJS5xiAJ2sf-MW9wY").toString());
	}
	
}
