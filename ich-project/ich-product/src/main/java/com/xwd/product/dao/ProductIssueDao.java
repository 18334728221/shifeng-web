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
public class ProductIssueDao extends BaseMyIbatisDao<ProductIssue, Long> {

	public Class<ProductIssue> getEntityClass() {
		return ProductIssue.class;
	}
	
	public int saveOrUpdate(ProductIssue entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<ProductIssue> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public List<ProductPurchase> findMemPurchase(ProductPurchase entity){
		return db().selectList(this.getEntityClass().getSimpleName() + ".findMemPurchase",entity);
	}
	
}
