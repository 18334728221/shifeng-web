package com.xwd.log.dao;

import org.springframework.stereotype.Component;

import com.xwd.log.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class LogUserDao extends BaseMyIbatisDao<LogUser, Long> {

	public Class<LogUser> getEntityClass() {
		return LogUser.class;
	}
	
	public int saveOrUpdate(LogUser entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<LogUser> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
