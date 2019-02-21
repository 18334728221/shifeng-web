package com.xwd.auth.service;

import java.util.HashMap;
import java.util.List;

import com.frame.base.BaseService;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthGroup;

/**
 * 
 * @author ljl
 *
 */
public interface AuthGroupService extends BaseService<AuthGroup>{
	
	public int save(AuthGroup entity);

	public int update(AuthGroup entity);

	public int saveOrUpdate(AuthGroup entity);
	
	public void delete(AuthGroup entity);

	/**
	 * 根据用户获得对应的组
	 * @param userId
	 * @return
	 */
	public List<AuthGroup> findGroupsByUser(Long userId);
	
	public List<AuthGroup> findSubGroupsByGroup(Long groupId);
	
	public Page<AuthGroup> findExceptByGroupIdPageRequest(PageRequest<HashMap<String, Object>> pageRequest);
	
}
