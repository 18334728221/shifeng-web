package com.xwd.trade.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.trade.model.DateLine;


@Component
public class DateLineDao extends BaseMyIbatisDao<DateLine, Long> {

	public Class<DateLine> getEntityClass() {
		return DateLine.class;
	}
	
	public int saveOrUpdate(DateLine entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<DateLine> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 根据股票编号获得最新的记录
	 * @param params
	 * @return
	 */
	public DateLine getLatest(Object ... params){
		return db().selectOne(getEntityClass().getSimpleName() + ".getLatest", map(params));
	}
}
