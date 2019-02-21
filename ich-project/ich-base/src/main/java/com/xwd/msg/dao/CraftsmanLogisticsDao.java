package com.xwd.msg.dao;

import org.springframework.stereotype.Component;

import com.xwd.msg.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class CraftsmanLogisticsDao extends BaseMyIbatisDao<CraftsmanLogistics, Long> {

	public Class<CraftsmanLogistics> getEntityClass() {
		return CraftsmanLogistics.class;
	}
	
	public int saveOrUpdate(CraftsmanLogistics entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<CraftsmanLogistics> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
