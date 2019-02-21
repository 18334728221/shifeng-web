package com.xwd.log.service;

import com.frame.base.BaseService;
import com.xwd.log.model.LogUser;


public interface LogUserService extends BaseService<LogUser>{

	public int save(LogUser entity);

	public int update(LogUser entity);

	public int saveOrUpdate(LogUser entity);

	public void delete(LogUser entity);
}
