package com.xwd.base.dao;

import org.springframework.stereotype.Component;

import com.xwd.base.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class EvaluateContentDao extends BaseMyIbatisDao<EvaluateContent, Long> {

	public Class<EvaluateContent> getEntityClass() {
		return EvaluateContent.class;
	}
	
	public int saveOrUpdate(EvaluateContent entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<EvaluateContent> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
