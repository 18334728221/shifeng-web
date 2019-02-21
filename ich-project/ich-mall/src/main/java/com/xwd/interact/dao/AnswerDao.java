package com.xwd.interact.dao;

import org.springframework.stereotype.Component;

import com.xwd.interact.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class AnswerDao extends BaseMyIbatisDao<Answer, Long> {

	public Class<Answer> getEntityClass() {
		return Answer.class;
	}
	
	public int saveOrUpdate(Answer entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Answer> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
