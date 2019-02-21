package com.xwd.customer.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.customer.model.CustomerChoice;


public interface CustomerChoiceService extends BaseService<CustomerChoice>{

	public int save(CustomerChoice entity);

	public int update(CustomerChoice entity);

	public int saveOrUpdate(CustomerChoice entity);

	public void delete(CustomerChoice entity);
	
	public List<CustomerChoice> findChoiceBy(Object... paras);
} 
