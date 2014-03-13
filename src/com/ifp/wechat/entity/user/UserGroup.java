package com.ifp.wechat.entity.user;

/**
 * 用户分组
 * @author caspar.chen
 * @version 1.0
 */
public class UserGroup {

	/**
	 * 分组id，由微信分配
	 */
	private String id;
	/**
	 * 分组名字，UTF8编码
	 */
	private String name;
	/**
	 * 分组内用户数量
	 */
	private int count;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public UserGroup(String id, String name, int count) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
	}
	public UserGroup() {
		super();
	}
	
}
