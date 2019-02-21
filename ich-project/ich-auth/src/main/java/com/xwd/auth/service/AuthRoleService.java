package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthRole;

/**
 * 
 * @author ljl
 *
 */
public interface AuthRoleService extends BaseService<AuthRole>{
	
	public int save(AuthRole entity);

	public int update(AuthRole entity);

	public int saveOrUpdate(AuthRole entity);
	
	public void delete(AuthRole entity);

	/**
	 * 根据用户id获得对应的角色
	 * @param userId
	 * @return
	 */
	public List<AuthRole> findRolesByUser(Long userId);
	
	/**
	 * 根据组id获得对应的角色
	 * @param groupId
	 * @return
	 */
	public List<AuthRole> findRolesByGroup(Long groupId);
}
