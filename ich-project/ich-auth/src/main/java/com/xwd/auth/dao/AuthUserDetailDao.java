package com.xwd.auth.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUserDetail;

/**
 * @author ljl
 */

@Component
public class AuthUserDetailDao extends BaseMyIbatisDao<AuthUserDetail, Long> {

	public Class<AuthUserDetail> getEntityClass() {
		return AuthUserDetail.class;
	}
	
	public int saveOrUpdate(AuthUserDetail entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthUserDetail> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
