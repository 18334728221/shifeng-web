package com.xwd.trade.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.trade.model.MinuteLine;


@Component
public class MinuteLineDao extends BaseMyIbatisDao<MinuteLine, Long> {

	public Class<MinuteLine> getEntityClass() {
		return MinuteLine.class;
	}
	
	public int saveOrUpdate(MinuteLine entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<MinuteLine> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
