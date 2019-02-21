package com.xwd.customer.service.impl;

import java.util.HashMap;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.customer.dao.CustomerProductDao;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.provider.CustomerProductProvider;
import com.xwd.customer.service.CustomerProductService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CustomerProductServiceImpl extends AbstractBaseService<CustomerProduct> implements CustomerProductService{

	@Autowired
	private CustomerProductDao customerProductDao;
	@Autowired
	private CustomerProductProvider customerProductProvider;
	
	public EntityDao<CustomerProduct,Long> getEntityDao() {
		return this.customerProductDao;
	}
	
	@Override
	public int save(CustomerProduct entity) {
		return customerProductDao.save(entity);
	}

	@Override
	public int update(CustomerProduct entity) {
		return customerProductDao.update(entity);
	}

	@Override
	public int saveOrUpdate(CustomerProduct entity) {
		return customerProductDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(CustomerProduct entity) {
		customerProductDao.delete(entity);
		//删除缓存
		this.customerProductProvider.delete(entity.getCustomerNo(), entity.getProductCode());
	}

	@Override
	public int updateSellNumAll(Object... paras) {
		int n = customerProductDao.updateSellNumAll(paras);
		this.initCache();
		return n;
	}

	@Override
	public int updatePrice(Object... paras) {
		return customerProductDao.updatePrice(paras);
	}

	@Override
	public void initCache() {
		PageRequest<HashMap<String, Object>> pageRequest = new PageRequest<HashMap<String, Object>>();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		pageRequest.setFilters(filters);
		int pageNumber = 1;
		pageRequest.setPageSize(100);
		Page<CustomerProduct> page;
		while (true) {
			pageRequest.setPageNumber(pageNumber);
			page = this.findByPageRequest(pageRequest);
			for(CustomerProduct entity : page.getResult()){
				this.customerProductProvider.update(entity, false);
			}
			if(pageNumber >= page.getLastPageNumber()){
				break;
			}
			pageNumber++;
		}
	}
	
}
