package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthChildrenGroup;
import com.xwd.auth.model.AuthGroupPermission;

/**
 * @author ljl
 */
@Component
public class AuthChildrenGroupDao extends BaseMyIbatisDao<AuthChildrenGroup, Long> {

	public Class<AuthChildrenGroup> getEntityClass() {
		return AuthChildrenGroup.class;
	}

	public int saveOrUpdate(AuthChildrenGroup entity) {
		if (entity.getGroupId() == null) {
			return save(entity);
		} else {
			return update(entity);
		}
	}

	public Page<AuthChildrenGroup> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

	/**
	 * 根据组ID删除关系组
	 * 
	 * @param groupId
	 */
	public void deleteByGroupId(Long groupId) {
		db().delete(getEntityClass().getSimpleName() + ".deleteByGroupId", groupId);
	}

	/**
	 * 根据groupId查询分配的组
	 * 
	 * @param groupId
	 **/
	public List<AuthGroupPermission> findByGroupId(Long groupId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findByGroupId", groupId);
	}


}
