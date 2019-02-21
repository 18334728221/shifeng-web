package com.xwd.auth.service;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthUserDetail;


public interface AuthUserDetailService extends BaseService<AuthUserDetail>{

	public int save(AuthUserDetail entity);

	public int update(AuthUserDetail entity);

	public int saveOrUpdate(AuthUserDetail entity);

	public void delete(AuthUserDetail entity);
	
}
