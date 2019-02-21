package com.xwd.evaluate.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.evaluate.model.*;


@Component
public class EvaluateBecDao extends BaseMyIbatisDao<EvaluateBec, Long> {

	public Class<EvaluateBec> getEntityClass() {
		return EvaluateBec.class;
	}
	
	public int saveOrUpdate(EvaluateBec entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<EvaluateBec> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public long queryCommenTimes(long customerNo){
		return db().selectOne("EvaluateBec.queryCommenTimes",customerNo);
	}
}
