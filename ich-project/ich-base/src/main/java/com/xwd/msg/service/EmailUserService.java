package com.xwd.msg.service;

import com.frame.base.BaseService;
import com.xwd.msg.model.EmailUser;


public interface EmailUserService extends BaseService<EmailUser>{

	public int save(EmailUser entity);

	public int update(EmailUser entity);

	public int saveOrUpdate(EmailUser entity);

	public void delete(EmailUser entity);
}
