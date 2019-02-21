package com.xwd.seller.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.seller.model.Craftsman;


@Component
public class CraftsmanDao extends BaseMyIbatisDao<Craftsman, Long> {

	public Class<Craftsman> getEntityClass() {
		return Craftsman.class;
	}
	
	public int saveOrUpdate(Craftsman entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Craftsman> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	public Craftsman findLastCraftsman(){
		return db().selectOne("Craftsman.findLastCraftsman");
	}
	
	/**
	 * 根据手机号或者email获得用户
	 * @param params
	 * @return
	 */
	public Craftsman getCustomerByEmailOrMobile(Object ...params){
		return db().selectOne("Craftsman.getCustomerByEmailOrMobile", map(params));
	}
}
