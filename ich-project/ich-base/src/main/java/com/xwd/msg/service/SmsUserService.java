package com.xwd.msg.service;

import com.frame.base.BaseService;
import com.xwd.msg.model.SmsUser;


public interface SmsUserService extends BaseService<SmsUser>{

	public int save(SmsUser entity);

	public int update(SmsUser entity);

	public int saveOrUpdate(SmsUser entity);

	public void delete(SmsUser entity);
}
