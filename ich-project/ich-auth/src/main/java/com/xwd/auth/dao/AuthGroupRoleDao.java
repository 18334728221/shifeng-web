package com.xwd.auth.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthGroupRole;

/**
 * @author ljl
 */
@Component
public class AuthGroupRoleDao extends BaseMyIbatisDao<AuthGroupRole, Long> {

	public Class<AuthGroupRole> getEntityClass() {
		return AuthGroupRole.class;
	}

	public int saveOrUpdate(AuthGroupRole entity) {
		if (entity.getGroupId() == null) {
			return save(entity);
		} else {
			return update(entity);
		}
	}

	public Page<AuthGroupRole> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

}
