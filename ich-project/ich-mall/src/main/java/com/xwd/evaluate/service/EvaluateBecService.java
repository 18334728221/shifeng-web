package com.xwd.evaluate.service;

import com.frame.base.BaseService;
import com.xwd.evaluate.model.EvaluateBec;


public interface EvaluateBecService extends BaseService<EvaluateBec>{

	public int save(EvaluateBec entity);

	public int update(EvaluateBec entity);

	public int saveOrUpdate(EvaluateBec entity);

	public void delete(EvaluateBec entity);
	
	public long queryCommenTimes(long customerNo);
}
