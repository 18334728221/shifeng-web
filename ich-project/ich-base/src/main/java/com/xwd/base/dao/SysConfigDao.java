package com.xwd.base.dao;

import java.util.HashMap;

/**
 * @author ljl
 */
import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.SysConfig;
/**
 * 系统配置表
 * @author Administrator
 *
 */
@Component
public class SysConfigDao extends BaseMyIbatisDao<SysConfig, Long> {

	public Class<SysConfig> getEntityClass() {
		return SysConfig.class;
	}
	
	public int saveOrUpdate(SysConfig entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<SysConfig> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
}
