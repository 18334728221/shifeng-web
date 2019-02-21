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
public class LogSellerCommissionServiceImpl extends AbstractBaseService<LogSellerCommission> implements LogSellerCommissionService{

	@Autowired
	private LogSellerCommissionDao logSellerCommissionDao;
	
	public EntityDao<LogSellerCommission,Long> getEntityDao() {
		return this.logSellerCommissionDao;
	}
	
	@Override
	public int save(LogSellerCommission entity) {
		return logSellerCommissionDao.save(entity);
	}

	@Override
	public int update(LogSellerCommission entity) {
		return logSellerCommissionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(LogSellerCommission entity) {
		return logSellerCommissionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(LogSellerCommission entity) {
		logSellerCommissionDao.delete(entity);
	}
	
}
