package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthUserRole;

/**
 * 
 * @author ljl
 *
 */
public interface AuthUserRoleService extends BaseService<AuthUserRole>{
	
	public int save(AuthUserRole entity);

	public int update(AuthUserRole entity);

	public int saveOrUpdate(AuthUserRole entity);
	
	public void delete(AuthUserRole entity);
	
	/**
	 * 根据userId删除角色
	 * @param userId
	 */
	public void deleteByUserId(Long userId);
	
	/** 
	 * 根据userId查询分配的角色
	 * @param userId
	 **/
	public List<AuthUserRole> findByUserId(Long userId);
	
}
