package com.xwd.mall.service;

import com.xwd.customer.model.Customer;
import com.xwd.mall.model.GoodsOrder;

/**
 * 订单组合类
 * @author admin
 *
 */
public interface OrderComprisedService {
	
	/**
	 * 提交订单
	 */
	public GoodsOrder save(Customer user,Long productCode,String addressId,String description,String av,String num,String amount,String paymentAmount);
	
	/**
	 * 购物车结算
	 * @return
	 */
	public GoodsOrder settleAccounts(Customer user, String addressId, String description,
			String [] productSku, String [] num, String amount, String paymentAmount);
}
