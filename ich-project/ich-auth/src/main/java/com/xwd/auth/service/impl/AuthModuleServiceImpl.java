package com.xwd.auth.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.auth.dao.AuthModuleDao;
import com.xwd.auth.model.AuthModule;
import com.xwd.auth.service.AuthModuleService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthModuleServiceImpl extends AbstractBaseService<AuthModule> implements AuthModuleService{

	@Autowired
	private AuthModuleDao authModuleDao;
	
	public EntityDao<AuthModule,Long> getEntityDao() {
		return this.authModuleDao;
	}

	@Override
	public int save(AuthModule entity) {
		return authModuleDao.save(entity);
	}

	@Override
	public int update(AuthModule entity) {
		return authModuleDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthModule entity) {
		return authModuleDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthModule entity) {
		authModuleDao.delete(entity);
	}
	
}
