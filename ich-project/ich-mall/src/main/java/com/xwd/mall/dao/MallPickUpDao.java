package com.xwd.mall.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.mall.model.MallPickUp;


@Component
public class MallPickUpDao extends BaseMyIbatisDao<MallPickUp, Long> {

	public Class<MallPickUp> getEntityClass() {
		return MallPickUp.class;
	}
	
	public int saveOrUpdate(MallPickUp entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<MallPickUp> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
