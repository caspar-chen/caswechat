package com.caspar.caswechat.user.entity;

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
	private String[] data;
	/**
	 * 	拉取列表的最后一个用户的OPENID
	 */
	private String next_openid;

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

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

}
