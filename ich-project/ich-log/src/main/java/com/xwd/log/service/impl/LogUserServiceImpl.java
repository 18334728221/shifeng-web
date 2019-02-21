package com.xwd.log.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.log.model.*;
import com.xwd.log.dao.*;
import com.xwd.log.service.*;

@Component
@Transactional
@Aspect
public class LogUserServiceImpl extends AbstractBaseService<LogUser> implements LogUserService{

	@Autowired
	private LogUserDao logUserDao;
	
	public EntityDao<LogUser,Long> getEntityDao() {
		return this.logUserDao;
	}
	
	@Override
	public int save(LogUser entity) {
		return logUserDao.save(entity);
	}

	@Override
	public int update(LogUser entity) {
		return logUserDao.update(entity);
	}

	@Override
	public int saveOrUpdate(LogUser entity) {
		return logUserDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(LogUser entity) {
		logUserDao.delete(entity);
	}
	
}
