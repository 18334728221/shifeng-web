package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUserGroup;

/**
 * @author ljl
 */
@Component
public class AuthUserGroupDao extends BaseMyIbatisDao<AuthUserGroup, Long> {

	public Class<AuthUserGroup> getEntityClass() {
		return AuthUserGroup.class;
	}
	
	public int saveOrUpdate(AuthUserGroup entity) {
		if (entity.getUserId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthUserGroup> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 根据userId删除组
	 * @param userId
	 */
	public void deleteByUserId(Long userId) {
		db().delete(getEntityClass().getSimpleName() + ".deleteByUserId",userId);
	}
	
	/** 
	 * 根据userId查询分配的组
	 * @param userId
	 **/
	public List<AuthUserGroup> findByUserId(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findByUserId", userId);
	}
	

}
