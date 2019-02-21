package com.xwd.customer.dao;

import org.springframework.stereotype.Component;

import com.xwd.customer.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class AddressDao extends BaseMyIbatisDao<Address, Long> {

	public Class<Address> getEntityClass() {
		return Address.class;
	}
	
	public int saveOrUpdate(Address entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Address> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
