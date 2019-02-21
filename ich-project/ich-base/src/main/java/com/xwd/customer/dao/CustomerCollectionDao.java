package com.xwd.customer.dao;

import org.springframework.stereotype.Component;

import com.xwd.customer.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class CustomerCollectionDao extends BaseMyIbatisDao<CustomerCollection, Long> {

	public Class<CustomerCollection> getEntityClass() {
		return CustomerCollection.class;
	}
	
	public int saveOrUpdate(CustomerCollection entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<CustomerCollection> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
