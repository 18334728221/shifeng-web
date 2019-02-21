package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthRole;

/**
 * @author ljl
 */
@Component
public class AuthRoleDao extends BaseMyIbatisDao<AuthRole, Long> {

	public Class<AuthRole> getEntityClass() {
		return AuthRole.class;
	}
	
	public int saveOrUpdate(AuthRole entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthRole> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public List<AuthRole> findRolesByUser(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findRolesByUser", userId);
	}
	
	public List<AuthRole> findRolesByGroup(Long groupId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findRolesByGroup", groupId);
	}

}
