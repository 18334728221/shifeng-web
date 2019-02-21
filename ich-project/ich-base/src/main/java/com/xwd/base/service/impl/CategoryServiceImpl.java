package com.xwd.base.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.CategoryDao;
import com.xwd.base.model.Category;
import com.xwd.base.service.CategoryService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CategoryServiceImpl extends AbstractBaseService<Category> implements CategoryService{

	@Autowired
	private CategoryDao categoryDao;
	
	public EntityDao<Category,Long> getEntityDao() {
		return this.categoryDao;
	}
	
	@Override
	public int save(Category entity) {
		return categoryDao.save(entity);
	}

	@Override
	public int update(Category entity) {
		return categoryDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Category entity) {
		return categoryDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Category entity) {
		categoryDao.delete(entity);
	}
	
	public void isTop(Category entity){
		categoryDao.isTop(entity);
	}
}
