package com.xwd.trade.dao;

import org.springframework.stereotype.Component;

import com.xwd.trade.model.Poundage;

import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class PoundageDao extends BaseMyIbatisDao<Poundage, Long> {

	public Class<Poundage> getEntityClass() {
		return Poundage.class;
	}
	
	public int saveOrUpdate(Poundage entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Poundage> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
