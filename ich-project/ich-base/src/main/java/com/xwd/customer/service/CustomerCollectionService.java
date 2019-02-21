package com.xwd.customer.service;

import com.frame.base.BaseService;
import com.xwd.customer.model.CustomerCollection;


public interface CustomerCollectionService extends BaseService<CustomerCollection>{

	public int save(CustomerCollection entity);

	public int update(CustomerCollection entity);

	public int saveOrUpdate(CustomerCollection entity);

	public void delete(CustomerCollection entity);
}
