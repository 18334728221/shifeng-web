package com.xwd.msg.service;

import com.frame.base.BaseService;
import com.xwd.msg.model.SmsCustomer;


public interface SmsCustomerService extends BaseService<SmsCustomer>{

	public int save(SmsCustomer entity);

	public int update(SmsCustomer entity);

	public int saveOrUpdate(SmsCustomer entity);

	public void delete(SmsCustomer entity);
}
