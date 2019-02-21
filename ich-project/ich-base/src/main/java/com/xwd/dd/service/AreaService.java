package com.xwd.dd.service;

import java.util.List;

import com.frame.base.BaseService;
import com.xwd.dd.model.Area;

/**
 * 
 * @author ljl
 *
 */
public interface AreaService extends BaseService<Area> {

	public int save(Area entity);

	public int update(Area entity);

	public int saveOrUpdate(Area entity);
	
	public void delete(Area entity);
	
	/**
	 * 将地区list数据转化成JSON对象
	 * 
	 * @return
	 */
	public String treeJson();

	/**
	 * 
	 * 将地区list数据转化成JSON对象
	 */
	public String listAllTree();

	/**
	 * 根据父Id查找子地区中标识码最大数据
	 * 
	 * @param parentId
	 * @return
	 */
	public Long findByMaxPlatMark(Long parentId);

	/**
	 * @ 查询某平台下所有下一级地区 zhangxt
	 */
	public List<Area> selectAreaByParentId(String parentId);

	/**
	 * @ 获取当前平台标示的id zhangxt
	 */
	public Area getParentId(String platMark);

	/**
	 * @ 查询某平台下所有地区
	 * 
	 */
	public List<Area> selectAreaByPlatMark(Long platMark, Long nextPlatMark);

	/**
	 * 重置地区树
	 * 
	 * @param resetStatus
	 */
	public void updateAreaList(String status);

	/**
	 * 设置显示地区树
	 * 
	 * @param list
	 */
	public void updateAreaIds(List<String> list);

	/**
	 * 根据平台标识更新
	 * 
	 * @param paras
	 */
	public void updateAreaByPlatmark(Object... paras);

	public Area findAreaByParentId(Long parentId);

	/**
	 * 查找平台所在的地区
	 * 
	 * @return
	 */
	public Area findPlatformByArea();

}
