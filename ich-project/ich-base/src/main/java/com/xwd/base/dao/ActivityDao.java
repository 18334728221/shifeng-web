package com.xwd.base.dao;

import org.springframework.stereotype.Component;

import com.xwd.base.model.*;
import java.util.HashMap;
import java.util.List;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;


@Component
public class ActivityDao extends BaseMyIbatisDao<Activity, Long> {

	public Class<Activity> getEntityClass() {
		return Activity.class;
	}
	
	public int saveOrUpdate(Activity entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Activity> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	
	/**
	 * 查看活动列表
	 */
	public List<Activity> findActivity(){
		return db().selectList(getEntityClass().getSimpleName() + ".findActivity");
	}
	
	/**
	 * 查看最新活动
	 */
	public List<Activity> findIsNew(){
		return db().selectList(getEntityClass().getSimpleName() + ".findIsNew");
	}
	
	/**
	 * 查询栏目id
	 */
	public Long findByAcitvId(Long activId){
		return db().selectOne("Activity.queryCategoryId",activId); 
	}
	
}
