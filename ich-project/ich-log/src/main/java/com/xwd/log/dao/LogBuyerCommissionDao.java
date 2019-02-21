package com.xwd.log.dao;

import org.springframework.stereotype.Component;

import com.xwd.log.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class LogBuyerCommissionDao extends BaseMyIbatisDao<LogBuyerCommission, Long> {

	public Class<LogBuyerCommission> getEntityClass() {
		return LogBuyerCommission.class;
	}
	
	public int saveOrUpdate(LogBuyerCommission entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<LogBuyerCommission> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
