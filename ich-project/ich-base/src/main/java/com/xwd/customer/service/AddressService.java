package com.xwd.customer.service;

import com.frame.base.BaseService;
import com.xwd.customer.model.Address;


public interface AddressService extends BaseService<Address>{

	public int save(Address entity);

	public int update(Address entity);

	public int saveOrUpdate(Address entity);

	public void delete(Address entity);
}
