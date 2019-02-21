package com.xwd.msg.service;

import com.frame.base.BaseService;
import com.xwd.msg.model.EmailCustomer;


public interface EmailCustomerService extends BaseService<EmailCustomer>{

	public int save(EmailCustomer entity);

	public int update(EmailCustomer entity);

	public int saveOrUpdate(EmailCustomer entity);

	public void delete(EmailCustomer entity);
}
