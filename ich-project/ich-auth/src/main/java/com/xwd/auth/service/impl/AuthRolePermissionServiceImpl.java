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
import com.xwd.auth.dao.AuthRolePermissionDao;
import com.xwd.auth.model.AuthRolePermission;
import com.xwd.auth.service.AuthRolePermissionService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthRolePermissionServiceImpl extends AbstractBaseService<AuthRolePermission> implements AuthRolePermissionService{

	@Autowired
	private AuthRolePermissionDao authRolePermissionDao;
	
	public EntityDao<AuthRolePermission,Long> getEntityDao() {
		return this.authRolePermissionDao;
	}
	
	/**
	 * 根据角色删除权限
	 * @param roleId
	 */
	public void deleteByRoleId(Long roleId) {
		authRolePermissionDao.deleteByRoleId(roleId);
	}
	
	/**
	 * 根据角色查找权限
	 * @param roleId
	 * @return
	 */
	public List<AuthRolePermission> findByRoleId(Long roleId) {
		return authRolePermissionDao.findByRoleId(roleId);
	}

	@Override
	public int save(AuthRolePermission entity) {
		return authRolePermissionDao.save(entity);
	}

	@Override
	public int update(AuthRolePermission entity) {
		return authRolePermissionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthRolePermission entity) {
		return authRolePermissionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthRolePermission entity) {
		authRolePermissionDao.delete(entity);
	}
	
}
