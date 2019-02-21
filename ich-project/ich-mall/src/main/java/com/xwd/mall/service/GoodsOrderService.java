package com.xwd.mall.service;

import com.frame.base.BaseService;
import com.xwd.mall.model.GoodsOrder;


public interface GoodsOrderService extends BaseService<GoodsOrder>{

	public int save(GoodsOrder entity);

	public int update(GoodsOrder entity);

	public int saveOrUpdate(GoodsOrder entity);

	public void delete(GoodsOrder entity);
	
	/**
	 * 取消订单
	 * @param entity
	 */
	public int updateOrderStatus(GoodsOrder entity);
	
}
