package com.xwd.mall.dao;

import org.springframework.stereotype.Component;

import com.xwd.mall.model.*;
import java.util.HashMap;
import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class GoodsOrderDao extends BaseMyIbatisDao<GoodsOrder, Long> {

	public Class<GoodsOrder> getEntityClass() {
		return GoodsOrder.class;
	}
	
	public int saveOrUpdate(GoodsOrder entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<GoodsOrder> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public int updateOrderStatus(GoodsOrder entity){
		return db().update(getEntityClass().getSimpleName() +".updateOrderStatus",entity);
	}
}
