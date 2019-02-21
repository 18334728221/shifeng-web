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
import com.xwd.auth.dao.AuthPermissionDao;
import com.xwd.auth.model.AuthPermission;
import com.xwd.auth.service.AuthPermissionService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthPermissionServiceImpl extends AbstractBaseService<AuthPermission> implements AuthPermissionService{

	@Autowired
	private AuthPermissionDao authPermissionDao;
	
	public EntityDao<AuthPermission,Long> getEntityDao() {
		return this.authPermissionDao;
	}
	
	/**
	 * 根据用户返回权限信息
	 * @param userId
	 * @return
	 */
	public List<AuthPermission> findPermissionsByUser(Long userId) {
		return this.authPermissionDao.findPermissionsByUser(userId);
	}
	
	/**
	 * 根据组获得对应的功能权限
	 * @param groupId
	 * @return
	 */
	public List<AuthPermission> findPermissionsByGroup(Long groupId) {
		return this.authPermissionDao.findPermissionsByGroup(groupId);
	}
	
	/**
	 * 根据用户、权限类别获得对应的功能权限
	 * @param userId
	 * @param permissionType
	 * @return
	 */
	public List<AuthPermission> findPermissionsByUserAndType(Long userId, int permissionType){
		 return authPermissionDao.findPermissionsByUserAndType("userId", userId, "permissionType", permissionType);
	}
	
	/**
	 * 根据角色获得对应的功能权限
	 * @param roles
	 * @return
	 */
	public List<AuthPermission> findPermissionsByRoles(List<String> roles){
		return authPermissionDao.findPermissionsByRoles(roles);
	}

	@Override
	public int save(AuthPermission entity) {
		return authPermissionDao.save(entity);
	}

	@Override
	public int update(AuthPermission entity) {
		return authPermissionDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthPermission entity) {
		return authPermissionDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthPermission entity) {
		authPermissionDao.delete(entity);
	}
	
}
