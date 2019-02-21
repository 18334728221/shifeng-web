package com.xwd.mall.dao;

import org.springframework.stereotype.Component;

import com.xwd.mall.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class InvoiceDao extends BaseMyIbatisDao<Invoice, Long> {

	public Class<Invoice> getEntityClass() {
		return Invoice.class;
	}
	
	public int saveOrUpdate(Invoice entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Invoice> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
