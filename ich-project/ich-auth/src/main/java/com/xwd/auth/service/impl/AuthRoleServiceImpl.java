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
import com.xwd.auth.dao.AuthRoleDao;
import com.xwd.auth.model.AuthRole;
import com.xwd.auth.service.AuthRoleService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthRoleServiceImpl extends AbstractBaseService<AuthRole> implements AuthRoleService{

	@Autowired
	private AuthRoleDao authRoleDao;
	
	public EntityDao<AuthRole,Long> getEntityDao() {
		return this.authRoleDao;
	}
	
	/**
	 * 根据用户id获得对应的角色
	 * @param userId
	 * @return
	 */
	public List<AuthRole> findRolesByUser(Long userId) {
		return this.authRoleDao.findRolesByUser(userId);
	}
	
	/**
	 * 根据组id获得对应的角色
	 * @param groupId
	 * @return
	 */
	public List<AuthRole> findRolesByGroup(Long groupId) {
		return this.authRoleDao.findRolesByGroup(groupId);
	}

	@Override
	public int save(AuthRole entity) {
		return authRoleDao.save(entity);
	}

	@Override
	public int update(AuthRole entity) {
		return authRoleDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthRole entity) {
		return authRoleDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthRole entity) {
		authRoleDao.delete(entity);
	}
}
