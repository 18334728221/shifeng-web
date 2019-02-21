package com.xwd.trade.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.trade.model.TradeOrder;


@Component
public class TradeOrderDao extends BaseMyIbatisDao<TradeOrder, Long> {

	public Class<TradeOrder> getEntityClass() {
		return TradeOrder.class;
	}
	
	public int saveOrUpdate(TradeOrder entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<TradeOrder> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public List<TradeOrder> findSuccess(Object ...params) {
		return db().selectList(getEntityClass().getSimpleName()+".findSuccess", map(params));
	}
	
	/**
	 * 撤单的时候批量更新
	 * @param params
	 * @return
	 */
	public int updateAsCancel(Object... params){
		return db().update(this.getEntityClass().getSimpleName() + ".updateAsCancel", map(params));
	}
}
