package com.xwd.base.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.Mq;

/**
 * @author ljl
 */

@Component
public class MqDao extends BaseMyIbatisDao<Mq, Long> {

	public Class<Mq> getEntityClass() {
		return Mq.class;
	}
	
	public int saveOrUpdate(Mq entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Mq> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
