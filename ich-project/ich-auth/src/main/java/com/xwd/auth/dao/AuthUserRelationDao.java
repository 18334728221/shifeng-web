package com.xwd.auth.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUserRelation;

/**
 * @author ljl
 */
@Component
public class AuthUserRelationDao extends BaseMyIbatisDao<AuthUserRelation, Long> {

	public Class<AuthUserRelation> getEntityClass() {
		return AuthUserRelation.class;
	}
	
	public int saveOrUpdate(AuthUserRelation entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<AuthUserRelation> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}

	public void deleteByUserId(Long userId) {
		db().delete(getEntityClass().getSimpleName() + ".deleteByUserId",userId);
	}

	public List<AuthUserRelation> findUserRelationByUserId(Long userId) {
		return db().selectList(getEntityClass().getSimpleName() + ".findUserRelationByUserId", userId);
	}
	

}
