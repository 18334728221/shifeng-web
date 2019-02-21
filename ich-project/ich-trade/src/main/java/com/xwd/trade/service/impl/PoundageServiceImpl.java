package com.xwd.trade.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.trade.dao.PoundageDao;
import com.xwd.trade.model.Poundage;
import com.xwd.trade.service.PoundageService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class PoundageServiceImpl extends AbstractBaseService<Poundage> implements PoundageService{

	@Autowired
	private PoundageDao poundageDao;
	
	public EntityDao<Poundage,Long> getEntityDao() {
		return this.poundageDao;
	}
	
	@Override
	public int save(Poundage entity) {
		return poundageDao.save(entity);
	}

	@Override
	public int update(Poundage entity) {
		return poundageDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Poundage entity) {
		return poundageDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Poundage entity) {
		poundageDao.delete(entity);
	}
	
}
