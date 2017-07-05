package com.caspar.caswechat.demo.test;

import com.alibaba.fastjson.JSONArray;
import com.caspar.caswechat.demo.util.SpringContextUtil;
import com.caspar.caswechat.user.entity.UserTag;
import com.caspar.caswechat.user.service.UserWechatService;

/**
 * @author caspar.chen
 * @date 2017-6-14
 */
public class TestUser {

	private static final UserWechatService us = SpringContextUtil.getBean(UserWechatService.class);
	
	public static void main(String[] args) {
//		//用户openid列表
//		System.out.println(us.getUserOpenIdList());
//				
//		//用户列表
//		System.out.println(us.getUserList());
//		
//		//用户详细信息，根据openid
//		System.out.println(us.getUserInfo("o-ZkQt2XvzCGJS5xiAJ2sf-MW9wY").toString());
		
		//创建标签
		System.out.println(us.createTag("妹子").toString());
		
		//修改标签
		System.out.println(us.updateTag(new UserTag(103,"修改后的值12")));
		
		//删除标签
		System.out.println(us.deleteTag(101));
		
		//获取标签
		System.out.println(us.getTags().toString());
		
		//批量为用户打标签
		us.batchAddtagUser(109, new String[]{"o-ZkQtznTy0hUqlkeRYuQ9KEKQvU","o-ZkQt3UbtTsirYyLrFzr3bmLXMo"});
		
		//批量为用户取消标签
		us.batchRemovetagUser(109, new String[]{"o-ZkQtznTy0hUqlkeRYuQ9KEKQvU"});
		
		//获取标签下的用户
		System.out.println(us.getTagUserOpenIdList(109, null).toString());
		
		//获取用户身上的标签列表
		System.out.println(JSONArray.toJSONString(us.getUserTag("o-ZkQt3UbtTsirYyLrFzr3bmLXMo")));
	}
	
}
