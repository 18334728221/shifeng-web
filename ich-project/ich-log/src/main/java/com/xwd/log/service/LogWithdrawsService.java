package com.xwd.log.service;

import com.frame.base.BaseService;
import com.xwd.log.model.LogWithdraws;


public interface LogWithdrawsService extends BaseService<LogWithdraws>{

	public int save(LogWithdraws entity);

	public int update(LogWithdraws entity);

	public int saveOrUpdate(LogWithdraws entity);

	public void delete(LogWithdraws entity);
}
