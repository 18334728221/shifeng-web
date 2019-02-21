package com.xwd.customer.dao;

import org.springframework.stereotype.Component;

import com.xwd.customer.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class CustomerProductDao extends BaseMyIbatisDao<CustomerProduct, Long> {

	public Class<CustomerProduct> getEntityClass() {
		return CustomerProduct.class;
	}
	
	public int saveOrUpdate(CustomerProduct entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<CustomerProduct> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 根据股票编号批量更新可卖数量
	 * 每天早上股票初始化的时候
	 * @param paras
	 * @return
	 */
	public int updateSellNumAll(Object... paras){
		return db().update(this.getEntityClass().getSimpleName() + ".updateSellNumAll", map(paras));
	}
	
	/**
	 * 根据股票编号批量更新用户持仓的最新价格
	 * @param paras
	 * @return
	 */
	public int updatePrice(Object... paras){
		return db().update(this.getEntityClass().getSimpleName() + ".updatePrice", map(paras));
	}
}
