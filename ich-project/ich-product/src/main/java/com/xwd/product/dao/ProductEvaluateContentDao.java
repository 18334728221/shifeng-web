package com.xwd.product.dao;

import org.springframework.stereotype.Component;

import com.xwd.product.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class ProductEvaluateContentDao extends BaseMyIbatisDao<ProductEvaluateContent, Long> {

	public Class<ProductEvaluateContent> getEntityClass() {
		return ProductEvaluateContent.class;
	}
	
	public int saveOrUpdate(ProductEvaluateContent entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ProductEvaluateContent> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public int queryTimes(Long productCode){
		int times = db().selectOne("ProductEvaluateContent.queryTimes",productCode); 
		return times;
	}
	
}
