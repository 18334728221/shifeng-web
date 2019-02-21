package com.xwd.log.service;

import com.frame.base.BaseService;
import com.xwd.log.model.LogCustomer;


public interface LogCustomerService extends BaseService<LogCustomer>{

	public int save(LogCustomer entity);

	public int update(LogCustomer entity);

	public int saveOrUpdate(LogCustomer entity);

	public void delete(LogCustomer entity);
}
