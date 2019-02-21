package com.xwd.customer.provider;

import java.math.BigDecimal;
import java.util.List;

import com.xwd.account.dto.CustomerProductBean;
import com.xwd.customer.model.CustomerProduct;

/**
 * 持仓表
 * @author ljl
 *
 */
public interface CustomerProductProvider {

	public void save(CustomerProduct entity);
	
	public void update(CustomerProduct entity, boolean isNeedUpdateDatabase);
	
	/**
	 * 根据顾客删除
	 * @param customerNo
	 */
	public void delete(Long customerNo);
	
	/**
	 * 根据顾客、产品代码删除
	 * @param customerNo
	 * @param Long
	 */
	public void delete(Long customerNo, Long productCode);
	
	/**
	 * 根据顾客获得股票
	 * @param customerNo
	 * @return
	 */
	public List<CustomerProduct> get(Long customerNo);
	
	/**
	 * 根据顾客、产品代码获得 有延迟
	 * @param customerNo
	 * @param productCode
	 * @return
	 */
	public CustomerProduct get(Long customerNo, Long productCode);
	
	/**
	 * 根据顾客、产品代码获得 没有有延迟
	 * @param customerNo
	 * @param productCode
	 * @return
	 */
	public CustomerProduct getCurrent(Long customerNo, Long productCode);
	
	/**
	 * 新增总数
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void increaseTotal(Long customerNo, Long productCode, Long amount);
	
	/**
	 * 减少总数
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void decreaseTotal(Long customerNo, Long productCode, Long amount);
	
	/**
	 * 新增可卖数量
	 * @param txNo 支持t+0交易用
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void increaseSellNum(String txNo, Long customerNo, Long productCode, Long amount);
	
	/**
	 * 减少可卖数量
	 * @param txNo
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void decreaseSellNum(String txNo, Long customerNo, Long productCode, Long amount);
	
	/**
	 * 增加购买成本，不含手续费
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void increaseBuyCost(Long customerNo, Long productCode, BigDecimal amount);
	
	/**
	 * 增加买入手续费
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void increaseBuyPoundage(Long customerNo, Long productCode, BigDecimal amount);
	
	/**
	 * 增加卖出手续费
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void increaseSellPoundage(Long customerNo, Long productCode, BigDecimal amount);
	
	/**
	 * 新增套现资金
	 * @param customerNo
	 * @param productCode
	 * @param amount
	 */
	public void increaseCashFund(Long customerNo, Long productCode, BigDecimal amount);
	
	/**
	 * 弹出来一个持仓Bean
	 * @return null表示没有了
	 */
	public CustomerProductBean pop();
	
	/**
	 * 删除临时缓存
	 * @param key
	 * @param txNo
	 */
	public void delete(String key, String txNo);
	
}
