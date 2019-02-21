package com.xwd.auth.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthModule;

/**
 * @author ljl
 */
@Component
public class AuthModuleDao extends BaseMyIbatisDao<AuthModule, Long> {

	public Class<AuthModule> getEntityClass() {
		return AuthModule.class;
	}

	public int saveOrUpdate(AuthModule entity) {
		if (entity.getId() == null) {
			return save(entity);
		} else {
			return update(entity);
		}
	}

	public Page<AuthModule> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

}
