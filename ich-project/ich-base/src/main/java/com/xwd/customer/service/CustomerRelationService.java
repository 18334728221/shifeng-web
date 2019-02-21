package com.xwd.customer.service;

import com.frame.base.BaseService;
import com.xwd.customer.model.CustomerRelation;


public interface CustomerRelationService extends BaseService<CustomerRelation>{

	public int save(CustomerRelation entity);

	public int update(CustomerRelation entity);

	public int saveOrUpdate(CustomerRelation entity);

	public void delete(CustomerRelation entity);
}
