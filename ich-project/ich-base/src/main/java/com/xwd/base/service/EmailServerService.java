package com.xwd.base.service;

import com.frame.base.BaseService;
import com.xwd.base.model.EmailServer;


public interface EmailServerService extends BaseService<EmailServer>{

	public int save(EmailServer entity);

	public int update(EmailServer entity);

	public int saveOrUpdate(EmailServer entity);

	public void delete(EmailServer entity);
	
	public EmailServer get();
}
