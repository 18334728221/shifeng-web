package com.xwd.dd.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.base.BaseMyIbatisDao;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.dd.model.Area;

/**
 * @author sf
 */
@Component
public class AreaDao extends BaseMyIbatisDao<Area, Long> {

	public Class<Area> getEntityClass() {
		return Area.class;
	}
	
	public int saveOrUpdate(Area entity) {
		if (entity.getId() == null) { 
			return save(entity);
		} else {
			return update(entity);
		}
	}
	
	public Page<Area> findByPageRequest(PageRequest<HashMap<String, Object>> pageRequest) {
		return pageQuery("pageSelect", pageRequest);
	}
	

	/**
	 * 根据父Id查找子地区中标识码最大数据
	 * @param parentId
	 * @return
	 */
	public Long findByMaxPlatMark(Long parentId) {
		return db().selectOne(getEntityClass().getSimpleName() + ".findByMaxPlatMark", parentId);
	}
 
	/**
	 *  
	 * 查询地区
	 * */
	public List<Area> selectAreaByParentId(String parentId) {
		return db().selectList(getEntityClass().getSimpleName() + ".selectAreaByParentId", parentId);
	}
	
	/**
	 *  
	 * 查询某平台下地下一级所有地区
	 * */
	public Area getParentId(String platMark){
		return db().selectOne(getEntityClass().getSimpleName() + ".getAreaByPlatMark",platMark);
	}
	
	/**
	 * @author zhangxt 
	 * 查询某平台下地所有地区
	 * */
	public List<Area> selectAreaByPlatMark(Object ... paras){
		return db().selectList(getEntityClass().getSimpleName() + ".selectAreaByPlatMark", map(paras));
	}
	
	public List<Area> selectAreaByPlatMark2(Object ... paras){
		return db().selectList(getEntityClass().getSimpleName() + ".selectAreaByPlatMark2", map(paras));
	}
	/**
	 * 重置地区树
	 * @param resetStatus 
	 */
	public void updateAreaList(String status) {
		db().update(getEntityClass().getSimpleName() + ".updateAreaList",status);
	}
	
	
	/**
	 * 根据平台标识更新
	 * @param paras
	 */
	public void updateAreaByPlatmark(Object ...paras){
		db().update(getEntityClass().getSimpleName() + ".updateAreaByPlatmark", map(paras));
	}

	/**
	 * 设置显示地区树
	 * @param list
	 */
	public void updateAreaIds(List<String> list) {
		db().update(getEntityClass().getSimpleName() + ".updateAreaIds",list);
	}

	public Area findAreaByParentId(Long parentId) {
		return db().selectOne(getEntityClass().getSimpleName() + ".findAreaByParentId", parentId);
	}

	public Area findPlatformByArea() {
		return db().selectOne(getEntityClass().getSimpleName() + ".findPlatformByArea");
	}


}
