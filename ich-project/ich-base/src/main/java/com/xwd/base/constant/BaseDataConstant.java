package com.xwd.base.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xwd.base.model.Bank;
import com.xwd.base.model.Category;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.model.EmailServer;
import com.xwd.base.model.ImageServer;
import com.xwd.base.model.Mq;
import com.xwd.base.model.MqTheme;
import com.xwd.dd.model.Area;

/**
 * 数据库base_开头或dd_的表数据存储到内存 提供查询速度
 * 
 * @author linjinliang
 * 
 */
public class BaseDataConstant {

	// 地区key=platMark,value=entity
	public static Map<Long, Area> DD_AREA_MAP = new HashMap<Long, Area>();
	//银行
	public static Map<String, Bank> DD_BANK_MAP = new HashMap<String, Bank>();
	// 省
	public static List<Area> DD_PROVINCE_AREA_LIST = new ArrayList<Area>();
	// 产品分类
	public static Map<Long, Category> BASE_CATEGORY_MAP = new HashMap<Long, Category>();
	// 产品分类属性
	public static Map<Long, CategoryProperty> BASE_CATEGORY_PROPERTY_MAP = new HashMap<Long, CategoryProperty>();
	//产品属性
	public static Map<Long, List<CategoryProperty>> BASE_CATEGORY_PROPERTY_LIST_MAP = new HashMap<Long, List<CategoryProperty>>();
	// 消息队列服务器
	public static Map<Long, Mq> BASE_MQ_MAP = new HashMap<Long, Mq>();
	// 消息队列主题
	public static Map<Long, MqTheme> BASE_MQ_THEME_MAP = new HashMap<Long, MqTheme>();
	// 邮件服务器
	public static Map<Long, EmailServer> BASE_EMAIL_SERVER_MAP = new HashMap<Long, EmailServer>();
	// 图片服务器
	public static Map<Long, ImageServer> BASE_IMAGE_SERVER_MAP = new HashMap<Long, ImageServer>();
	
	/**
	 * 根据图片服务器类型获得图片服务器
	 * 
	 * @param serverType
	 *            在实体类ImageServer里面定义
	 * @return
	 */
	public static ImageServer getImageServer(int serverType) {
		for (Entry<Long, ImageServer> entry : BASE_IMAGE_SERVER_MAP.entrySet()) {
			if (entry.getValue().getServerType().intValue() == serverType) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public static ImageServer getImageServer(Long id) {
		for (Entry<Long, ImageServer> entry : BASE_IMAGE_SERVER_MAP.entrySet()) {
			if (entry.getValue().getId() == id) {
				return entry.getValue();
			}
		}
		return null;
	}
}