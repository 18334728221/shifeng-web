package com.xwd.log.service;

import com.frame.base.BaseService;
import com.xwd.log.model.LogAccount;


public interface LogAccountService extends BaseService<LogAccount>{

	public int save(LogAccount entity);

	public int update(LogAccount entity);

	public int saveOrUpdate(LogAccount entity);

	public void delete(LogAccount entity);
}
