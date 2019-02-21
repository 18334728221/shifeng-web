package com.xwd.auth.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.auth.dao.AuthUserRelationDao;
import com.xwd.auth.model.AuthUserRelation;
import com.xwd.auth.service.AuthUserRelationService;


/**
 * @author ljl
 */

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthUserRelationServiceImpl extends AbstractBaseService<AuthUserRelation> implements AuthUserRelationService{

	@Autowired
	private AuthUserRelationDao authUserRelationDao;
	
	public EntityDao<AuthUserRelation,Long> getEntityDao() {
		return this.authUserRelationDao;
	}
	
	public boolean checkClosedCycle(Long userId, List<Long> userList){
		if(userId == null || userList == null || userList.isEmpty()){
			return false; 
		}
		List<AuthUserRelation> list = this.authUserRelationDao.findBy("userId", userId);
		if(list.isEmpty()){
			return false;
		}
		return true;
	}
	
	public void deleteByUserId(Long userId) {
		this.authUserRelationDao.deleteByUserId(userId);
	}

	public List<AuthUserRelation> findUserRelationByUserId(Long userId) {
		return this.authUserRelationDao.findUserRelationByUserId(userId);
	}

	@Override
	public int save(AuthUserRelation entity) {
		return authUserRelationDao.save(entity);
	}

	@Override
	public int update(AuthUserRelation entity) {
		return authUserRelationDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthUserRelation entity) {
		return authUserRelationDao.save(entity);
	}

	@Override
	public void delete(AuthUserRelation entity) {
		authUserRelationDao.delete(entity);
	}
}
