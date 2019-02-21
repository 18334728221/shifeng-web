package com.xwd.product.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.model.ProductPurchase;


@Component
public class ProductPurchaseDao extends BaseMyIbatisDao<ProductPurchase, Long> {

	public Class<ProductPurchase> getEntityClass() {
		return ProductPurchase.class;
	}
	
	public int saveOrUpdate(ProductPurchase entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ProductPurchase> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 全部中签
	 * @return
	 */
	public int updateWithBidAll(Long productCode){
		return db().update(getEntityClass().getSimpleName() + ".updateWithBidAll", productCode);
	}

	/**
	 * 根据产品获得对应的申购总数
	 * @param paras
	 * @return
	 */
	public Long getPurchaseNumByCode(Object... paras){
		return db().selectOne(getEntityClass().getSimpleName() + ".getPurchaseNumByCode",map(paras));
	}
	
	/**
	 * 根据顾客获得申购总数
	 * @param paras
	 * @return
	 */
	public Long getPurchaseNumByCustomer(Object... paras){
		return db().selectOne(getEntityClass().getSimpleName() + ".getPurchaseNumByCustomer",map(paras));
	}
	
	/**
	 * 根据股票编号、顾客No's查找
	 * @param paras
	 * @return
	 */
	public List<ProductPurchase> findByCustomer(Object... paras){
		return db().selectList(getEntityClass().getSimpleName() + ".findByCustomer",map(paras));
	}
}
