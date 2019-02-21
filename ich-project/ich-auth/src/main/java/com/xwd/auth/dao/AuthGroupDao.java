package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthGroup;


/**
 * @author ljl
 */
@Component
public class AuthGroupDao extends BaseMyIbatisDao<AuthGroup, Long> {

	public Class<AuthGroup> getEntityClass() {
		return AuthGroup.class;
	}
	
	public int saveOrUpdate(AuthGroup entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthGroup> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public List<AuthGroup> findGroupsByUser(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findGroupsByUser", userId);
	}
	
	public List<AuthGroup> findSubGroupsByGroup(Long groupId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findSubGroupsByGroup", groupId);
	}
	
	public Page<AuthGroup> findExceptByGroupIdPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery(getEntityClass().getSimpleName() + ".findExceptByGroupIdPageRequest", pageRequest);
	}

}
