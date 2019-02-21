package com.xwd.customer.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.customer.dao.CustomerCollectionDao;
import com.xwd.customer.model.CustomerCollection;
import com.xwd.customer.service.CustomerCollectionService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CustomerCollectionServiceImpl extends AbstractBaseService<CustomerCollection> implements CustomerCollectionService{

	@Autowired
	private CustomerCollectionDao customerCollectionDao;
	
	public EntityDao<CustomerCollection,Long> getEntityDao() {
		return this.customerCollectionDao;
	}
	
	@Override
	public int save(CustomerCollection entity) {
		return customerCollectionDao.save(entity);
	}

	@Override
	public int update(CustomerCollection entity) {
		return customerCollectionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(CustomerCollection entity) {
		return customerCollectionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(CustomerCollection entity) {
		customerCollectionDao.delete(entity);
	}
	
}
