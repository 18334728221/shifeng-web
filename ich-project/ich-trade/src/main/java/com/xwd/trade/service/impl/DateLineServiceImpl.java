package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.DateLineDao;
import com.xwd.trade.model.DateLine;
import com.xwd.trade.model.YearLine;
import com.xwd.trade.service.DateLineService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class DateLineServiceImpl extends AbstractBaseService<DateLine> implements DateLineService{

	@Autowired
	private DateLineDao dateLineDao;
	
	public EntityDao<DateLine,Long> getEntityDao() {
		return this.dateLineDao;
	}
	
	@Override
	public int save(DateLine entity) {
		return dateLineDao.save(entity);
	}

	@Override
	public int update(DateLine entity) {
		return dateLineDao.update(entity);
	}

	@Override
	public int saveOrUpdate(DateLine entity) {
		return dateLineDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(DateLine entity) {
		dateLineDao.delete(entity);
	}

	@Override
	public DateLine getLatest(Object... params) {
		return dateLineDao.getLatest(params);
	}
	
}
