package com.xwd.trade.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.trade.model.TradePickUp;


@Component
public class TradePickUpDao extends BaseMyIbatisDao<TradePickUp, Long> {

	public Class<TradePickUp> getEntityClass() {
		return TradePickUp.class;
	}
	
	public int saveOrUpdate(TradePickUp entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<TradePickUp> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 提货数量
	 * @param params
	 * @return
	 */
	public Long findPickUpNum(Object ...params){
		return db().selectOne(getEntityClass().getSimpleName() + ".findPickUpNum",params);
	}
}
