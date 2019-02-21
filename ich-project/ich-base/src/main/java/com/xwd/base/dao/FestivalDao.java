package com.xwd.base.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.Festival;


@Component
public class FestivalDao extends BaseMyIbatisDao<Festival, Long> {

	public Class<Festival> getEntityClass() {
		return Festival.class;
	}
	
	public int saveOrUpdate(Festival entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Festival> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	/**
	 * 
	 */
	public List<Festival> findIsFestival(){
		return db().selectList(getEntityClass().getSimpleName() + ".findIsFestival");
	}
}
