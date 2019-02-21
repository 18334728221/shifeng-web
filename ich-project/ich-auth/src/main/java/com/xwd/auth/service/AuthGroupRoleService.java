package com.xwd.auth.service;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthGroupRole;

/**
 * 
 * @author ljl
 *
 */
public interface AuthGroupRoleService extends BaseService<AuthGroupRole>{
	
	public int save(AuthGroupRole entity);

	public int update(AuthGroupRole entity);

	public int saveOrUpdate(AuthGroupRole entity);
	
	public void delete(AuthGroupRole entity);
	
}
