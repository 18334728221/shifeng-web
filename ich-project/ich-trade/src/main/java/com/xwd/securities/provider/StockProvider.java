package com.xwd.securities.provider;

import java.math.BigDecimal;
import java.util.List;

import com.xwd.securities.model.Stock;

public interface StockProvider {
	
	/**
	 * 清空
	 */
	public void clear();

	/**
	 * 初始化股票
	 * 开盘前调用
	 * 里面对应的参数设置为0
	 * @param stock
	 */
	public void save(Stock stock);
	
	/**
	 * 更是股票信息
	 * 撮合回调等
	 * @param stock
	 */
	public void update(Stock stock);
	
	/**
	 * 根据编号获得股票
	 * @param code
	 * @return
	 */
	public Stock get(Long code);
	
	/**
	 * 获得所有的股票
	 * @return 不为null
	 */
	public List<Stock> find();
	
	/**
	 * 分页查询
	 * @param pageNo 第几页
	 * @param pageSize 每页大小
	 * @return 不为null
	 */
	public List<Stock> find(int pageNo, int pageSize);
	
	/**
	 * 分页并且排序
	 * @param pageNo 第几页
	 * @param pageSize 每页大小
	 * @param fieldName 排序的字段
	 * @param isAsc 是否升序
	 * @return 不为null
	 */
	public List<Stock> findBySort(int pageNo, int pageSize, String fieldName, Boolean isAsc);
	
	/**
	 * 分页查询
	 * @param categoryId 产品种类
	 * @param pageNo 第几页
	 * @param pageSize 每页大小
	 * @return 不为null
	 */
	public List<Stock> find(Long categoryId, int pageNo, int pageSize);
	
	/**
	 * 分页并且排序
	 * @param categoryId 产品种类
	 * @param pageNo 第几页
	 * @param pageSize 每页大小
	 * @param fieldName 排序的字段
	 * @param isAsc 是否升序
	 * @return 不为null
	 */
	public List<Stock> findBySort(Long categoryId, int pageNo, int pageSize, String fieldName, Boolean isAsc);
	
	/**
	 * 是否超过涨停价
	 * @param price
	 * @return
	 */
	public boolean isOverLimitUpPrice(Long code, BigDecimal price);
	
	/**
	 * 是否超过跌停价
	 * @param price
	 * @return
	 */
	public boolean isOverLimitDownPrice(Long code, BigDecimal price);
	
}
