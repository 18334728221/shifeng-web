package com.xwd.evaluate.service;

import com.frame.base.BaseService;
import com.xwd.evaluate.model.EvaluateImage;


public interface EvaluateImageService extends BaseService<EvaluateImage>{

	public int save(EvaluateImage entity);

	public int update(EvaluateImage entity);

	public int saveOrUpdate(EvaluateImage entity);

	public void delete(EvaluateImage entity);
}
