package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUserPermission;

/**
 * @author ljl
 */
@Component
public class AuthUserPermissionDao extends BaseMyIbatisDao<AuthUserPermission, Long> {

	public Class<AuthUserPermission> getEntityClass() {
		return AuthUserPermission.class;
	}
	
	public int saveOrUpdate(AuthUserPermission entity) {
		if (entity.getUserId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthUserPermission> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

	/** 
	 * 根据userId查询分配的功能
	 *  @param userId
	 **/
	public List<AuthUserPermission> findByUserId(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findByUserId", userId);
	}

	/**
	 * 根据userId删除功能权限
	 * @param userId
	 */
	public void deleteByUserId(Long userId) {
		db().delete(getEntityClass().getSimpleName() + ".deleteByUserId",userId);
	}
	

}
