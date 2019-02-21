package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthPermission;

/**
 * @author ljl
 */
@Component
public class AuthPermissionDao extends BaseMyIbatisDao<AuthPermission, Long> {

	public Class<AuthPermission> getEntityClass() {
		return AuthPermission.class;
	}

	public int saveOrUpdate(AuthPermission entity) {
		if (entity.getId() == null) {
			return save(entity);
		} else {
			return update(entity);
		}
	}

	public Page<AuthPermission> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

	public List<AuthPermission> findPermissionsByUser(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findPermissionsByUser", userId);
	}

	public List<AuthPermission> findPermissionsByGroup(Long groupId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findPermissionsByGroup", groupId);
	}

	public List<AuthPermission> findPermissionsByUserAndType(Object... params) {
		return db().selectList(getEntityClass().getSimpleName() + ".findPermissionsByUserAndType", map(params));
	}

	public List<AuthPermission> findPermissionsByRoles(List<String> roles) {
		return db().selectList(getEntityClass().getSimpleName() + ".findPermissionsByRoles", roles);
	}
}
