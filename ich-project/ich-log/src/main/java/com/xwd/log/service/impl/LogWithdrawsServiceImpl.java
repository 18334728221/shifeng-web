package com.xwd.log.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.log.dao.LogWithdrawsDao;
import com.xwd.log.model.LogWithdraws;
import com.xwd.log.service.LogWithdrawsService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.LOG, constraint=false)
public class LogWithdrawsServiceImpl extends AbstractBaseService<LogWithdraws> implements LogWithdrawsService{

	@Autowired
	private LogWithdrawsDao logWithdrawsDao;
	
	public EntityDao<LogWithdraws,Long> getEntityDao() {
		return this.logWithdrawsDao;
	}
	
	@Override
	public int save(LogWithdraws entity) {
		return logWithdrawsDao.save(entity);
	}

	@Override
	public int update(LogWithdraws entity) {
		return logWithdrawsDao.update(entity);
	}

	@Override
	public int saveOrUpdate(LogWithdraws entity) {
		return logWithdrawsDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(LogWithdraws entity) {
		logWithdrawsDao.delete(entity);
	}
	
}
