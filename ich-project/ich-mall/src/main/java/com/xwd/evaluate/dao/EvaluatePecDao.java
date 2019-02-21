package com.xwd.evaluate.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.evaluate.model.EvaluatePec;


@Component
public class EvaluatePecDao extends BaseMyIbatisDao<EvaluatePec, Long> {

	public Class<EvaluatePec> getEntityClass() {
		return EvaluatePec.class;
	}
	
	public int saveOrUpdate(EvaluatePec entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<EvaluatePec> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public int queryCommenTimes(long customerNo){
		return db().selectOne("EvaluatePec.queryCommenTimes",customerNo);
	}
}
