package com.xwd.msg.dao;

import org.springframework.stereotype.Component;

import com.xwd.msg.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class EmailUserDao extends BaseMyIbatisDao<EmailUser, Long> {

	public Class<EmailUser> getEntityClass() {
		return EmailUser.class;
	}
	
	public int saveOrUpdate(EmailUser entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<EmailUser> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
