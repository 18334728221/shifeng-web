package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthPermission;

/**
 * 
 * @author ljl
 *
 */
public interface AuthPermissionService extends BaseService<AuthPermission>{
	
	public int save(AuthPermission entity);

	public int update(AuthPermission entity);

	public int saveOrUpdate(AuthPermission entity);
	
	public void delete(AuthPermission entity);

	/**
	 * 根据用户返回权限信息
	 * @param userId
	 * @return
	 */
	public List<AuthPermission> findPermissionsByUser(Long userId);
	
	/**
	 * 根据组获得对应的功能权限
	 * @param groupId
	 * @return
	 */
	public List<AuthPermission> findPermissionsByGroup(Long groupId);
	
	/**
	 * 根据用户、权限类别获得对应的功能权限
	 * @param userId
	 * @param permissionType
	 * @return
	 */
	public List<AuthPermission> findPermissionsByUserAndType(Long userId, int permissionType);
	
	/**
	 * 根据角色获得对应的功能权限
	 * @param roles
	 * @return
	 */
	public List<AuthPermission> findPermissionsByRoles(List<String> roles);
	
}
