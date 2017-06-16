package com.caspar.caswechat.start.service;

import java.util.List;

/**
 * 获取微信服务器IP地址
 * @author caspar.chen
 * @date 2017-6-14
 */
public interface WechatServerService {

	List<String> getServerIpList();
}
