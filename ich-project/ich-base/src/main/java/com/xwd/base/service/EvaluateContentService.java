package com.xwd.base.service;

import com.frame.base.BaseService;
import com.xwd.base.model.EvaluateContent;


public interface EvaluateContentService extends BaseService<EvaluateContent>{

	public int save(EvaluateContent entity);

	public int update(EvaluateContent entity);

	public int saveOrUpdate(EvaluateContent entity);

	public void delete(EvaluateContent entity);
}
