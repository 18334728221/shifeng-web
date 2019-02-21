package com.xwd.product.dao;

import org.springframework.stereotype.Component;

import com.xwd.product.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class SkuDao extends BaseMyIbatisDao<Sku, Long> {

	public Class<Sku> getEntityClass() {
		return Sku.class;
	}
	
	public int saveOrUpdate(Sku entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Sku> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public Sku findLastSkuByProduct(Object... params){
		return db().selectOne(getEntityClass().getSimpleName() + ".findLastSkuByProduct", map(params));
	}
}
