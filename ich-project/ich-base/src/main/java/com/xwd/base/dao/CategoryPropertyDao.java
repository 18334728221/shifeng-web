package com.xwd.base.dao;

import org.springframework.stereotype.Component;

import com.xwd.base.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class CategoryPropertyDao extends BaseMyIbatisDao<CategoryProperty, Long> {

	public Class<CategoryProperty> getEntityClass() {
		return CategoryProperty.class;
	}
	
	public int saveOrUpdate(CategoryProperty entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<CategoryProperty> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	//查询属性项
	
	
	
	//查询属性项内容
}
