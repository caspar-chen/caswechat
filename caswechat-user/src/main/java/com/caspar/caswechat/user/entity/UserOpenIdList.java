package com.caspar.caswechat.user.entity;

import java.util.Arrays;

/**
 * 用户openId列表
 * @author caspar.chen
 * @date 2017-5-15
 */
public class UserOpenIdList {

	/**
	 * 关注该公众账号的总用户数
	 */
	private Integer total;
	/**
	 * 	拉取的OPENID个数，最大值为10000
	 */
	private Integer count;
	/**
	 * 	列表数据，OPENID的列表
	 */
	private String[] openIds;
	/**
	 * 	拉取列表的最后一个用户的OPENID
	 */
	private String nextOpenid;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String[] getOpenIds() {
		return openIds;
	}

	public void setOpenIds(String[] openIds) {
		this.openIds = openIds;
	}

	public String getNextOpenid() {
		return nextOpenid;
	}

	public void setNextOpenid(String nextOpenid) {
		this.nextOpenid = nextOpenid;
	}

	@Override
	public String toString() {
		return "UserOpenIdList [total=" + total + ", count=" + count
				+ ", openIds=" + Arrays.toString(openIds) + ", nextOpenid="
				+ nextOpenid + "]";
	}

}
