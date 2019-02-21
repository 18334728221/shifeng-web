package com.xwd.log.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.log.dao.LogAccountDao;
import com.xwd.log.model.LogAccount;
import com.xwd.log.service.LogAccountService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.LOG, constraint=false)
public class LogAccountServiceImpl extends AbstractBaseService<LogAccount> implements LogAccountService{

	@Autowired
	private LogAccountDao logAccountDao;
	
	public EntityDao<LogAccount,Long> getEntityDao() {
		return this.logAccountDao;
	}
	
	@Override
	public int save(LogAccount entity) {
		return logAccountDao.save(entity);
	}

	@Override
	public int update(LogAccount entity) {
		return logAccountDao.update(entity);
	}

	@Override
	public int saveOrUpdate(LogAccount entity) {
		return logAccountDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(LogAccount entity) {
		logAccountDao.delete(entity);
	}
	
}
