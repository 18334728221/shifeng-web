package com.xwd.customer.service;

import com.frame.base.BaseService;
import com.xwd.customer.model.CustomerProduct;


public interface CustomerProductService extends BaseService<CustomerProduct>{

	public int save(CustomerProduct entity);

	public int update(CustomerProduct entity);

	public int saveOrUpdate(CustomerProduct entity);

	public void delete(CustomerProduct entity);
	
	/**
	 * 根据股票编号批量更新可卖数量
	 * 每天早上股票初始化的时候
	 * @param paras
	 * @return
	 */
	public int updateSellNumAll(Object... paras);
	
	/**
	 * 根据股票编号批量更新用户持仓的最新价格
	 * @param paras
	 * @return
	 */
	public int updatePrice(Object... paras);
	
	/**
	 * 更新缓存
	 * 数据库信息重新加载到缓存
	 */
	public void initCache();
}
