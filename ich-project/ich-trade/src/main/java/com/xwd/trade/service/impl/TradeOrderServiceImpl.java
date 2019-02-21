package com.xwd.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.constant.SysConfigConstant;
import com.xwd.engine.facade.service.EngineFacadeService;
import com.xwd.engine.model.Order;
import com.xwd.trade.dao.TradeOrderDao;
import com.xwd.trade.model.TradeOrder;
import com.xwd.trade.service.TradeOrderService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.TRADE, constraint=false)
public class TradeOrderServiceImpl extends AbstractBaseService<TradeOrder> implements TradeOrderService{

	@Autowired
	private TradeOrderDao tradeOrderDao;
	@Autowired
	private EngineFacadeService engineFacadeService;
	
	public EntityDao<TradeOrder,Long> getEntityDao() {
		return this.tradeOrderDao;
	}
	
	@Override
	public int save(TradeOrder entity) {
		return tradeOrderDao.save(entity);
	}

	@Override
	public int update(TradeOrder entity) {
		return tradeOrderDao.update(entity);
	}

	@Override
	public int saveOrUpdate(TradeOrder entity) {
		return tradeOrderDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(TradeOrder entity) {
		tradeOrderDao.delete(entity);
	}

	@Override
	public int saveBatch(ArrayList<TradeOrder> list) {
		return tradeOrderDao.saveBatch(list);
	}
	
	public  List<TradeOrder> findSuccess(Object ...params){
		return tradeOrderDao.findSuccess(params);
	}

	@Override
	public void trade(TradeOrder entity) {
		if(entity == null){
			return;
		}
		Order order = new Order();
		order.setBroker(SysConfigConstant.BROKER_NO);
		order.setShareholderNo(entity.getCustomerNo());
		order.setVolume(entity.getEntrustNumber());
		order.setCode(entity.getProductCode());
		order.setTxNo(entity.getTxNo());
		order.setPrice(entity.getEntrustPrice().multiply(new BigDecimal(100)).longValue());
		order.setTradeMark(entity.getTradeMark());
		order.setPriceMethod(entity.getPriceMethod());
		this.engineFacadeService.getTradeService().trade(order);
	}

	@Override
	public void cancel(TradeOrder entity) {
		if(entity == null){
			return;
		}
		Order order = new Order();
		order.setBroker(SysConfigConstant.BROKER_NO);
		order.setShareholderNo(entity.getCustomerNo());
		order.setTxNo(entity.getTxNo());
		order.setVolume(entity.getEntrustNumber());
		order.setCode(entity.getProductCode());
		order.setPrice(entity.getEntrustPrice().multiply(new BigDecimal(100)).longValue());
		order.setTradeMark(Order.TRADE_MARK_CANCEL);
		this.engineFacadeService.getTradeService().cancel(order);
	}
	
	@Override
	public int updateAsCancel(Object... params){
		return this.tradeOrderDao.updateAsCancel(params);
	}
}
