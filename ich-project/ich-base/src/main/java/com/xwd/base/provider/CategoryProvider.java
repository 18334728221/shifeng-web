package com.xwd.base.provider;

import java.util.List;

import com.xwd.base.model.Category;

public interface CategoryProvider {

	public void save(Category entity);
	
	public void update(Category entity);
	
	public void delete(Long id);
	
	public Category get(Long id);
	
	public List<Category> findAll();
}
