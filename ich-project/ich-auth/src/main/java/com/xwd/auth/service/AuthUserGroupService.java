package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthUserGroup;

/**
 * 
 * @author ljl
 *
 */
public interface AuthUserGroupService extends BaseService<AuthUserGroup>{
	
	public int save(AuthUserGroup entity);

	public int update(AuthUserGroup entity);

	public int saveOrUpdate(AuthUserGroup entity);
	
	public void delete(AuthUserGroup entity);

	/**
	 * 根据userId删除组
	 * @param userId
	 */
	public void deleteByUserId(Long userId);
	
	/** 
	 * 根据userId查询分配的组
	 * @param userId
	 **/
	public List<AuthUserGroup> findByUserId(Long userId);
	
}
