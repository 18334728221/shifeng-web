package com.xwd.auth.service.impl;

import java.util.HashMap;
import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.dao.AuthGroupDao;
import com.xwd.auth.model.AuthGroup;
import com.xwd.auth.service.AuthGroupService;


/**
 * 
 * @author ljl
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AuthGroupServiceImpl extends AbstractBaseService<AuthGroup> implements AuthGroupService{

	@Autowired
	private AuthGroupDao authGroupDao;
	
	public EntityDao<AuthGroup,Long> getEntityDao() {
		return this.authGroupDao;
	}
	
	/**
	 * 根据用户获得对应的组
	 * @param userId
	 * @return
	 */
	public List<AuthGroup> findGroupsByUser(Long userId) {
		return authGroupDao.findGroupsByUser(userId);
	}
	
	public List<AuthGroup> findSubGroupsByGroup(Long groupId) {
		return this.authGroupDao.findSubGroupsByGroup(groupId);
	}
	
	
	public Page<AuthGroup> findExceptByGroupIdPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return authGroupDao.findExceptByGroupIdPageRequest(pageRequest);
	}

	@Override
	public int save(AuthGroup entity) {
		return authGroupDao.save(entity);
	}

	@Override
	public int update(AuthGroup entity) {
		return authGroupDao.update(entity);
	}

	@Override
	public int saveOrUpdate(AuthGroup entity) {
		return authGroupDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(AuthGroup entity) {
		authGroupDao.delete(entity);
	}
	
}
