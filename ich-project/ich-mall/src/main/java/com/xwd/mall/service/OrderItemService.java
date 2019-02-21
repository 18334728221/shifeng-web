package com.xwd.mall.service;

import com.frame.base.BaseService;
import com.xwd.mall.model.OrderItem;


public interface OrderItemService extends BaseService<OrderItem>{

	public int save(OrderItem entity);

	public int update(OrderItem entity);

	public int saveOrUpdate(OrderItem entity);

	public void delete(OrderItem entity);
	
	/**
	 * 根据订单号更新状态
	 * @param params
	 * @return
	 */
	public int updateFlow(Object... params);
}
