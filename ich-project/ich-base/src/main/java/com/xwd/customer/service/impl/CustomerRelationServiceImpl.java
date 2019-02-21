package com.xwd.customer.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.customer.model.*;
import com.xwd.customer.dao.*;
import com.xwd.customer.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CustomerRelationServiceImpl extends AbstractBaseService<CustomerRelation> implements CustomerRelationService{

	@Autowired
	private CustomerRelationDao customerRelationDao;
	
	public EntityDao<CustomerRelation,Long> getEntityDao() {
		return this.customerRelationDao;
	}
	
	@Override
	public int save(CustomerRelation entity) {
		return customerRelationDao.save(entity);
	}

	@Override
	public int update(CustomerRelation entity) {
		return customerRelationDao.update(entity);
	}

	@Override
	public int saveOrUpdate(CustomerRelation entity) {
		return customerRelationDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(CustomerRelation entity) {
		customerRelationDao.delete(entity);
	}
	
}
