package com.xwd.auth.service;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthModule;

/**
 * 
 * @author ljl
 *
 */
public interface AuthModuleService extends BaseService<AuthModule>{
	
	public int save(AuthModule entity);

	public int update(AuthModule entity);

	public int saveOrUpdate(AuthModule entity);
	
	public void delete(AuthModule entity);
	
}
