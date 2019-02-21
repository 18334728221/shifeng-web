package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthGroupPermission;

/**
 * 
 * @author ljl
 *
 */
public interface AuthGroupPermissionService extends BaseService<AuthGroupPermission>{
	
	public int save(AuthGroupPermission entity);

	public int update(AuthGroupPermission entity);

	public int saveOrUpdate(AuthGroupPermission entity);
	
	public void delete(AuthGroupPermission entity);

	/**
	 * 根据组删除权限
	 * @param groupId
	 */
	public void deleteByGroupId(Long groupId);
	
	/**
	 * 根据组查找功能
	 * @param groupId
	 * @return
	 */
	public List<AuthGroupPermission> findByGroupId(Long groupId);
	
	
}
