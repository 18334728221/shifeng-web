package com.xwd.mall.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.mall.dao.OrderItemDao;
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.OrderItemService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.MALL, constraint=false)
public class OrderItemServiceImpl extends AbstractBaseService<OrderItem> implements OrderItemService{

	@Autowired
	private OrderItemDao orderItemDao;
	
	public EntityDao<OrderItem,Long> getEntityDao() {
		return this.orderItemDao;
	}
	
	@Override
	public int save(OrderItem entity) {
		return orderItemDao.save(entity);
	}

	@Override
	public int update(OrderItem entity) {
		return orderItemDao.update(entity);
	}

	@Override
	public int saveOrUpdate(OrderItem entity) {
		return orderItemDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(OrderItem entity) {
		orderItemDao.delete(entity);
	}

	@Override
	public int updateFlow(Object... params) {
		return orderItemDao.updateFlow(params);
	}
	
}
