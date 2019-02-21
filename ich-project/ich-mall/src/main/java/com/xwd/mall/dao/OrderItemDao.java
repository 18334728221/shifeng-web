package com.xwd.mall.dao;

import org.springframework.stereotype.Component;

import com.xwd.mall.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class OrderItemDao extends BaseMyIbatisDao<OrderItem, Long> {

	public Class<OrderItem> getEntityClass() {
		return OrderItem.class;
	}
	
	public int saveOrUpdate(OrderItem entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<OrderItem> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 根据订单号更新状态
	 * @param params
	 * @return
	 */
	public int updateFlow(Object... params){
		return this.db().update("updateFlow", map(params));
	}
}
