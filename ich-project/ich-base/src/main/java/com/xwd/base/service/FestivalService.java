package com.xwd.base.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.base.model.Festival;


public interface FestivalService extends BaseService<Festival>{

	public int save(Festival entity);

	public int update(Festival entity);

	public int saveOrUpdate(Festival entity);

	public void delete(Festival entity);
	
	public List<Festival> findIsFestival();
	
	public void initCache();
}
