package com.caspar.caswechat.user.service;

import java.util.List;

import com.caspar.caswechat.user.entity.UserOpenIdList;
import com.caspar.caswechat.user.entity.UserTag;
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
	UserWechat getUserInfo(String openid);

	/**
	 * 获取关注者OpenID列表
	 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return UserOpenIdList 关注者openID列表
	 */
	UserOpenIdList getUserOpenIdList(String nextOpenId);

	/**
	 * 获取关注者列表
	 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return List<UserWechat> 关注者列表信息
	 */
	List<UserWechat> getUserList(String nextOpenId);
	
	/**
	 * 创建标签 （一个公众号，最多可以创建100个标签）
	 * @param tag	标签名（30个字以内）
	 */
	UserTag createTag(String tag);
	
	/**
	 * 获取已创建的标签
	 */
	List<UserTag> getTags();
	
	/**
	 * 修改标签
	 * @param userTag 
	 */
	boolean updateTag(UserTag userTag);
	
	/**
	 * 删除标签
	 * @param tagId tag标签的id
	 */
	boolean deleteTag(int tagId);
	
	/**
	 * 获取标签下粉丝列表
	 * @param tagId	标签Id
	 * @param nextOpenId	第一个拉取的OPENID，不填默认从头开始拉取
	 */
	UserOpenIdList getTagUserOpenIdList(int tagId,String nextOpenId);
	
	/**
	 * 批量为用户打标签
	 * @param tagId	标签Id
	 * @param openIds 粉丝的openId列表
	 */
	boolean batchAddtagUser(int tagId,String[] openIds);
	
	/**
	 * 批量为用户取消标签
	 * @param tagId	标签Id
	 * @param openIds 粉丝的openId列表
	 */
	boolean batchRemovetagUser(int tagId,String[] openIds);
	
	/**
	 * 获取用户身上的标签Id列表
	 * @param openId	粉丝的openId
	 */
	Integer[] getUserTag(String openId);
}
