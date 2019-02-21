package com.xwd.base.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.MqTheme;

/**
 * @author ljl
 */

@Component
public class MqThemeDao extends BaseMyIbatisDao<MqTheme, Long> {

	public Class<MqTheme> getEntityClass() {
		return MqTheme.class;
	}
	
	public int saveOrUpdate(MqTheme entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<MqTheme> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
