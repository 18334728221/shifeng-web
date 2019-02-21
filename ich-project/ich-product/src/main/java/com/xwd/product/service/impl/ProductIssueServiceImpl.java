package com.xwd.product.service.impl;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.product.dao.ProductIssueDao;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.service.ProductIssueService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class ProductIssueServiceImpl extends AbstractBaseService<ProductIssue> implements ProductIssueService{

	@Autowired
	private ProductIssueDao productIssueDao;
	
	public EntityDao<ProductIssue,Long> getEntityDao() {
		return this.productIssueDao;
	}
	
	@Override
	public int save(ProductIssue entity) {
		return productIssueDao.save(entity);
	}

	@Override
	public int update(ProductIssue entity) {
		return productIssueDao.update(entity);
	}

	@Override
	public int saveOrUpdate(ProductIssue entity) {
		return productIssueDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(ProductIssue entity) {
		productIssueDao.delete(entity);
	}
	
	/**
	 * 可入库数量
	 */
	public Long findProductAmount(Long productCode) {
		Long amount = 0L;
		List<ProductIssue> productIssueList = productIssueDao.findBy("productCode",productCode);
		if(productIssueList.isEmpty()){
			for (ProductIssue productIssue : productIssueList) {
				amount += productIssue.getCirculatingStock();
			}
		}
		return amount;
	}
	
}
