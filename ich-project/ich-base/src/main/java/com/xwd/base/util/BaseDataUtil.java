package com.xwd.base.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Category;
import com.xwd.dd.model.Area;


public class BaseDataUtil {

	private BaseDataUtil() {

	}

	public final static Set<String> FIELD_CONSTANT_SET = new HashSet<String>();//需要操作的字段名称
	
	static{
		FIELD_CONSTANT_SET.add("userType");
		FIELD_CONSTANT_SET.add("platMark");
	}

	/**
	 * 对引用base_table开头的表赋予对应的字段名称 引用规则: 字段必须命名tableId(exclude base_ ,e.g areaPlatMark)
	 * 字段必须含有tableName(exclude base_,e.g areaName)
	 * 
	 * @param list
	 */
	public synchronized static void setBaseFieldName(List<Object> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (Object object : list) {
			setFieldName(object);
		}
	}

	/**
	 * 对引用base_table开头的表赋予对应的字段名称 引用规则: 字段必须命名tableId(exclude base_ ,e.g areaPlatMark)
	 * 字段必须含有tableName(exclude base_,e.g areaName)
	 * @param object
	 */
	public static void setFieldName(Object object) {
		if (object == null)
			return;
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = object.getClass().getDeclaredFields();
		try {
			for (Field f : fields) {
				if (f.getName().endsWith("Id")) {
					f.setAccessible(true);
					if (f.getName().contains("categoryId")) {
						Category obj = BaseDataConstant.BASE_CATEGORY_MAP.get(f.get(object));
						if (obj != null) {
							map.put("categoryName", obj.getName());
						}
						continue;
					} 
				} else if (FIELD_CONSTANT_SET.contains(f.getName())) {
					f.setAccessible(true);
					if (f.getName().contains("platMark")) {
						Area obj = BaseDataConstant.DD_AREA_MAP.get(f.get(object));
						if (obj != null) {
							map.put("areaName", obj.getAreaName());
						}
						continue;
					}
				} 
			}
			for (Field f : fields) {
				if (map.containsKey(f.getName())) {
					f.setAccessible(true);
					f.set(object, map.get(f.getName()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
