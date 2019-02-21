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
import com.xwd.auth.dao.AuthUserPermissionDao;
import com.xwd.auth.model.AuthUserPermission;
import com.xwd.auth.service.AuthUserPermissionService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthUserPermissionServiceImpl extends AbstractBaseService<AuthUserPermission> implements AuthUserPermissionService{

	@Autowired
	private AuthUserPermissionDao authUserPermissionDao;
	
	public EntityDao<AuthUserPermission,Long> getEntityDao() {
		return this.authUserPermissionDao;
	}
	
	/**
	 * 根据userId删除功能权限
	 * @param userId
	 */
	public void deleteByUserId(Long userId) {
		authUserPermissionDao.deleteByUserId(userId);
	}
	
	/** 
	 * 根据userId查询分配的功能
	 *  @param userId
	 **/
	public List<AuthUserPermission> findByUserId(Long userId) {
		return authUserPermissionDao.findByUserId(userId);
	}

	@Override
	public int save(AuthUserPermission entity) {
		return authUserPermissionDao.save(entity);
	}

	@Override
	public int update(AuthUserPermission entity) {
		return authUserPermissionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthUserPermission entity) {
		return authUserPermissionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthUserPermission entity) {
		authUserPermissionDao.delete(entity);
	}
	
}
