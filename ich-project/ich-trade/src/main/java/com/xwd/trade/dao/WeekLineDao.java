package com.xwd.trade.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.trade.model.WeekLine;


@Component
public class WeekLineDao extends BaseMyIbatisDao<WeekLine, Long> {

	public Class<WeekLine> getEntityClass() {
		return WeekLine.class;
	}
	
	public int saveOrUpdate(WeekLine entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<WeekLine> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 根据股票编号获得最新的记录
	 * @param params
	 * @return
	 */
	public WeekLine getLatest(Object ... params){
		return db().selectOne(getEntityClass().getSimpleName() + ".getLatest", map(params));
	}
}
