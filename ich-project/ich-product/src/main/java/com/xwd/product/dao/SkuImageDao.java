package com.xwd.product.dao;

import org.springframework.stereotype.Component;

import com.xwd.product.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class SkuImageDao extends BaseMyIbatisDao<SkuImage, Long> {

	public Class<SkuImage> getEntityClass() {
		return SkuImage.class;
	}
	
	public int saveOrUpdate(SkuImage entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<SkuImage> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
