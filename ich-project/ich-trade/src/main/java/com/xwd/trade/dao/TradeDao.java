package com.xwd.trade.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.trade.model.Trade;


@Component
public class TradeDao extends BaseMyIbatisDao<Trade, Long> {

	public Class<Trade> getEntityClass() {
		return Trade.class;
	}
	
	public int saveOrUpdate(Trade entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Trade> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public Trade findByMonth(Object... paras){
		return db().selectOne(getEntityClass().getSimpleName() + ".findByMonth",map(paras));
	}
	
	public Trade findSellPoundage(Object... paras){
		return db().selectOne(getEntityClass().getSimpleName() + ".findSellPoundage",map(paras));
	}
}
