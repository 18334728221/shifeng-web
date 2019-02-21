package com.xwd.account.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.account.model.*;


@Component
public class WithdrawsDao extends BaseMyIbatisDao<Withdraws, Long> {

	public Class<Withdraws> getEntityClass() {
		return Withdraws.class;
	}
	
	public int saveOrUpdate(Withdraws entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Withdraws> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
