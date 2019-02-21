package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.MonthLineDao;
import com.xwd.trade.model.MonthLine;
import com.xwd.trade.service.MonthLineService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class MonthLineServiceImpl extends AbstractBaseService<MonthLine> implements MonthLineService{

	@Autowired
	private MonthLineDao monthLineDao;
	
	public EntityDao<MonthLine,Long> getEntityDao() {
		return this.monthLineDao;
	}
	
	@Override
	public int save(MonthLine entity) {
		return monthLineDao.save(entity);
	}

	@Override
	public int update(MonthLine entity) {
		return monthLineDao.update(entity);
	}

	@Override
	public int saveOrUpdate(MonthLine entity) {
		return monthLineDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(MonthLine entity) {
		monthLineDao.delete(entity);
	}
	
	@Override
	public MonthLine getLatest(Object... params) {
		return monthLineDao.getLatest(params);
	}
}
