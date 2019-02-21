package com.xwd.log.service;

import com.frame.base.BaseService;
import com.xwd.log.model.LogDeposits;


public interface LogDepositsService extends BaseService<LogDeposits>{

	public int save(LogDeposits entity);

	public int update(LogDeposits entity);

	public int saveOrUpdate(LogDeposits entity);

	public void delete(LogDeposits entity);
}
