package com.xwd.customer.provider.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.account.dto.CustomerProductBean;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.provider.CustomerProductProvider;
import com.xwd.redis.constant.RedisTaskConstant;

@Service
public class CustomerProductProviderImpl implements CustomerProductProvider{

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void save(CustomerProduct entity) {
		if(entity == null){
			return;
		}
		redisTemplate.opsForHash().put(RedisTaskConstant.CUSTOMER_PRODUCT_KEY + entity.getCustomerNo(), entity.getProductCode(), entity);
	}

	@Override
	public void update(CustomerProduct entity, boolean isNeedUpdateDatabase) {
		if(entity == null){
			return;
		}
		redisTemplate.opsForHash().put(RedisTaskConstant.CUSTOMER_PRODUCT_KEY + entity.getCustomerNo(), entity.getProductCode(), entity);
		if(isNeedUpdateDatabase){
			redisTemplate.opsForSet().add(RedisTaskConstant.CUSTOMER_PRODUCT_SET_UPDATE_KEY, entity.getCustomerNo() + "=" + entity.getProductCode());
		}
	}

	@Override
	public void delete(Long customerNo) {
		if(customerNo == null){
			return;
		}
		redisTemplate.delete(RedisTaskConstant.CUSTOMER_PRODUCT_KEY + customerNo);
	}

	@Override
	public void delete(Long customerNo, Long productCode) {
		redisTemplate.opsForHash().delete(RedisTaskConstant.CUSTOMER_PRODUCT_KEY + customerNo, productCode);
	}

	@Override
	public List<CustomerProduct> get(Long customerNo) {
		Set<Long> set = redisTemplate.opsForHash().keys(RedisTaskConstant.CUSTOMER_PRODUCT_KEY + customerNo);
		List<CustomerProduct> result = new ArrayList<CustomerProduct>();
		if(set == null || set.isEmpty()){
			return result;
		}
		List<Object> list = redisTemplate.opsForHash().values(set);
		for(Object o : list){
			result.add((CustomerProduct)o);
		}
		return result;
	}

	@Override
	public CustomerProduct get(Long customerNo, Long productCode) {
		return (CustomerProduct)redisTemplate.opsForHash().get(RedisTaskConstant.CUSTOMER_PRODUCT_KEY + customerNo, productCode);
	}

	@Override
	public CustomerProduct getCurrent(Long customerNo, Long productCode) {
		CustomerProduct entity = (CustomerProduct)redisTemplate.opsForHash().get(RedisTaskConstant.CUSTOMER_PRODUCT_KEY + customerNo, productCode);
		if(entity != null){
			entity.setSellNum(entity.getSellNum() + getSellNum(customerNo, productCode));
		}
		return entity;
	}

	@Override
	public void increaseTotal(Long customerNo, Long productCode, Long amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_INCREASE_TOTAL);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void decreaseTotal(Long customerNo, Long productCode, Long amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_DECREASE_TOTAL);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void increaseSellNum(String txNo, Long customerNo, Long productCode, Long amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_INCREASE_SELL_NUM);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		bean.setIsIncrease(true);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
		//临时缓存
		Long value = (Long)redisTemplate.opsForHash().get(RedisTaskConstant.CUSTOMER_PRODUCT_INCREASE_SELL_KEY + bean.getKey(), txNo);
		if(value == null){
			value = amount;
		} else {
			value += amount;
		}
		redisTemplate.opsForHash().put(RedisTaskConstant.CUSTOMER_PRODUCT_INCREASE_SELL_KEY + bean.getKey(), txNo, value);
	}

	@Override
	public void decreaseSellNum(String txNo, Long customerNo, Long productCode, Long amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_DECREASE_SELL_NUM);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
		//临时缓存
		Long value = (Long)redisTemplate.opsForHash().get(RedisTaskConstant.CUSTOMER_PRODUCT_DECREASE_SELL_KEY + bean.getKey(), txNo);
		if(value == null){
			value = amount;
		} else {
			value += amount;
		}
		redisTemplate.opsForHash().put(RedisTaskConstant.CUSTOMER_PRODUCT_DECREASE_SELL_KEY + bean.getKey(), txNo, value);
	}

	@Override
	public void increaseBuyCost(Long customerNo, Long productCode, BigDecimal amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_INCREASE_BUY_COST);
		bean.setCustomerNo(customerNo);
		bean.setCost(amount);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void increaseBuyPoundage(Long customerNo, Long productCode, BigDecimal amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_INCREASE_BUY_POUNDAGE);
		bean.setCustomerNo(customerNo);
		bean.setCost(amount);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
	}
	
	@Override
	public void increaseSellPoundage(Long customerNo, Long productCode, BigDecimal amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_INCREASE_SELL_POUNDAGE);
		bean.setCustomerNo(customerNo);
		bean.setCost(amount);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void increaseCashFund(Long customerNo, Long productCode, BigDecimal amount) {
		CustomerProductBean bean = new CustomerProductBean();
		bean.setOperation(CustomerProductBean.OPERATION_INCREASE_CASH_FUND);
		bean.setCustomerNo(customerNo);
		bean.setCost(amount);
		bean.setProductCode(productCode);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY, bean);
	}
	
	/**
	 * 获得临时缓存中余额
	 * @param customerNo
	 * @return
	 */
	private Long getSellNum(Long customerNo, Long productCode){
		Long result = 0L;
		String key = RedisTaskConstant.CUSTOMER_PRODUCT_INCREASE_SELL_KEY + customerNo + productCode;
		Set<String> set = redisTemplate.opsForHash().keys(key);
		if(set != null){
			List<Long> list = (List<Long>) redisTemplate.opsForHash().multiGet(key, set);
			for (Long o : list) {
				result += o;
			}
		}
		key = RedisTaskConstant.CUSTOMER_PRODUCT_DECREASE_SELL_KEY + customerNo + productCode;
		set = redisTemplate.opsForHash().keys(key);
		if(set != null){
			List<Long> list = (List<Long>) redisTemplate.opsForHash().multiGet(key, set);
			for (Long o : list) {
				result -= o;
			}
		}
		return result;
	}

	@Override
	public CustomerProductBean pop() {
		return (CustomerProductBean)redisTemplate.opsForList().rightPop(RedisTaskConstant.CUSTOMER_PRODUCT_OPERATION_LIST_KEY);
	}

	@Override
	public void delete(String key, String txNo) {
		redisTemplate.opsForHash().delete(key, txNo);
	}

}
