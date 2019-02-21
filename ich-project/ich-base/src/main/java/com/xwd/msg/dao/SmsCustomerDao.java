package com.xwd.msg.dao;

import org.springframework.stereotype.Component;

import com.xwd.msg.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class SmsCustomerDao extends BaseMyIbatisDao<SmsCustomer, Long> {

	public Class<SmsCustomer> getEntityClass() {
		return SmsCustomer.class;
	}
	
	public int saveOrUpdate(SmsCustomer entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<SmsCustomer> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
