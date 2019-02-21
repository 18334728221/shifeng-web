package com.xwd.evaluate.service;

import com.frame.base.BaseService;
import com.xwd.evaluate.model.EvaluatePec;


public interface EvaluatePecService extends BaseService<EvaluatePec>{

	public int save(EvaluatePec entity);

	public int update(EvaluatePec entity);

	public int saveOrUpdate(EvaluatePec entity);

	public void delete(EvaluatePec entity);
	//校验是否是第一次评论
	public int queryCommenTimes(Long customerNo);
}
