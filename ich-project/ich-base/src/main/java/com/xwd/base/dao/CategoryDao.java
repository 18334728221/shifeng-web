package com.xwd.base.dao;

import org.springframework.stereotype.Component;

import com.xwd.base.model.*;
import java.util.HashMap;
import java.util.List;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class CategoryDao extends BaseMyIbatisDao<Category, Long> {

	public Class<Category> getEntityClass() {
		return Category.class;
	}
	
	public int saveOrUpdate(Category entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Category> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public void isTop(Category entity){
		 db().selectOne("Category.updateIsTop",entity);
		 db().selectOne("Category.updateDown",entity);
	}
}
