package com.xwd.trade.service.impl;

import java.util.ArrayList;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.TradeDao;
import com.xwd.trade.model.Trade;
import com.xwd.trade.service.TradeService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.TRADE, constraint=false)
public class TradeServiceImpl extends AbstractBaseService<Trade> implements TradeService{

	@Autowired
	private TradeDao tradeDao;
	
	public EntityDao<Trade,Long> getEntityDao() {
		return this.tradeDao;
	}
	
	@Override
	public int save(Trade entity) {
		return tradeDao.save(entity);
	}

	@Override
	public int update(Trade entity) {
		return tradeDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Trade entity) {
		return tradeDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Trade entity) {
		tradeDao.delete(entity);
	}

	@Override
	public int saveBatch(ArrayList<Trade> list) {
		return tradeDao.saveBatch(list);
	}

	@Override
	public Trade findByMonth(Object... paras) {
		return tradeDao.findByMonth(paras);
	}
	
	@Override
	public Trade findSellPoundage(Object... paras){
		return tradeDao.findSellPoundage(paras);
	}
	
}
