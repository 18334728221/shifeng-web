package com.xwd.log.dao;

import org.springframework.stereotype.Component;

import com.xwd.log.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class LogSellerCommissionDao extends BaseMyIbatisDao<LogSellerCommission, Long> {

	public Class<LogSellerCommission> getEntityClass() {
		return LogSellerCommission.class;
	}
	
	public int saveOrUpdate(LogSellerCommission entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<LogSellerCommission> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
