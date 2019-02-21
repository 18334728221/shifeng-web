package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.TradePickUpDao;
import com.xwd.trade.model.TradePickUp;
import com.xwd.trade.service.TradePickUpService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class TradePickUpServiceImpl extends AbstractBaseService<TradePickUp> implements TradePickUpService{

	@Autowired
	private TradePickUpDao tradePickUpDao;
	
	public EntityDao<TradePickUp,Long> getEntityDao() {
		return this.tradePickUpDao;
	}
	
	@Override
	public int save(TradePickUp entity) {
		return tradePickUpDao.save(entity);
	}

	@Override
	public int update(TradePickUp entity) {
		return tradePickUpDao.update(entity);
	}

	@Override
	public int saveOrUpdate(TradePickUp entity) {
		return tradePickUpDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(TradePickUp entity) {
		tradePickUpDao.delete(entity);
	}

	@Override
	public Long findPickUpNum(Object... params) {
		return tradePickUpDao.findPickUpNum(params);
	}
	
}
