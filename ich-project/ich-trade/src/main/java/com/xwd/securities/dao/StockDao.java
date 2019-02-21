package com.xwd.securities.dao;

import org.springframework.stereotype.Component;

import com.xwd.securities.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class StockDao extends BaseMyIbatisDao<Stock, Long> {

	public Class<Stock> getEntityClass() {
		return Stock.class;
	}
	
	public int saveOrUpdate(Stock entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Stock> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
