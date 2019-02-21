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
import com.xwd.auth.dao.AuthGroupPermissionDao;
import com.xwd.auth.model.AuthGroupPermission;
import com.xwd.auth.service.AuthGroupPermissionService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthGroupPermissionServiceImpl extends AbstractBaseService<AuthGroupPermission> implements AuthGroupPermissionService{

	@Autowired
	private AuthGroupPermissionDao authGroupPermissionDao;
	
	public EntityDao<AuthGroupPermission,Long> getEntityDao() {
		return this.authGroupPermissionDao;
	}
	
	/**
	 * 根据组删除权限
	 * @param groupId
	 */
	public void deleteByGroupId(Long groupId) {
		authGroupPermissionDao.deleteByGroupId(groupId);
	}
	
	/**
	 * 根据组查找功能
	 * @param groupId
	 * @return
	 */
	public List<AuthGroupPermission> findByGroupId(Long groupId) {
		return authGroupPermissionDao.findByGroupId(groupId);
	}

	@Override
	public int save(AuthGroupPermission entity) {
		return authGroupPermissionDao.save(entity);
	}

	@Override
	public int update(AuthGroupPermission entity) {
		return authGroupPermissionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthGroupPermission entity) {
		return authGroupPermissionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthGroupPermission entity) {
		authGroupPermissionDao.delete(entity);
	}
	
}
