package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.WeekLineDao;
import com.xwd.trade.model.WeekLine;
import com.xwd.trade.service.WeekLineService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class WeekLineServiceImpl extends AbstractBaseService<WeekLine> implements WeekLineService{

	@Autowired
	private WeekLineDao weekLineDao;
	
	public EntityDao<WeekLine,Long> getEntityDao() {
		return this.weekLineDao;
	}
	
	@Override
	public int save(WeekLine entity) {
		return weekLineDao.save(entity);
	}

	@Override
	public int update(WeekLine entity) {
		return weekLineDao.update(entity);
	}

	@Override
	public int saveOrUpdate(WeekLine entity) {
		return weekLineDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(WeekLine entity) {
		weekLineDao.delete(entity);
	}
	
	@Override
	public WeekLine getLatest(Object... params) {
		return weekLineDao.getLatest(params);
	}
}
