package com.xwd.securities.service.impl;

import java.util.HashMap;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.securities.model.*;
import com.xwd.securities.provider.StockProvider;
import com.xwd.securities.dao.*;
import com.xwd.securities.service.*;

@Component
@Transactional
@Aspect
public class StockServiceImpl extends AbstractBaseService<Stock> implements StockService{

	@Autowired
	private StockDao stockDao;
	@Autowired
	private StockProvider stockProvider;
	
	public EntityDao<Stock,Long> getEntityDao() {
		return this.stockDao;
	}
	
	@Override
	public int save(Stock entity) {
		return stockDao.save(entity);
	}

	@Override
	public int update(Stock entity) {
		return stockDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Stock entity) {
		return stockDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Stock entity) {
		stockDao.delete(entity);
	}

	@Override
	public void initCache() {
		stockProvider.clear();
		PageRequest<HashMap<String, Object>> pageRequest = new PageRequest<HashMap<String, Object>>();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		pageRequest.setFilters(filters);
		int pageNumber = 1;
		pageRequest.setPageSize(100);
		Page<Stock> page;
		while (true) {
			pageRequest.setPageNumber(pageNumber);
			page = this.findByPageRequest(pageRequest);
			for(Stock entity : page.getResult()){
				entity.init();
				this.stockProvider.save(entity);
			}
			if(pageNumber >= page.getLastPageNumber()){
				break;
			}
			pageNumber++;
		}
	}
	
}
