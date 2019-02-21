package com.xwd.auth.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.auth.dao.AuthUserDetailDao;
import com.xwd.auth.model.AuthUserDetail;
import com.xwd.auth.service.AuthUserDetailService;



/**
 * @author ljl
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthUserDetailServiceImpl extends AbstractBaseService<AuthUserDetail> implements AuthUserDetailService{

	@Autowired
	private AuthUserDetailDao authUserDetailDao;
	
	public EntityDao<AuthUserDetail,Long> getEntityDao() {
		return this.authUserDetailDao;
	}
	
	@Override
	public int save(AuthUserDetail entity) {
		return authUserDetailDao.save(entity);
	}

	@Override
	public int update(AuthUserDetail entity) {
		return authUserDetailDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthUserDetail entity) {
		return authUserDetailDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthUserDetail entity) {
		authUserDetailDao.delete(entity);
	}
	
}
