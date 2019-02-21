package com.xwd.customer.dao;

import org.springframework.stereotype.Component;

import com.xwd.customer.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class FriendDao extends BaseMyIbatisDao<Friend, Long> {

	public Class<Friend> getEntityClass() {
		return Friend.class;
	}
	
	public int saveOrUpdate(Friend entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Friend> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
