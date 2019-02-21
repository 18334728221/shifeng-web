package com.xwd.evaluate.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.evaluate.model.*;


@Component
public class EvaluateImageDao extends BaseMyIbatisDao<EvaluateImage, Long> {

	public Class<EvaluateImage> getEntityClass() {
		return EvaluateImage.class;
	}
	
	public int saveOrUpdate(EvaluateImage entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<EvaluateImage> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
