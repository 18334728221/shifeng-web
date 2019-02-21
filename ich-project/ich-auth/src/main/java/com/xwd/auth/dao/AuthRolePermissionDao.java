package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthRolePermission;


/**
 * @author ljl
 */
@Component
public class AuthRolePermissionDao extends BaseMyIbatisDao<AuthRolePermission, Long> {

	public Class<AuthRolePermission> getEntityClass() {
		return AuthRolePermission.class;
	}
	
	public int saveOrUpdate(AuthRolePermission entity) {
		if (entity.getRoleId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthRolePermission> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

	/**
	 * 根据角色删除权限
	 * @param roleId
	 */
	public void deleteByRoleId(Long roleId) {
		db().delete(getEntityClass().getSimpleName() + ".deleteByRoleId",roleId);
	}

	/**
	 * 根据角色查找权限
	 * @param roleId
	 * @return
	 */
	public List<AuthRolePermission> findByRoleId(Long roleId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findByRoleId", roleId);
	}

	

}
