package com.caspar.caswechat.user.service;

import java.util.List;

import com.caspar.caswechat.user.entity.UserOpenIdList;
import com.caspar.caswechat.user.entity.UserWechat;

/**
 * 用户服务
 * @author caspar.chen
 * @date 2017-5-15
 */
public interface UserWechatService {
	
	/**
	 * 获取用户详细信息
	 * @param openid
	 * @return
	 */
	public UserWechat getUserInfo(String openid);

	/**
	 * 获取关注者OpenID列表
	 * 
	 * @return UserOpenIdList 关注者openID列表
	 */
	public UserOpenIdList getUserOpenIdList();

	/**
	 * 获取关注者列表
	 * 
	 * @return List<UserWechat> 关注者列表信息
	 */
	public List<UserWechat> getUserList();
}
