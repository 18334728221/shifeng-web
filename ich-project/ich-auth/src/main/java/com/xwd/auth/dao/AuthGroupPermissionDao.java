package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthGroupPermission;

/**
 * @author ljl
 */
@Component
public class AuthGroupPermissionDao extends BaseMyIbatisDao<AuthGroupPermission, Long> {

	public Class<AuthGroupPermission> getEntityClass() {
		return AuthGroupPermission.class;
	}

	public int saveOrUpdate(AuthGroupPermission entity) {
		if (entity.getGroupId() == null) {
			return save(entity);
		} else {
			return update(entity);
		}
	}

	public Page<AuthGroupPermission> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

	/**
	 * 根据组删除权限
	 * 
	 * @param groupId
	 */
	public void deleteByGroupId(Long groupId) {
		db().delete(getEntityClass().getSimpleName() + ".deleteByGroupId", groupId);
	}

	/**
	 * 根据组查找功能
	 * 
	 * @param groupId
	 * @return
	 */
	public List<AuthGroupPermission> findByGroupId(Long groupId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findByGroupId", groupId);
	}

}
