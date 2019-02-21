package com.xwd.base.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.EmailServerDao;
import com.xwd.base.model.EmailServer;
import com.xwd.base.service.EmailServerService;


/**
 * @author ljl
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class EmailServerServiceImpl extends AbstractBaseService<EmailServer> implements EmailServerService{
	
	private static EmailServer emailServer = null;

	@Autowired
	private EmailServerDao emailServerDao;
	
	public EntityDao<EmailServer,Long> getEntityDao() {
		return this.emailServerDao;
	}
	
	@Override
	public int save(EmailServer entity) {
		return emailServerDao.save(entity);
	}

	@Override
	public int update(EmailServer entity) {
		return emailServerDao.update(entity);
	}

	@Override
	public int saveOrUpdate(EmailServer entity) {
		return emailServerDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(EmailServer entity) {
		emailServerDao.delete(entity);
	}

	@Override
	public EmailServer get() {
		if(emailServer == null){
			List<EmailServer> list = emailServerDao.findAll();
			if(!list.isEmpty()){
				emailServer = list.get(0);
			}
		}
		return emailServer;
	}
}
