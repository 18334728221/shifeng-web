package com.xwd.customer.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.customer.model.*;
import com.xwd.customer.dao.*;
import com.xwd.customer.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class FriendServiceImpl extends AbstractBaseService<Friend> implements FriendService{

	@Autowired
	private FriendDao friendDao;
	
	public EntityDao<Friend,Long> getEntityDao() {
		return this.friendDao;
	}
	
	@Override
	public int save(Friend entity) {
		return friendDao.save(entity);
	}

	@Override
	public int update(Friend entity) {
		return friendDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Friend entity) {
		return friendDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Friend entity) {
		friendDao.delete(entity);
	}
	
}
