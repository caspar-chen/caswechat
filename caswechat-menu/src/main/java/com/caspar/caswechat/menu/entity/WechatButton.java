package com.caspar.caswechat.menu.entity;

import java.util.Arrays;

/**
 * 按钮<br>
 * 1、click：点击推事件用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event的结构给开发者（参考消息接口指南），
 * 并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；<br>
 * 2、view：跳转URL用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL
 * ，可与网页授权获取用户基本信息接口结合，获得用户基本信息。<br>
 * 3、scancode_push：扫码推事件用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果
 * （如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。<br>
 * 4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框用户点击按钮后
 * ，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。<br>
 * 5、pic_sysphoto：弹出系统拍照发图用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，
 * 同时收起系统相机，随后可能会收到开发者下发的消息。<br>
 * 6、pic_photo_or_album：弹出拍照或者相册发图用户点击按钮后，微信客户端将弹出选择器供用户选择
 * “拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。<br>
 * 7、pic_weixin：弹出微信相册发图器用户点击按钮后，微信客户端将调起微信相册，完成选择操作后
 * ，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。<br>
 * 8、location_select：弹出地理位置选择器用户点击按钮后
 * ，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。<br>
 * 9、media_id
 * ：下发消息（除文本消息）用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片
 * 、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。<br>
 * 10、view_limited：跳转图文消息URL用户点击view_limited类型按钮后
 * ，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL
 * ，永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 * 
 * @author caspar
 * @since 2017-6-14
 * @version v1.0.0
 */
public class WechatButton {

	/**
	 * 按钮名称
	 */
	private String name;

	/**
	 * 按钮类型
	 */
	private String type;

	/**
	 * 按钮key值
	 */
	private String key;

	/**
	 * 按钮url,view、miniprogram类型必须
	 */
	private String url;

	/**
	 * media_id类型和view_limited类型必须,<br>
	 * 调用新增永久素材接口返回的合法media_id
	 */
	private String media_id;

	/**
	 * miniprogram类型必须 小程序的appid（仅认证公众号可配置）
	 */
	private String appid;

	/**
	 * miniprogram类型必须 小程序的页面路径
	 */
	private String pagepath;

	/**
	 * 子按钮列表
	 */
	private WechatButton[] sub_button;

	public WechatButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(WechatButton[] sub_button) {
		this.sub_button = sub_button;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}

	public WechatButton() {
		super();
	}

	@Override
	public String toString() {
		return "WechatButton [name=" + name + ", type=" + type + ", key=" + key
				+ ", url=" + url + ", media_id=" + media_id + ", appid="
				+ appid + ", pagepath=" + pagepath + ", sub_button="
				+ Arrays.toString(sub_button) + "]";
	}

}
