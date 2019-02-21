package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUserRole;

/**
 * @author ljl
 */
@Component
public class AuthUserRoleDao extends BaseMyIbatisDao<AuthUserRole, Long> {

	public Class<AuthUserRole> getEntityClass() {
		return AuthUserRole.class;
	}
	
	public int saveOrUpdate(AuthUserRole entity) {
		if (entity.getUserId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthUserRole> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 根据userId删除角色
	 * @param userId
	 */
	public void deleteByUserId(Long userId) {
		db().delete(getEntityClass().getSimpleName() + ".deleteByUserId",userId);
	}

	/** 
	 * 根据userId查询分配的角色
	 * @param userId
	 **/
	public List<AuthUserRole> findByUserId(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findByUserId", userId);
	}

}
