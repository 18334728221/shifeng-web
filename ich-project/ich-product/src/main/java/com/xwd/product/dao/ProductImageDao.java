package com.xwd.product.dao;

import org.springframework.stereotype.Component;

import com.xwd.product.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class ProductImageDao extends BaseMyIbatisDao<ProductImage, Long> {

	public Class<ProductImage> getEntityClass() {
		return ProductImage.class;
	}
	
	public int saveOrUpdate(ProductImage entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ProductImage> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
