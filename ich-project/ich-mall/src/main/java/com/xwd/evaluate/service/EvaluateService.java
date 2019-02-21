package com.xwd.evaluate.service;

import com.frame.base.BaseService;
import com.xwd.evaluate.model.Evaluate;


public interface EvaluateService extends BaseService<Evaluate>{

	public int save(Evaluate entity);

	public int update(Evaluate entity);

	public int saveOrUpdate(Evaluate entity);

	public void delete(Evaluate entity);
}
