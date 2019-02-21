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
import com.xwd.product.dao.SkuDao;
import com.xwd.product.model.Sku;
import com.xwd.product.service.SkuService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class SkuServiceImpl extends AbstractBaseService<Sku> implements SkuService{

	@Autowired
	private SkuDao skuDao;
	
	public EntityDao<Sku,Long> getEntityDao() {
		return this.skuDao;
	}
	
	@Override
	public int save(Sku entity) {
		return skuDao.save(entity);
	}

	@Override
	public int update(Sku entity) {
		return skuDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Sku entity) {
		return skuDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Sku entity) {
		skuDao.delete(entity);
	}

	/**
	 * 可入库数量
	 */
	public Long findProductAmount(Long productCode) {
		
		Long amount = 0L;
		List<Sku> SkuList = skuDao.findBy("productCode",productCode);
		if(SkuList != null && SkuList.size()>0){
			for (Sku sku : SkuList) {
				amount += sku.getTotalStock();
			}
		}
		return amount;
	}
	
	/**
	 * 查询最后一条sku
	 * productCode
	 */
	public Sku findLastSkuByProduct(Object... params){
		return skuDao.findLastSkuByProduct(params);
	}
	
}
