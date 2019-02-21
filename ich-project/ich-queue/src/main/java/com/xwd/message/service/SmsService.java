package com.xwd.message.service;

import java.util.Map;

import com.auth.User;
import com.xwd.msg.model.SmsCustomer;
import com.xwd.msg.model.SmsUser;

public interface SmsService {
	
	public void send(SmsUser entity);
	
	public void send(SmsCustomer entity);
	
	public void send(User user,String mobile, String smsTemplateId, Map<String, String> map);
	
}
