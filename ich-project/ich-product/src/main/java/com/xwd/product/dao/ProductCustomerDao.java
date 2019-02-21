package com.xwd.product.dao;

import org.springframework.stereotype.Component;

import com.xwd.product.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class ProductCustomerDao extends BaseMyIbatisDao<ProductCustomer, Long> {

	public Class<ProductCustomer> getEntityClass() {
		return ProductCustomer.class;
	}
	
	public int saveOrUpdate(ProductCustomer entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ProductCustomer> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
