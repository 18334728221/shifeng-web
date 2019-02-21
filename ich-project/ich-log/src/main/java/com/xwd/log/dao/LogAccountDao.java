package com.xwd.log.dao;

import org.springframework.stereotype.Component;

import com.xwd.log.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class LogAccountDao extends BaseMyIbatisDao<LogAccount, Long> {

	public Class<LogAccount> getEntityClass() {
		return LogAccount.class;
	}
	
	public int saveOrUpdate(LogAccount entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<LogAccount> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
