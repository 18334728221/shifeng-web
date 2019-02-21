package com.xwd.account.provider.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xwd.account.dto.AccountBean;
import com.xwd.account.model.Account;
import com.xwd.account.provider.AccountProvider;
import com.xwd.redis.constant.RedisTaskConstant;

@Service
public class AccountProviderImpl implements AccountProvider {

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void save(Account entity) {
		if (entity == null || entity.getCustomerNo() == null) {
			return;
		}
		redisTemplate.opsForValue().set(RedisTaskConstant.ACCOUNT_KEY + entity.getCustomerNo(), entity);
	}

	@Override
	public synchronized void update(Account entity, boolean isUpdateDatabase) {
		if (entity == null || entity.getCustomerNo() == null) {
			return;
		}
		redisTemplate.opsForValue().set(RedisTaskConstant.ACCOUNT_KEY + entity.getCustomerNo(), entity);
		if (isUpdateDatabase) {
			redisTemplate.opsForSet().add(RedisTaskConstant.ACCOUNT_SET_UPDATE_KEY, entity.getCustomerNo());
		}
	}
	
	@Override
	public Account get(Long customerNo) {
		return (Account) redisTemplate.opsForValue().get(RedisTaskConstant.ACCOUNT_KEY + customerNo);
	}

	@Override
	public Account getCurrentAccount(Long customerNo) {
		Account entity = (Account) redisTemplate.opsForValue().get(RedisTaskConstant.ACCOUNT_KEY + customerNo);
		if (entity != null) {
			entity.setBalance(entity.getBalance().add(getBalance(customerNo)));
		}
		return entity;
	}

	@Override
	public void increaseBalance(String txNo, Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_INCREASE_BALANCE);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		bean.setIsIncrease(true);
		bean.setTxNo(txNo);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
		// 放入临时缓存
		redisTemplate.opsForHash().put(RedisTaskConstant.ACCOUNT_INCREASE_BALANCE_KEY + customerNo, txNo, amount);
	}

	@Override
	public void decreaseBalance(String txNo, Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_DECREASE_BALANCE);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		bean.setTxNo(txNo);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
		// 放入临时缓存
		redisTemplate.opsForHash().put(RedisTaskConstant.ACCOUNT_DECREASE_BALANCE_KEY + customerNo, txNo, amount);
	}

	@Override
	public void frozenFund(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_FROZEN_FUND);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void unfrozenFund(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_UNFROZEN_FUND);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}
	
	@Override
	public void increaseBuyPoundage(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_INCREASE_BUY_POUNDAGE);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void increaseSellPoundage(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_INCREASE_SELL_POUNDAGE);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void increaseCost(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_INCREASE_COST);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void decreaseCost(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_DECREASE_COST);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void setCost(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_SET_COST);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void increaseMarketValue(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_INCREASE_MARKET_VALUE);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void decreaseMarketValue(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_DECREASE_MARKET_VALUE);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void setMarketValue(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_SET_MARKET_VALUE);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void increaseWithdrawCash(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_INCREASE_WITHDRAW_CASH);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void decreaseWithdrawCash(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_DECREASE_WITHDRAW_CASH);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void profit(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_PROFIT);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void loss(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_LOSS);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}

	@Override
	public void setProfitAndLoss(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_SET_PROFIT_AND_LOSS);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}
	
	@Override
	public void addHistoryProfitAndLoss(Long customerNo, BigDecimal amount) {
		AccountBean bean = new AccountBean();
		bean.setOperation(AccountBean.OPERATION_ADD_HISTORY_PROFIT_AND_LOSS);
		bean.setCustomerNo(customerNo);
		bean.setValue(amount);
		redisTemplate.opsForList().leftPush(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY, bean);
	}
	
	/**
	 * 获得临时缓存中余额
	 * @param customerNo
	 * @return
	 */
	private BigDecimal getBalance(Long customerNo){
		BigDecimal result = new BigDecimal(0);
		Set<String> set = redisTemplate.opsForHash().keys(RedisTaskConstant.ACCOUNT_INCREASE_BALANCE_KEY + customerNo);
		if(set != null){
			List<BigDecimal> list = (List<BigDecimal>) redisTemplate.opsForHash().multiGet(RedisTaskConstant.ACCOUNT_INCREASE_BALANCE_KEY + customerNo, set);
			for (BigDecimal o : list) {
				result = result.add(o);
			}
		}
		set = redisTemplate.opsForHash().keys(RedisTaskConstant.ACCOUNT_DECREASE_BALANCE_KEY + customerNo);
		if(set != null){
			List<BigDecimal> list = (List<BigDecimal>) redisTemplate.opsForHash().multiGet(RedisTaskConstant.ACCOUNT_DECREASE_BALANCE_KEY + customerNo, set);
			for (BigDecimal o : list) {
				result = result.subtract(o);
			}
		}
		return result;
	}

	@Override
	public AccountBean pop() {
		return (AccountBean)redisTemplate.opsForList().rightPop(RedisTaskConstant.ACCOUNT_OPERATION_LIST_KEY);
	}

	@Override
	public void delete(String key, String txNo) {
		redisTemplate.opsForHash().delete(key, txNo);
	}

}
