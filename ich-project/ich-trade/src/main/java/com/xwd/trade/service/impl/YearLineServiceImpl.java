package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.YearLineDao;
import com.xwd.trade.model.YearLine;
import com.xwd.trade.service.YearLineService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class YearLineServiceImpl extends AbstractBaseService<YearLine> implements YearLineService{

	@Autowired
	private YearLineDao yearLineDao;
	
	public EntityDao<YearLine,Long> getEntityDao() {
		return this.yearLineDao;
	}
	
	@Override
	public int save(YearLine entity) {
		return yearLineDao.save(entity);
	}

	@Override
	public int update(YearLine entity) {
		return yearLineDao.update(entity);
	}

	@Override
	public int saveOrUpdate(YearLine entity) {
		return yearLineDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(YearLine entity) {
		yearLineDao.delete(entity);
	}

	@Override
	public YearLine getLatest(Object... params) {
		return yearLineDao.getLatest(params);
	}
}
