package com.xwd.evaluate.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.evaluate.model.*;


@Component
public class ThumbsUpDao extends BaseMyIbatisDao<ThumbsUp, Long> {

	public Class<ThumbsUp> getEntityClass() {
		return ThumbsUp.class;
	}
	
	public int saveOrUpdate(ThumbsUp entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ThumbsUp> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public Long queryUserNum(Long userId){
		return db().selectOne("ThumbsUp.queryUserCount",userId);
	}
}
