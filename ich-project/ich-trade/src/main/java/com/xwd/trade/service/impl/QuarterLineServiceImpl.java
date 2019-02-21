package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.QuarterLineDao;
import com.xwd.trade.model.QuarterLine;
import com.xwd.trade.service.QuarterLineService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class QuarterLineServiceImpl extends AbstractBaseService<QuarterLine> implements QuarterLineService{

	@Autowired
	private QuarterLineDao quarterLineDao;
	
	public EntityDao<QuarterLine,Long> getEntityDao() {
		return this.quarterLineDao;
	}
	
	@Override
	public int save(QuarterLine entity) {
		return quarterLineDao.save(entity);
	}

	@Override
	public int update(QuarterLine entity) {
		return quarterLineDao.update(entity);
	}

	@Override
	public int saveOrUpdate(QuarterLine entity) {
		return quarterLineDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(QuarterLine entity) {
		quarterLineDao.delete(entity);
	}
	
	@Override
	public QuarterLine getLatest(Object... params) {
		return quarterLineDao.getLatest(params);
	}
}
