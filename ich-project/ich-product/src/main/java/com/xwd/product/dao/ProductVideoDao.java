package com.xwd.product.dao;

import org.springframework.stereotype.Component;

import com.xwd.product.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class ProductVideoDao extends BaseMyIbatisDao<ProductVideo, Long> {

	public Class<ProductVideo> getEntityClass() {
		return ProductVideo.class;
	}
	
	public int saveOrUpdate(ProductVideo entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ProductVideo> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
