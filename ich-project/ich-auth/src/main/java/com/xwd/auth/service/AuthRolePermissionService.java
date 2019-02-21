package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthRolePermission;

/**
 * 
 * @author ljl
 *
 */
public interface AuthRolePermissionService extends BaseService<AuthRolePermission>{
	
	public int save(AuthRolePermission entity);

	public int update(AuthRolePermission entity);

	public int saveOrUpdate(AuthRolePermission entity);
	
	public void delete(AuthRolePermission entity);
	
	/**
	 * 根据角色删除权限
	 * @param roleId
	 */
	public void deleteByRoleId(Long roleId);
	
	/**
	 * 根据角色查找权限
	 * @param roleId
	 * @return
	 */
	public List<AuthRolePermission> findByRoleId(Long roleId);
	
}
