package com.xwd.websocket.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.bean.WebsocketMessage;
import com.xwd.websocket.constant.WebsocketConstant;
import com.xwd.websocket.service.WebsocketService;

@Service
public class WebsocketServiceImpl implements WebsocketService{
	
	@Autowired
	private RedisTemplate<String, WebsocketMessage> redisTemplate;
 
	@Override
	public void send(String msgType, String msg) {
		if(StringUtils.isNotBlank(msg)){
			WebsocketMessage bean = new WebsocketMessage();
			bean.setMsg(msg);
			bean.setMsgType(msgType);
			redisTemplate.opsForList().leftPush(WebsocketConstant.WEBSOCKET_QUEUE_KEY, bean);
		}
	}

	@Override
	public void send(String msg) {
		if(StringUtils.isNotBlank(msg)){
			WebsocketMessage bean = new WebsocketMessage();
			bean.setMsg(msg);
			redisTemplate.opsForList().leftPush(WebsocketConstant.WEBSOCKET_QUEUE_KEY, bean);
		}
	}

}
