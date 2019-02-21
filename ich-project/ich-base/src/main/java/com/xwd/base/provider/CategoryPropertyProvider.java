package com.xwd.base.provider;

import java.util.List;

import com.xwd.base.model.CategoryProperty;

public interface CategoryPropertyProvider {

	public void save(CategoryProperty entity);
	
	public void update(CategoryProperty entity);
	
	public void delete(Long id);
	
	public CategoryProperty get(Long id);
	
	public List<CategoryProperty> findAll();
	
	public List<CategoryProperty> find(Long categoryId);
}
