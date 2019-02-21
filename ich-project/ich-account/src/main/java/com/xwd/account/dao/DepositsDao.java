package com.xwd.account.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.account.model.*;


@Component
public class DepositsDao extends BaseMyIbatisDao<Deposits, Long> {

	public Class<Deposits> getEntityClass() {
		return Deposits.class;
	}
	
	public int saveOrUpdate(Deposits entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Deposits> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
