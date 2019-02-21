package com.xwd.auth.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.auth.model.AuthUserRelation;

/**
 * 
 * @author ljl
 *
 */
public interface AuthUserRelationService extends BaseService<AuthUserRelation>{
	
	public int save(AuthUserRelation entity);

	public int update(AuthUserRelation entity);

	public int saveOrUpdate(AuthUserRelation entity);
	
	public void delete(AuthUserRelation entity);

	public boolean checkClosedCycle(Long userId, List<Long> userList);
	
	public void deleteByUserId(Long userId);

	public List<AuthUserRelation> findUserRelationByUserId(Long userId);
}
