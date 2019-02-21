package com.xwd.base.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.ImageServer;

/**
 * @author ljl
 */

@Component
public class ImageServerDao extends BaseMyIbatisDao<ImageServer, Long> {

	public Class<ImageServer> getEntityClass() {
		return ImageServer.class;
	}
	
	public int saveOrUpdate(ImageServer entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ImageServer> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
}
