package com.xwd.evaluate.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.evaluate.model.*;


@Component
public class EvaluateDao extends BaseMyIbatisDao<Evaluate, Long> {

	public Class<Evaluate> getEntityClass() {
		return Evaluate.class;
	}
	
	public int saveOrUpdate(Evaluate entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Evaluate> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
