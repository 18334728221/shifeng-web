package com.xwd.log.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.log.model.*;
import com.xwd.log.dao.*;
import com.xwd.log.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.LOG, constraint=false)
public class LogCustomerServiceImpl extends AbstractBaseService<LogCustomer> implements LogCustomerService{

	@Autowired
	private LogCustomerDao logCustomerDao;
	
	public EntityDao<LogCustomer,Long> getEntityDao() {
		return this.logCustomerDao;
	}
	
	@Override
	public int save(LogCustomer entity) {
		return logCustomerDao.save(entity);
	}

	@Override
	public int update(LogCustomer entity) {
		return logCustomerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(LogCustomer entity) {
		return logCustomerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(LogCustomer entity) {
		logCustomerDao.delete(entity);
	}
	
}
