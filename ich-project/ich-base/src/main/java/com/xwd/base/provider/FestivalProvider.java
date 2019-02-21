package com.xwd.base.provider;

import java.util.List;

import com.xwd.base.model.Festival;

public interface FestivalProvider {

	public void saveOrUpdate(Festival entity);
	
	public void delete(String id);
	
	public Festival get(String id);
	
	public List<Festival> findAll();
	
	public List<Festival> findIsFestival();
	
	public void delete(Long id);
	
	public void deleteByIds(String ids);
}
