package com.caspar.caswechat.start.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caspar.caswechat.start.service.AccessTokenService;
import com.caspar.caswechat.start.service.WechatServerService;
import com.caspar.caswechat.util.general.HttpRequestUtil;
import com.caspar.caswechat.util.general.PropertyUtil;
import com.caspar.hoe.StringHoe;

/**
 * @author caspar.chen
 * @date 2017-6-14
 */
@Service
public class WechatServerServiceImpl implements WechatServerService{

	@Autowired
	private AccessTokenService accessTokenService;
	
	private static final Logger log = LoggerFactory
			.getLogger(WechatServerServiceImpl.class);
	
	private final static String URL_SERVER_IP_LIST = PropertyUtil
			.get("url_server_ip_list");
	
	@Override
	public List<String> getServerIpList() {
		String token = accessTokenService.getAccessTokenStr();
		List<String> ipList = new ArrayList<String>();
		if (token != null) {
			String url = URL_SERVER_IP_LIST.replace("ACCESS_TOKEN", token);
			JSONObject jsonObject = HttpRequestUtil.createDefault().doGetToJsonObject(url);
			if (null != jsonObject) {
				if (StringHoe.isNotEmpty(jsonObject.getString("errcode"))
						&& !"0".equals(jsonObject.get("errcode"))) {
					log.error("获取服务器IP列表失败 : " + jsonObject.toString());
				} else {
					ipList = JSONArray
							.parseArray(jsonObject.getString("ip_list"), String.class);
				}
			}

		}
		return ipList;
	}

}
