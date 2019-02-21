package com.xwd.auth.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.auth.dao.AuthGroupRoleDao;
import com.xwd.auth.model.AuthGroupRole;
import com.xwd.auth.service.AuthGroupRoleService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthGroupRoleServiceImpl extends AbstractBaseService<AuthGroupRole> implements AuthGroupRoleService{

	@Autowired
	private AuthGroupRoleDao authGroupRoleDao;
	
	public EntityDao<AuthGroupRole,Long> getEntityDao() {
		return this.authGroupRoleDao;
	}

	@Override
	public int save(AuthGroupRole entity) {
		return authGroupRoleDao.save(entity);
	}

	@Override
	public int update(AuthGroupRole entity) {
		return authGroupRoleDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthGroupRole entity) {
		return authGroupRoleDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthGroupRole entity) {
		authGroupRoleDao.delete(entity);
	}
	
}
