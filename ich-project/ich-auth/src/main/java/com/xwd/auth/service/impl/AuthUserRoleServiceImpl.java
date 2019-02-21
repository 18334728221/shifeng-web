package com.xwd.auth.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.auth.dao.AuthUserRoleDao;
import com.xwd.auth.model.AuthUserRole;
import com.xwd.auth.service.AuthUserRoleService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthUserRoleServiceImpl extends AbstractBaseService<AuthUserRole> implements AuthUserRoleService{

	@Autowired
	private AuthUserRoleDao authUserRoleDao;
	
	public EntityDao<AuthUserRole,Long> getEntityDao() {
		return this.authUserRoleDao;
	}
	
	/**
	 * 根据userId删除角色
	 * @param userId
	 */
	public void deleteByUserId(Long userId) {
		authUserRoleDao.deleteByUserId(userId);
	}
	
	/** 
	 * 根据userId查询分配的角色
	 * @param userId
	 **/
	public List<AuthUserRole> findByUserId(Long userId) {
		return authUserRoleDao.findByUserId(userId);
	}

	@Override
	public int save(AuthUserRole entity) {
		return authUserRoleDao.save(entity);
	}

	@Override
	public int update(AuthUserRole entity) {
		return authUserRoleDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthUserRole entity) {
		return authUserRoleDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthUserRole entity) {
		authUserRoleDao.delete(entity);
	}
	
}
