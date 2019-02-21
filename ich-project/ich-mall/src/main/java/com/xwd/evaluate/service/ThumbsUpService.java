package com.xwd.evaluate.service;

import com.frame.base.BaseService;
import com.xwd.evaluate.model.ThumbsUp;


public interface ThumbsUpService extends BaseService<ThumbsUp>{

	public int save(ThumbsUp entity);

	public int update(ThumbsUp entity);

	public int saveOrUpdate(ThumbsUp entity);

	public void delete(ThumbsUp entity);
	
	public Long queryUserNum(Long userId);
}
