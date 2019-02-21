package com.xwd.queue.service;

import com.xwd.base.model.MqTheme;

public interface QueueService {

	public void sendMessage(String topic, String tags, Object object);
	
	public void sendMessage(String topic, String tags, String keys, Object object);
	
	public void sendMessage(MqTheme topic, Object object);
	
	public void init();
}
