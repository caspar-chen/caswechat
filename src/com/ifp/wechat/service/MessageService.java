package com.ifp.wechat.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ifp.wechat.constant.ConstantWeChat;
import com.ifp.wechat.entity.message.resp.BaseMessage;
import com.ifp.wechat.entity.message.resp.MediaMessage;
import com.ifp.wechat.entity.message.resp.MusicMessage;
import com.ifp.wechat.entity.message.resp.NewsMessage;
import com.ifp.wechat.entity.message.resp.TextMessage;
import com.ifp.wechat.entity.message.resp.VideoMessage;
import com.ifp.wechat.util.MessageUtil;

/**
 * 消息处理
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class MessageService {

	public static Map<String, BaseMessage> bulidMessageMap = new HashMap<String, BaseMessage>();
	
	static {
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_TEXT, new TextMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS, new NewsMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_IMAGE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VOICE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VIDEO, new VideoMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_MUSIC, new MusicMessage());
	}
	
	/**
	 * 构建基本消息
	 * 
	 * @param requestMap
	 *            xml请求解析后的map
	 * @param msgType
	 *            消息类型
	 * @return BaseMessage 基本消息对象
	 */
	public static Object bulidBaseMessage(Map<String, String> requestMap,
			String msgType) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");

		BaseMessage message = bulidMessageMap.get(msgType);
		message.setToUserName(fromUserName);
		message.setFromUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(msgType);
		message.setFuncFlag(0);
		return message;
	}

	/**
	 * 发送消息接口
	 * 
	 * @param obj
	 *            消息对象
	 * @param msgType
	 *            消息类型
	 * @return 返回xml格式数据给微信
	 */
	public static String bulidSendMessage(Object obj, String msgType) {
		String responseMessage = "";

		// 图文消息处理
		if (msgType == ConstantWeChat.RESP_MESSAGE_TYPE_NEWS) {
			responseMessage = MessageUtil.newsMessageToXml((NewsMessage) obj);
		} else {// 其他消息处理
			responseMessage = MessageUtil.messageToXml(obj);
		}
		return responseMessage;
	}

}
