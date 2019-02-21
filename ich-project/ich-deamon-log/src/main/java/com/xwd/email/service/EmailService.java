package com.xwd.email.service;

import com.xwd.bean.MailMessage;

/**
 * 
 * Description: Spring邮件服务 Copyright (c) Department of Research and
 * Development/Beijing/Digital Heaven. All Rights Reserved.
 * 
 * @version 1.0 Dec 9, 2008 2:30:15 PM by 林金良（linjinliang@d-heaven.com）创建
 */
public interface EmailService {

	
	/**
	 * 
	 * Description: 发送邮件
	 * 
	 * @Version1.0 Dec 9, 2008 3:02:59 PM by 林金良（linjinliang@d-heaven.com）创建
	 * @param msg
	 * @param map
	 */
	public boolean sendEmail(MailMessage msg);

	
}
