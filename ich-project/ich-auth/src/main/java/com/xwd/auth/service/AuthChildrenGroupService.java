package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthChildrenGroup;
import com.xwd.auth.model.AuthGroupPermission;

/**
 * 
 * @author ljl
 *
 */
public interface AuthChildrenGroupService extends BaseService<AuthChildrenGroup>{
	
	public int save(AuthChildrenGroup entity);

	public int update(AuthChildrenGroup entity);

	public int saveOrUpdate(AuthChildrenGroup entity);
	
	public void delete(AuthChildrenGroup entity);
	

	/**
	 * 根据组ID删除关系组
	 * @param groupId
	 */
	public void deleteByGroupId(Long groupId);
	
	
	/** 
	 * 根据groupId查询分配的组
	 *  @param groupId
	 **/
	public List<AuthGroupPermission> findByGroupId(Long groupId);
	
}
