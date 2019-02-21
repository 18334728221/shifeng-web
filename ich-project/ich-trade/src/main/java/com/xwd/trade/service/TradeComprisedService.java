package com.xwd.trade.service;

import java.math.BigDecimal;

import com.xwd.product.model.ProductPurchase;
import com.xwd.trade.model.TradeOrder;

/**
 * 交易接口
 * 这里负责发送消息到撮合引擎
 * 返回结果在ClientReceiveServiceImpl.java里面处理
 * @author ljl
 *
 */
public interface TradeComprisedService {

	/**
	 * 买入 
	 * @param code 产品编号
	 * @param price 买入价格
	 * @param num 买入数量
	 * @param priceMethod 报价方式
	 * @param entrustMethod 委托方式
	 */
	public TradeOrder buy(Long code, BigDecimal price, Long num, Byte priceMethod, Byte entrustMethod);
	
	/**
	 * 卖出
	 * @param code 产品编号
	 * @param price 买入价格
	 * @param num 买入数量
	 * @param priceMethod 报价方式
	 * @param entrustMethod 委托方式
	 */
	public TradeOrder sell(Long code, BigDecimal price, Long num, Byte priceMethod, Byte entrustMethod);
	
	/**
	 * 撤单
	 * @param txNo 委托号
	 */
	public boolean cancel(String txNo);
	
	/**
	 * 申购
	 * @param ProductPurchase
	 */
	public ProductPurchase purchase(long productCode,long purchaseNum);
	
}
