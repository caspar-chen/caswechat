package com.ifp.wechat.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ifp.wechat.constant.ConstantWeChat;
import com.ifp.wechat.entity.customer.CustomerBaseMessage;
import com.ifp.wechat.entity.customer.MediaMessage;
import com.ifp.wechat.entity.customer.MusicMessage;
import com.ifp.wechat.entity.customer.NewsMessage;
import com.ifp.wechat.entity.customer.TextMessage;
import com.ifp.wechat.entity.customer.VideoMessage;
import com.ifp.wechat.util.StringUtil;
import com.ifp.wechat.util.WeixinUtil;

/**
 * 发送客服消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class CustomService {

	public static Logger log = Logger.getLogger(CustomService.class);

	private static String CUSTOME_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	public static Map<String, CustomerBaseMessage> bulidMessageMap = new HashMap<String, CustomerBaseMessage>();
	
	static {
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_TEXT, new TextMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS, new NewsMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_IMAGE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VOICE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VIDEO, new VideoMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_MUSIC, new MusicMessage());
	}
	
	/**
	 * 发送客服消息
	 * @param obj	消息对象
	 * @return 是否发送成功
	 */
	public static boolean sendCustomerMessage(Object obj) {
		boolean bo = false;
		String url = CUSTOME_URL.replace("ACCESS_TOKEN", WeixinUtil.getToken());
		JSONObject json = JSONObject.fromObject(obj);
		System.out.println(json);
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", json.toString());
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.getString("errcode"))
					&& jsonObject.getString("errcode").equals("0")) {
				bo = true;
			}
		}
		return bo;
	}

	
	/**
	 * 构建基本消息
	 * 
	 * @param toUser
	 *            接受者用户openId
	 * @param msgType
	 *            消息类型
	 * @return BaseMessage 基本消息对象
	 */
	public static Object bulidCustomerBaseMessage(String toUser,
			String msgType) {
		CustomerBaseMessage message = bulidMessageMap.get(msgType);
		message.setTouser(toUser);
		message.setMsgtype(msgType);
		return message;
	}

}
