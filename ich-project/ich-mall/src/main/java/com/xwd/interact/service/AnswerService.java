package com.xwd.interact.service;

import com.frame.base.BaseService;
import com.xwd.interact.model.Answer;


public interface AnswerService extends BaseService<Answer>{

	public int save(Answer entity);

	public int update(Answer entity);

	public int saveOrUpdate(Answer entity);

	public void delete(Answer entity);
}
