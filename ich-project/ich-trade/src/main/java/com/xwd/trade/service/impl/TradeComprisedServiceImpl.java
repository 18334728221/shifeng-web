package com.xwd.trade.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.auth.SecurityUtils;
import com.xwd.account.model.Account;
import com.xwd.account.provider.AccountProvider;
import com.xwd.account.service.AccountService;
import com.xwd.base.constant.SysConfigConstant;
import com.xwd.base.service.SysConfigService;
import com.xwd.base.util.NoUtils;
import com.xwd.customer.model.Customer;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.provider.CustomerProductProvider;
import com.xwd.customer.service.CustomerProductService;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.model.ProductPurchase;
import com.xwd.product.provider.ProductProvider;
import com.xwd.product.service.ProductIssueService;
import com.xwd.product.service.ProductPurchaseService;
import com.xwd.product.service.ProductService;
import com.xwd.securities.service.StockService;
import com.xwd.trade.model.TradeOrder;
import com.xwd.trade.provider.TradeOrderProvider;
import com.xwd.trade.service.TradeComprisedService;
import com.xwd.trade.service.TradeOrderService;

/**
 * 交易组合类
 * @author ljl
 *
 */
@Component
public class TradeComprisedServiceImpl implements TradeComprisedService, InitializingBean{
	
	@Autowired
	private ProductProvider productProvider;
	@Autowired
	private ProductService productService;
	@Autowired
	private AccountProvider accountProvider;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CustomerProductProvider customerProductProvider;
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private TradeOrderService tradeOrderService;
	@Autowired
	private ProductPurchaseService productPurchaseService;
	@Autowired
	private ProductIssueService productIssueService;
	@Autowired
	private TradeOrderProvider tradeOrderProvider;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private StockService stockService;

	@Override
	public TradeOrder buy(Long code, BigDecimal price, Long num, Byte priceMethod, Byte entrustMethod) {
		Customer user = (Customer) SecurityUtils.getUser();
		String key = user.getCustomerNo() + "" + code + TradeOrder.TRADE_MARK_BUY;
		try{
			//并发控制
			if (redisTemplate.hasKey(key)) {
				while (true) {
					Thread.currentThread().sleep(50);
					if (!redisTemplate.hasKey(key)) {
						break;
					}
				}
			} else {
				redisTemplate.opsForValue().append(key, num + "");
			}
			//判断资金余额是否够
			BigDecimal buyRate = new BigDecimal(0);
			String s = sysConfigService.getValue(SysConfigConstant.BUY_COMMISSION_RATE);
			if(StringUtils.isNotBlank(s)){
				buyRate = new BigDecimal(s);
			} 
			BigDecimal amount = price.multiply(new BigDecimal(num));
			BigDecimal poundage = amount.multiply(buyRate);
			poundage = poundage.divide(new BigDecimal(1000),2 , BigDecimal.ROUND_CEILING);
			amount = amount.add(poundage);
			Account account = accountProvider.getCurrentAccount(user.getCustomerNo());
			if(account == null){
				account = accountService.findUniqueBy("customerNo", user.getCustomerNo());
				if(account == null){
					return null;
				} else {
					accountProvider.save(account);
				}
			}
			//余额不够
			if(account.getBalance().compareTo(amount) < 0){
				return null;
			}
			Product product = productProvider.get(code);
			if(product == null){
				product = this.productService.findUniqueBy("code", code);
			}
			
			Calendar c = Calendar.getInstance();
			TradeOrder entity = new TradeOrder();
			entity.setCustomerNo(user.getCustomerNo());
			entity.setProductCode(code);
			entity.setProductName(product.getName());
			entity.setEntrustTime(c);
			entity.setTradeMark(TradeOrder.TRADE_MARK_BUY);
			entity.setEntrustPrice(price);
			entity.setEntrustNumber(num);
			entity.setEntrustCode(NoUtils.getEntrustCode());
			entity.setTurnoverQuantity(0L);
			entity.setPriceMethod(priceMethod);
			entity.setEntrustMethod(entrustMethod);
			entity.setCreateTime(c);
			String txNo = System.currentTimeMillis() + "" + entity.getEntrustCode();
			entity.setTxNo(txNo);
			entity.setPoundage(poundage);
			entity.addTradeStatus(TradeOrder.TRADE_STATUS_ORDER);
			entity.setTotalFund(amount);
			//股票账户里刚卖出去股票所得的资金,买入的时候需要先消耗所得资金，然后再消费可提现资金，撤单的时候就得回滚
			BigDecimal fromWithdrawCash = account.getBalance().subtract(account.getWithdrawCash());
			if(amount.compareTo(fromWithdrawCash) > 0){
				entity.setStockSellFund(fromWithdrawCash);
				entity.setFromWithdrawCash(amount.subtract(fromWithdrawCash));
			}
			this.tradeOrderService.save(entity);
			this.tradeOrderProvider.save(entity);
			accountProvider.decreaseBalance(txNo, user.getCustomerNo(), amount);
			//减少可用资金，撤单的时候需要返回
			if(entity.getFromWithdrawCash().compareTo(BigDecimal.ZERO) > 0){
				accountProvider.decreaseWithdrawCash(user.getCustomerNo(), entity.getFromWithdrawCash());
			}
			accountProvider.frozenFund(user.getCustomerNo(), amount);
			this.tradeOrderService.trade(entity);
			return entity;
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			redisTemplate.delete(key);
		}
		return null;
	}

	@Override
	public TradeOrder sell(Long code, BigDecimal price, Long num, Byte priceMethod, Byte entrustMethod) {
		Customer user = (Customer) SecurityUtils.getUser();
		String key = user.getCustomerNo() + "" + code + TradeOrder.TRADE_MARK_SELL;
		try{
			if (redisTemplate.hasKey(key)) {
				while (true) {
					Thread.currentThread().sleep(50);
					if (!redisTemplate.hasKey(key)) {
						break;
					}
				}
			} else {
				redisTemplate.opsForValue().append(key, num + "");
			}
			CustomerProduct cp = this.customerProductProvider.get(user.getCustomerNo(), code);
			if(cp == null){
				cp = this.customerProductService.findUniqueBy("customerNo", user.getCustomerNo(), "productCcode", "code");
				if(cp == null){
					return null;
				} else {
					this.customerProductProvider.save(cp);
				}
			}
			if(cp.getSellNum() < num){
				return null;
			}
			
			Product product = productProvider.get(code);
			if(product == null){
				product = this.productService.findUniqueBy("code", code);
			}
			Calendar c = Calendar.getInstance();
			TradeOrder entity = new TradeOrder();
			entity.setCustomerNo(user.getCustomerNo());
			entity.setProductCode(code);
			entity.setProductName(product.getName());
			entity.setEntrustTime(c);
			entity.setTradeMark(TradeOrder.TRADE_MARK_SELL);
			entity.setEntrustPrice(price);
			entity.setEntrustNumber(num);
			entity.setEntrustCode(NoUtils.getEntrustCode());
			entity.setTurnoverQuantity(0L);
			entity.setPriceMethod(priceMethod);
			entity.setEntrustMethod(entrustMethod);
			entity.setCreateTime(c);
			//交易买出
			String txNo = System.currentTimeMillis() + "" + entity.getEntrustCode();
			entity.setTxNo(txNo);
			entity.addTradeStatus(TradeOrder.TRADE_STATUS_ORDER);
			this.tradeOrderService.save(entity);
			this.tradeOrderProvider.save(entity);
			
			customerProductProvider.decreaseSellNum(txNo, user.getCustomerNo(), code ,num);
			this.tradeOrderService.trade(entity);
			return entity;
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			redisTemplate.delete(key);
		}
		return null;
	}

	@Override
	public boolean cancel(String txNo) {
		Customer user = (Customer) SecurityUtils.getUser();
		TradeOrder entity = this.tradeOrderProvider.get(user.getCustomerNo(), txNo);
		if(entity == null){
			return false;
		}
		this.tradeOrderService.cancel(entity);
		return true;
	}
	
	@Override
	public ProductPurchase purchase(long productCode,long purchaseNum){
		ProductIssue productIssue= productIssueService.findUniqueBy("productCode",productCode);
		BigDecimal price = productIssue.getPurchasePrice();//申购价格
		//判断资金余额是否够
		BigDecimal buyRate = new BigDecimal(0);
		String s = sysConfigService.getValue(SysConfigConstant.BUY_COMMISSION_RATE);//买入手续费
		if(StringUtils.isNotBlank(s)){
			buyRate = new BigDecimal(s);
		} 
		BigDecimal amount = price.multiply(new BigDecimal(purchaseNum));//买入个数
		BigDecimal poundage = amount.multiply(buyRate);//买入手续费
		poundage = poundage.divide(new BigDecimal(1000),2 , BigDecimal.ROUND_CEILING);
		amount = amount.add(poundage);
		Customer user = (Customer) SecurityUtils.getUser();
		Account account = accountProvider.getCurrentAccount(user.getCustomerNo());
		if(account == null){
			account = accountService.findUniqueBy("customerNo", user.getCustomerNo());
			if(account == null){
				return null;
			} else {
				accountProvider.save(account);
			}
		}
		//余额不够
		if(account.getBalance().compareTo(amount) < 0){
			return null;
		}
	
		ProductPurchase entity = new ProductPurchase();
		long customerNo = user.getCustomerNo();//客户编号
		String txNo = NoUtils.getTradeNo();// 交易码
		entity.setTxNo(txNo);
		entity.setProductCode(productCode);
		entity.setCustomerNo(customerNo);
		entity.setPurchaseNum(purchaseNum);// 申购数量
		entity.setBidNum(0L);// 中签数  初始为0
		entity.setPurchaseStatus(ProductPurchase.PURCHASE_STATUS_APPLY);// 中签状态 未中签
		String key = customerNo + "" + productCode + ProductPurchase.PURCHASE_STATUS_APPLY;
		try {
			//并发处理
			if (redisTemplate.hasKey(key)) {
				while (true) {
					Thread.currentThread().sleep(50);
					if (!redisTemplate.hasKey(key)) {
						break;
					}
				}
			} else {
				redisTemplate.opsForValue().append(key, purchaseNum + "");
			}
			productPurchaseService.save(entity);
			accountProvider.frozenFund(user.getCustomerNo(), amount);//冻结资金
			accountProvider.decreaseBalance(txNo, user.getCustomerNo(), amount);
			//可提现资金是否占用, 如果占用继续减少
			BigDecimal fromWithdrawCash = account.getBalance().subtract(account.getWithdrawCash());
			if(amount.compareTo(fromWithdrawCash) > 0){
				accountProvider.decreaseWithdrawCash(user.getCustomerNo(), amount.subtract(fromWithdrawCash));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			redisTemplate.delete(key);
		}
		return entity;
	}

	public void afterPropertiesSet() throws Exception {
		/*this.accountService.initCache();
		this.customerProductService.initCache();
		this.stockService.initCache();*/
	}

}
