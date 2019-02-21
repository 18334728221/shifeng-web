package com.xwd.base.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.EmailServer;

/**
 * @author ljl
 */

@Component
public class EmailServerDao extends BaseMyIbatisDao<EmailServer, Long> {

	public Class<EmailServer> getEntityClass() {
		return EmailServer.class;
	}
	
	public int saveOrUpdate(EmailServer entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<EmailServer> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
