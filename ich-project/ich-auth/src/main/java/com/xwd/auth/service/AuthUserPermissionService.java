package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthUserPermission;

/**
 * 
 * @author ljl
 *
 */
public interface AuthUserPermissionService extends BaseService<AuthUserPermission>{
	
	public int save(AuthUserPermission entity);

	public int update(AuthUserPermission entity);

	public int saveOrUpdate(AuthUserPermission entity);
	
	public void delete(AuthUserPermission entity);

	/**
	 * 根据userId删除功能权限
	 * @param userId
	 */
	public void deleteByUserId(Long userId);
	
	/** 
	 * 根据userId查询分配的功能
	 *  @param userId
	 **/
	public List<AuthUserPermission> findByUserId(Long userId);
	
}
