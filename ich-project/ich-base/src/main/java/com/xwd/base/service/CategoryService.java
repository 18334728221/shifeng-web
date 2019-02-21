package com.xwd.base.service;

import com.frame.base.BaseService;
import com.xwd.base.model.Category;


public interface CategoryService extends BaseService<Category>{

	public int save(Category entity);

	public int update(Category entity);

	public int saveOrUpdate(Category entity);

	public void delete(Category entity);
	
	public void isTop(Category entity);
}
