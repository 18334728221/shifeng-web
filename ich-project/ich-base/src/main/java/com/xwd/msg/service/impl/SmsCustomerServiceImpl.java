package com.xwd.msg.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.msg.model.*;
import com.xwd.msg.dao.*;
import com.xwd.msg.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class SmsCustomerServiceImpl extends AbstractBaseService<SmsCustomer> implements SmsCustomerService{

	@Autowired
	private SmsCustomerDao smsCustomerDao;
	
	public EntityDao<SmsCustomer,Long> getEntityDao() {
		return this.smsCustomerDao;
	}
	
	@Override
	public int save(SmsCustomer entity) {
		return smsCustomerDao.save(entity);
	}

	@Override
	public int update(SmsCustomer entity) {
		return smsCustomerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(SmsCustomer entity) {
		return smsCustomerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(SmsCustomer entity) {
		smsCustomerDao.delete(entity);
	}
	
}
