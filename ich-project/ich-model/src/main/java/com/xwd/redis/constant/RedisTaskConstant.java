package com.xwd.redis.constant;

/**
 * redis常量
 * 
 * @author ljl
 *
 */
public class RedisTaskConstant {

	// 账户放入redis 开头key
	public final static String ACCOUNT_KEY = "account";
	// 增加余额
	public final static String ACCOUNT_INCREASE_BALANCE_KEY = "account_increase_balance";
	// 减少余额
	public final static String ACCOUNT_DECREASE_BALANCE_KEY = "account_decrease_balance";
	// 更新
	public final static String ACCOUNT_SET_UPDATE_KEY = "account_set";
	// 账户操作
	public final static String ACCOUNT_OPERATION_LIST_KEY = "account_operation_list";

	// 股票
	public final static String CUSTOMER_PRODUCT_KEY = "customer_product";
	// 更新
	public final static String CUSTOMER_PRODUCT_SET_UPDATE_KEY = "customer_product_set";
	// 增加可卖数量
	public final static String CUSTOMER_PRODUCT_INCREASE_SELL_KEY = "customer_product_increase_sell";
	// 减少可卖数量
	public final static String CUSTOMER_PRODUCT_DECREASE_SELL_KEY = "customer_product_decrease_sell";
	// 持仓操作
	public final static String CUSTOMER_PRODUCT_OPERATION_LIST_KEY = "customer_product_operation_list";
}
