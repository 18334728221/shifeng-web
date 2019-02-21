package com.xwd.interact.service;

import com.frame.base.BaseService;
import com.xwd.interact.model.Question;


public interface QuestionService extends BaseService<Question>{

	public int save(Question entity);

	public int update(Question entity);

	public int saveOrUpdate(Question entity);

	public void delete(Question entity);
}
