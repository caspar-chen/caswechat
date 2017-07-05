package com.caspar.caswechat.user.entity;
/**
 * 用户标签
 * @author caspar.chen
 * @date 2017-7-5
 */
public class UserTag {
	/**
	 * 标签id
	 */
	private Integer id;
	
	/**
	 * 标签名
	 */
	private String name;
	
	/**
	 * 标签下用户数量
	 */
	private Integer count;
	
	public UserTag() {
		super();
	}
	
	public UserTag(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "UserTag [id=" + id + ", name=" + name + ", count=" + count
				+ "]";
	}
}
