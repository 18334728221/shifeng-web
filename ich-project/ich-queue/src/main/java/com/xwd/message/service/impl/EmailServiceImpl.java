package com.xwd.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.constant.MqThemeConstant;
import com.xwd.message.service.EmailService;
import com.xwd.msg.model.EmailCustomer;
import com.xwd.msg.model.EmailUser;
import com.xwd.queue.service.QueueService;



@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private QueueService queueService;

	@Override
	public void send(EmailUser entity) {
		if(entity != null){
			queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_EMAIL_USER), entity);
		}
	}

	@Override
	public void send(EmailCustomer entity) {
		if(entity != null){
			queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_EMAIL_CUSTOMER), entity);
		}
	}

}
