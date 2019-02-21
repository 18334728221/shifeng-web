package com.xwd.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.db.annotaion.DbSwitch;
import com.db.annotaion.DbSwitchType;
import com.frame.base.AbstractBaseService;
import com.frame.base.EntityDao;
import com.xwd.base.dao.CategoryPropertyDao;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.service.CategoryPropertyService;

@Component
@Transactional
@Aspect
@DbSwitch(type=DbSwitchType.ICH, constraint=false)
public class CategoryPropertyServiceImpl extends AbstractBaseService<CategoryProperty>
		implements CategoryPropertyService {

	@Autowired
	private CategoryPropertyDao categoryPropertyDao;

	public EntityDao<CategoryProperty, Long> getEntityDao() {
		return this.categoryPropertyDao;
	}

	@Override
	public int save(CategoryProperty entity) {
		return categoryPropertyDao.save(entity);
	}

	@Override
	public int update(CategoryProperty entity) {
		return categoryPropertyDao.update(entity);
	}

	@Override
	public int saveOrUpdate(CategoryProperty entity) {
		return categoryPropertyDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(CategoryProperty entity) {
		categoryPropertyDao.delete(entity);
	}

	// 产品属性组合
	public List<String> assemblePropertyId(Long categoryId) {
		List<String> propertyList = new ArrayList<String>();
		List<CategoryProperty> categoryList = this.findBy("categoryId", categoryId);

		Map<String, List<String>> typeMap = new HashMap<String, List<String>>();
		// 通过属性分组
		for (CategoryProperty bill : categoryList) {
			List<String> list = typeMap.get(bill.getName());
			if (list == null) {
				list = new ArrayList<String>();
				// list.add(bill.getValue());
				list.add(bill.getId().toString());
				typeMap.put(bill.getName(), list);
			} else {
				list.add(bill.getId().toString());
			}
		}

		List<String> temp = new ArrayList<String>();
		// 循环遍历MAP
		for (Entry<String, List<String>> entry : typeMap.entrySet()) {
			if (propertyList.isEmpty()) {
				propertyList.addAll(entry.getValue());
			} else {
				for (String o : propertyList) {
					for (String s : entry.getValue()) {
						temp.add(o + "," + s);
					}
				}

				propertyList.clear();
				propertyList.addAll(temp);
				temp.clear();
			}
		}

		return propertyList;
	}

	@Override
	public List<String> assemblePropertyName(Long categoryId) {
		List<String> propertyList = new ArrayList<String>();
		List<CategoryProperty> categoryList = this.findBy("categoryId", categoryId);

		Map<String, List<String>> typeMap = new HashMap<String, List<String>>();
		// 通过属性分组
		for (CategoryProperty bill : categoryList) {
			List<String> list = typeMap.get(bill.getName());
			if (list == null) {
				list = new ArrayList<String>();
				// list.add(bill.getValue());
				list.add(bill.getValue());
				typeMap.put(bill.getName(), list);
			} else {
				list.add(bill.getValue());
			}
		}

		List<String> temp = new ArrayList<String>();
		// 循环遍历MAP
		for (Entry<String, List<String>> entry : typeMap.entrySet()) {
			if (propertyList.isEmpty()) {
				propertyList.addAll(entry.getValue());
			} else {
				for (String o : propertyList) {
					for (String s : entry.getValue()) {
						temp.add(o + "," + s);
					}
				}

				propertyList.clear();
				propertyList.addAll(temp);
				temp.clear();
			}
		}

		return propertyList;
	}
	
	//产品属性组合
	public Map<String, Map<Long,String >> findCategoryProperty(Long categoryId){

		List<CategoryProperty> categoryList = this.findBy("categoryId", categoryId);

		Map<String, Map<Long,String >> typeMap = new HashMap<String, Map<Long,String >>();
		
		// 通过属性分组
		for (CategoryProperty bill : categoryList) {
			Map<Long,String > map = typeMap.get(bill.getName());
			if (map == null) {
				map = new HashMap<Long,String >();
				// list.add(bill.getValue());
				map.put(bill.getId(),bill.getValue());
				typeMap.put(bill.getName(), map);
			} else {
				map.put(bill.getId(),bill.getValue());
			}
		}
		return typeMap;

	}

}
