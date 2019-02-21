package com.xwd.interact.dao;

import org.springframework.stereotype.Component;

import com.xwd.interact.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class QuestionDao extends BaseMyIbatisDao<Question, Long> {

	public Class<Question> getEntityClass() {
		return Question.class;
	}
	
	public int saveOrUpdate(Question entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Question> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
