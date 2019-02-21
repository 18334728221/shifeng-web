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
public class LogBuyerCommissionServiceImpl extends AbstractBaseService<LogBuyerCommission> implements LogBuyerCommissionService{

	@Autowired
	private LogBuyerCommissionDao logBuyerCommissionDao;
	
	public EntityDao<LogBuyerCommission,Long> getEntityDao() {
		return this.logBuyerCommissionDao;
	}
	
	@Override
	public int save(LogBuyerCommission entity) {
		return logBuyerCommissionDao.save(entity);
	}

	@Override
	public int update(LogBuyerCommission entity) {
		return logBuyerCommissionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(LogBuyerCommission entity) {
		return logBuyerCommissionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(LogBuyerCommission entity) {
		logBuyerCommissionDao.delete(entity);
	}
	
}
