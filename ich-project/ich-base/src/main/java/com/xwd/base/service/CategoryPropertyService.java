package com.xwd.base.service;

import java.util.List;
import java.util.Map;

import com.frame.base.BaseService;
import com.xwd.base.model.CategoryProperty;


public interface CategoryPropertyService extends BaseService<CategoryProperty>{

	public int save(CategoryProperty entity);

	public int update(CategoryProperty entity);

	public int saveOrUpdate(CategoryProperty entity);

	public void delete(CategoryProperty entity);
	
	//产品属性组合
	public List<String> assemblePropertyId (Long categoryId);
	
	//产品属性组合
	public List<String> assemblePropertyName (Long categoryId);
	
	//产品属性组合
	public Map<String, Map<Long,String >> findCategoryProperty(Long categoryId);
}
