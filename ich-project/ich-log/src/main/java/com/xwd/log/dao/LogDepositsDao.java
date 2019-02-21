package com.xwd.log.dao;

import org.springframework.stereotype.Component;

import com.xwd.log.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class LogDepositsDao extends BaseMyIbatisDao<LogDeposits, Long> {

	public Class<LogDeposits> getEntityClass() {
		return LogDeposits.class;
	}
	
	public int saveOrUpdate(LogDeposits entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<LogDeposits> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
