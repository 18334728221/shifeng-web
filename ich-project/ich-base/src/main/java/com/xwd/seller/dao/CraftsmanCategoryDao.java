package com.xwd.seller.dao;

import org.springframework.stereotype.Component;

import com.xwd.seller.model.*;
import java.util.HashMap;
import java.util.List;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class CraftsmanCategoryDao extends BaseMyIbatisDao<CraftsmanCategory, Long> {

	public Class<CraftsmanCategory> getEntityClass() {
		return CraftsmanCategory.class;
	}
	
	public int saveOrUpdate(CraftsmanCategory entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<CraftsmanCategory> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/** 
	 * 根据craftsmanNo查询产品种类
	 * @param userId
	 **/
	public List<CraftsmanCategory> findByCraftsmanNo(Long craftsmanNo) {
		return db().selectList(getEntityClass().getSimpleName() + ".findByCraftsmanNo", craftsmanNo);
	}
}
