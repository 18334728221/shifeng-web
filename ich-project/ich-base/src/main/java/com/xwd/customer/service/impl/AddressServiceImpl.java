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
public class AddressServiceImpl extends AbstractBaseService<Address> implements AddressService{

	@Autowired
	private AddressDao addressDao;
	
	public EntityDao<Address,Long> getEntityDao() {
		return this.addressDao;
	}
	
	@Override
	public int save(Address entity) {
		return addressDao.save(entity);
	}

	@Override
	public int update(Address entity) {
		return addressDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Address entity) {
		return addressDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Address entity) {
		addressDao.delete(entity);
	}
	
}
