package com.xwd.customer.dao;

import org.springframework.stereotype.Component;

import com.xwd.customer.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class CustomerRelationDao extends BaseMyIbatisDao<CustomerRelation, Long> {

	public Class<CustomerRelation> getEntityClass() {
		return CustomerRelation.class;
	}
	
	public int saveOrUpdate(CustomerRelation entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<CustomerRelation> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
