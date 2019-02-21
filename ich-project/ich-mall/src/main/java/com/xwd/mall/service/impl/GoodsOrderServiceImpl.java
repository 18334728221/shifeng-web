package com.xwd.mall.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.mall.dao.GoodsOrderDao;
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.service.GoodsOrderService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.MALL, constraint=false)
public class GoodsOrderServiceImpl extends AbstractBaseService<GoodsOrder> implements GoodsOrderService{

	@Autowired
	private GoodsOrderDao goodsOrderDao;
	
	public EntityDao<GoodsOrder,Long> getEntityDao() {
		return this.goodsOrderDao;
	}
	
	@Override
	public int save(GoodsOrder entity) {
		return goodsOrderDao.save(entity);
	}

	@Override
	public int update(GoodsOrder entity) {
		return goodsOrderDao.update(entity);
	}

	@Override
	public int saveOrUpdate(GoodsOrder entity) {
		return goodsOrderDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(GoodsOrder entity) {
		goodsOrderDao.delete(entity);
	}
	
	@Override
	public int updateOrderStatus(GoodsOrder entity){
		return goodsOrderDao.updateOrderStatus(entity);
	}
	
}
