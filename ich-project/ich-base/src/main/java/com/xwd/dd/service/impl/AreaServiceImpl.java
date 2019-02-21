package com.xwd.dd.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.frame.tree.JsTree;
import com.frame.tree.TreeJson;
import com.frame.tree.TreeNode;
import com.xwd.dd.dao.AreaDao;
import com.xwd.dd.model.Area;
import com.xwd.dd.service.AreaService;

/**
 * 
 * @author Administrator
 *
 */
@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class AreaServiceImpl extends AbstractBaseService<Area> implements AreaService{

	@Autowired
	private AreaDao areaDao;

	public EntityDao<Area, Long> getEntityDao() {
		return this.areaDao;
	}

	/**
	 * 将地区list数据转化成JSON对象
	 * 
	 * @return
	 */
	public String treeJson() {
		List<Area> list = areaDao.findAll();
		List<TreeJson> listJson = new ArrayList<TreeJson>();
		for (Area baseArea : list) {
			TreeJson treeJson = new TreeJson();
			treeJson.setId(baseArea.getId());
			treeJson.setText(baseArea.getAreaName());
			treeJson.setPid(baseArea.getParentId());
			listJson.add(treeJson);
		}
		List<TreeJson> treeJsonList = TreeJson.formatTree(listJson);
		ObjectMapper mapper = new ObjectMapper();  
		try {
			String json = mapper.writeValueAsString(treeJsonList);
			json = json.replaceAll("'", "\"");
			return "[" + json.substring(2, json.length() - 2) + "]";
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @Author lipw
	 * 
	 *         将地区list数据转化成JSON对象
	 */
	public String listAllTree() {
		List<String> listNode = new ArrayList<String>();
		try {
			List<Area> list = areaDao.findAll();
			HashMap<Long, TreeNode> nodeList = new HashMap<Long, TreeNode>();
			for (Area baseArea : list) {
				TreeNode node = new TreeNode();
				node.setId(baseArea.getId());
				node.setPId(baseArea.getParentId());
				node.setName(baseArea.getAreaName().toString());
				node.setTreeCode(baseArea.getAreaCode());
				node.setPlatMark(baseArea.getPlatMark());
				node.setLevel(baseArea.getLevel());
				nodeList.put(node.getId(), node);
			}

			listNode = JsTree.getJonsTree2(nodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();  
		try {
			String json = mapper.writeValueAsString(listNode);
			json = json.replaceAll("'", "\"");
			return "[" + json.substring(2, json.length() - 2) + "]";
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据父Id查找子地区中标识码最大数据
	 * 
	 * @param parentId
	 * @return
	 */
	public Long findByMaxPlatMark(Long parentId) {
		return areaDao.findByMaxPlatMark(parentId);
	}

	public List<Area> selectAreaByParentId(String parentId) {
		return areaDao.selectAreaByParentId(parentId);
	}

	public Area getParentId(String platMark) {
		return areaDao.getParentId(platMark);
	}

	public List<Area> selectAreaByPlatMark(Long platMark, Long nextPlatMark) {
		return areaDao.selectAreaByPlatMark("platMark", platMark, "nextPlatMark", nextPlatMark);
	}

	/**
	 * 重置地区树
	 * 
	 * @param resetStatus
	 */
	public void updateAreaList(String status) {
		areaDao.updateAreaList(status);
	}

	/**
	 * 设置显示地区树
	 * 
	 * @param list
	 */
	public void updateAreaIds(List<String> list) {
		areaDao.updateAreaIds(list);
	}

	/**
	 * 根据平台标识更新
	 * 
	 * @param paras
	 */
	public void updateAreaByPlatmark(Object... paras) {
		this.areaDao.updateAreaByPlatmark(paras);
	}

	public Area findAreaByParentId(Long parentId) {
		return areaDao.findAreaByParentId(parentId);
	}

	/**
	 * 查找平台所在的地区
	 * 
	 * @return
	 */
	public Area findPlatformByArea() {
		return areaDao.findPlatformByArea();
	}
	@Override
	public int save(Area entity) {
		return areaDao.save(entity);
	}

	@Override
	public int update(Area entity) {
		return areaDao.update(entity);
	}

	@Override
	public int saveOrUpdate(Area entity) {
		return areaDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Area entity) {
		areaDao.delete(entity);
	}

}
