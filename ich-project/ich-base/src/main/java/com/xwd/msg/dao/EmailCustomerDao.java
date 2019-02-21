package com.xwd.msg.dao;

import org.springframework.stereotype.Component;

import com.xwd.msg.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class EmailCustomerDao extends BaseMyIbatisDao<EmailCustomer, Long> {

	public Class<EmailCustomer> getEntityClass() {
		return EmailCustomer.class;
	}
	
	public int saveOrUpdate(EmailCustomer entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<EmailCustomer> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
