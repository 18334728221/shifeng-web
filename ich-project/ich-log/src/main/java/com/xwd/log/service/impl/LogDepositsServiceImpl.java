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
public class LogDepositsServiceImpl extends AbstractBaseService<LogDeposits> implements LogDepositsService{

	@Autowired
	private LogDepositsDao logDepositsDao;
	
	public EntityDao<LogDeposits,Long> getEntityDao() {
		return this.logDepositsDao;
	}
	
	@Override
	public int save(LogDeposits entity) {
		return logDepositsDao.save(entity);
	}

	@Override
	public int update(LogDeposits entity) {
		return logDepositsDao.update(entity);
	}

	@Override
	public int saveOrUpdate(LogDeposits entity) {
		return logDepositsDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(LogDeposits entity) {
		logDepositsDao.delete(entity);
	}
	
}
