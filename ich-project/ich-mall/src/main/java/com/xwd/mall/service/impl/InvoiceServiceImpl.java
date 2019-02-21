package com.xwd.mall.service.impl;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;

import com.xwd.mall.model.*;
import com.xwd.mall.dao.*;
import com.xwd.mall.service.*;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.MALL, constraint=false)
public class InvoiceServiceImpl extends AbstractBaseService<Invoice> implements InvoiceService{

	@Autowired
	private InvoiceDao invoiceDao;
	
	public EntityDao<Invoice,Long> getEntityDao() {
		return this.invoiceDao;
	}
	
	@Override
	public int save(Invoice entity) {
		return invoiceDao.save(entity);
	}

	@Override
	public int update(Invoice entity) {
		return invoiceDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Invoice entity) {
		return invoiceDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Invoice entity) {
		invoiceDao.delete(entity);
	}
	
}
