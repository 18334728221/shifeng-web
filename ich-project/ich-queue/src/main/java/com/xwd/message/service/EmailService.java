package com.xwd.message.service;

import com.xwd.msg.model.EmailCustomer;
import com.xwd.msg.model.EmailUser;

public interface EmailService {

	public void send(EmailUser entity);
	
	public void send(EmailCustomer entity);
	
}
