package com.xwd.msg.dao;

import org.springframework.stereotype.Component;

import com.xwd.msg.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class SmsUserDao extends BaseMyIbatisDao<SmsUser, Long> {

	public Class<SmsUser> getEntityClass() {
		return SmsUser.class;
	}
	
	public int saveOrUpdate(SmsUser entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<SmsUser> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
