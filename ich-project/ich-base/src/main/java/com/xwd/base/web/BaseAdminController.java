package com.xwd.base.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.auth.spring.util.SpringContextUtil;
import com.frame.base.web.BaseSpringController;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.frame.util.CalendarUtils;
import com.frame.util.DateUtils;
import com.xwd.base.util.BaseDataUtil;
import com.xwd.bean.GridDataModel;

public class BaseAdminController extends BaseSpringController {

	protected static final String DEFAULT_SORT_COLUMNS = " id asc";
	protected static final String FAILURE = "failure";
	protected static final String SUCCESS = "success";
	
	/**
	 * 手机端分页
	 * @param request
	 * @return
	 */
	public PageRequest<HashMap<String, Object>> setMobilePageValue(HttpServletRequest request) {
		int pageNumber = 1;
		int pageSize = 15;
		PageRequest<HashMap<String, Object>> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		String startStr = request.getParameter("pageNumber").trim();
		String limitStr = request.getParameter("pageSize").trim();
		if(startStr != null){
			pageNumber = Integer.valueOf(startStr);
		}
		if(limitStr != null){
			pageSize = Integer.valueOf(limitStr);
		}
		pageRequest.setPageSize(pageSize);
		pageRequest.setPageNumber(pageNumber);
		return pageRequest;
	}

	/**
	 * 设置分页信息：使用默认参数名“limit, start”生成PageRequest<Map>对象
	 * 
	 * @param request
	 * @return
	 */
	public PageRequest<HashMap<String, Object>> setPageValue(HttpServletRequest request) {
		int start = 20;
		int limit = 20;
		PageRequest<HashMap<String, Object>> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		
		String obj = get("aoData");
		if(obj == null){
			String startStr = request.getParameter("start").trim();
			String limitStr = request.getParameter("limit").trim();
			if(startStr != null){
				start = Integer.valueOf(startStr);
			}
			if(limitStr != null){
				limit = Integer.valueOf(limitStr);
			}
		}else{
			obj = obj.replaceAll("&quot;", "\"");
			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				List<LinkedHashMap> list = mapper.readValue(obj, List.class);
				for(LinkedHashMap bean : list){
					if("iDisplayLength".equals((String)bean.get("name"))){
						limit = (Integer)bean.get("value");
					}else if("iDisplayStart".equals((String)bean.get("name"))){
						start = (Integer)bean.get("value");
					} 
				}

				//设置查询条件
				obj = get("sParam");
				obj = obj.replaceAll("&quot;", "\"");
				if(obj != null){
					list = mapper.readValue(obj, List.class);
					for(LinkedHashMap bean : list){
						pageRequest.getFilters().put((String)bean.get("name"), bean.get("value"));
					}
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		int pageNumber = 1;
		pageNumber = start / limit + 1;
		pageRequest.setPageSize(limit);
		pageRequest.setPageNumber(pageNumber);
		return pageRequest;
	}
	
	/**
	 * 将对象转换为JSON格式字节数组
	 * 
	 * @author lipw
	 * @date	2014年8月29日上午11:26:46
	 * @param object
	 * 需要转换为JSON格式字节数组的对象
	 * @return
	 * JSON格式字节数组
	 */
	private byte[] getJson(Object object) {
		byte[] jsonByte = new byte[] { }; 
		try {
			if (object != null) {
				if (object instanceof byte[]) {
					jsonByte = ((byte[]) object);
				} else if (object instanceof String) {
					jsonByte = (object.toString().getBytes("utf-8"));
				} else{
					ObjectMapper mapper = new ObjectMapper();
					jsonByte = mapper.writeValueAsString(object).getBytes("utf-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonByte;
	}
	
	/**
	 * 将N个对象转换为JSON格式存入一个字节数组
	 * 
	 * @author lipw
	 * @date	2014年8月29日下午12:30:52
	 * @param objects
	 * 需要转换为JSON格式字节数组的对象
	 * @return
	 * JSON格式字节数组
	 */
	public byte[] getJson(Object ... objects) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		Integer i = 0;
		for (Object object : objects){
			try {
				if (i != 0){
					baos.write(new byte[] { ',' });
				}
				baos.write(getJson(object));
				i++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	/**
	 * 将传入的Object对象转换为JSON格式字符串作为响应内容以“utf-8”编码输出
	 * 
	 * @param response
	 * @param object
	 */
	public void outJson(HttpServletResponse response, Object ... objects) {
		// 设置响应内容编码，解决直接在浏览器地址栏访问中文内容乱码的问题
		response.setCharacterEncoding("utf-8");
		// 设置响应内容类型
		response.setContentType("text/json;charset=UTF-8");
		try {
			OutputStream out = response.getOutputStream();
			Integer i = 0;
			for (Object object : objects){
				try {
					if (i != 0){
						out.write(new byte[] { ',' });
					}
					out.write(getJson(object));
					i++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将传入的Object对象转换为JSON格式字符串作为响应内容以“utf-8”编码输出
	 * 
	 * @param response
	 * @param object
	 */
	public void outJson(HttpServletResponse response, String json) {
		// 设置响应内容编码，解决直接在浏览器地址栏访问中文内容乱码的问题
		response.setCharacterEncoding("utf-8");
		// 设置响应内容类型
		response.setContentType("text/json;charset=UTF-8");
		
		try {
			OutputStream out = response.getOutputStream();
			out.write(json.getBytes("UTF-8"));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 输出Page json
	 * 
	 * @param response
	 * @param page
	 * @param isInitBaseFieldName 是否对base字段赋值
	 */
	public void outPageJson(HttpServletResponse response, Page page, boolean isInitBaseFieldName){
		if (response == null || page == null){
			return;
		}
		
		if(isInitBaseFieldName){
			BaseDataUtil.setBaseFieldName(page.getResult());
		}
		GridDataModel model = new GridDataModel();
		model.setAaData(page.getResult());
		model.setiTotalRecords(page.getTotalCount());
		model.setiTotalDisplayRecords(page.getTotalCount());
		this.outJson(response, model);
	}
	
	/**
	 * 输出成功json {result: 'success'}
	 * 
	 * @param response
	 * @throws IOException
	 */
	public void outSuccessJson(HttpServletResponse response) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", SUCCESS);
		this.outJson(response, map);
	}

    /**
     * 输出成功json {result: 'success', data:{}}
     *
     * @param response
     * @param data 数据
     * @throws IOException
     */
	public void outSuccessJson(HttpServletResponse response, Object data) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", SUCCESS);
		map.put("data", data);
		this.outJson(response, map);
	}

	/**
	 * 输出失败json {result: 'failure', error: '' }
	 * 
	 * @param response
	 * @throws IOException
	 */
	public void outFailureJson(HttpServletResponse response) throws IOException {
		outFailureJson(response, "");
	}

	/**
	 * 输出失败json {result: 'failure', error: errMsg }
	 * @param response
	 * @param errMsg
	 * @throws IOException
	 */
	public void outFailureJson(HttpServletResponse response, String errMsg) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", FAILURE);
		map.put("error", errMsg);
		this.outJson(response, map);
	}
	/**
	 * json输出 {result:result} json格式的结果
	 * 
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public void outResultJson(HttpServletResponse response, Object obj)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", obj);
		this.outJson(response, map);
	}

	/**
	 * 返回单个实体对象并对其关联表中的name赋值
	 * @param response
	 * @param obj
	 * @param isInitBaseFieldName
	 * @throws IOException
	 */
	public void outResultJson(HttpServletResponse response, Object obj,Boolean isInitBaseFieldName)
			throws IOException {
		if(isInitBaseFieldName){
			BaseDataUtil.setFieldName(obj);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", obj);
		this.outJson(response, map);
	}
	
	 /**
	  * 国际化的json输出
	  * {result:message}
	  * 输出result:result json格式的结果
	  * 对应文件message.properties
	  * message_zh.properties
	  * @param response
	  * @param messageKey
	  * @throws IOException
	  */
	 public void outResultJsoni18n(HttpServletResponse response, String messageKey) throws IOException{
		 Map<String,String> map = new HashMap<String,String>();
		 String str = SpringContextUtil.getMessage(messageKey);
		 map.put("result", str);
		 map.put("error", str);
		 this.outJson(response, map);
	 }
	 
	 public void outResultJsoni18n(HttpServletResponse response, String messageKey, String elementName) throws IOException{
		 Map<String,String> map = new HashMap<String,String>();
		 map.put(elementName, SpringContextUtil.getMessage(messageKey));
		 this.outJson(response, map);
	 }
	 
	 /**
	  * 国际化的json输出
	  * {result:message}
	  * 输出result:result json格式的结果
	  * 对应文件message.properties
	  * message_zh.properties
	  * @param response
	  * @param messageKey 
	  * @param args
	  * @param locale 传人null,则为中文
	  * @throws IOException
	  */
	 public void outResultJsoni18n(HttpServletResponse response, String messageKey, Object[] args, Locale locale) throws IOException{
		 Map<String,String> map = new HashMap<String,String>();
		 if(locale == null){
			 locale = new Locale("zh","CN");
		 }
		 String str = SpringContextUtil.getMessage(messageKey, args, locale);
		 map.put("result", str);
		 map.put("error", str);
		 this.outJson(response, map);
	 }

	/**
	 * 获得Timestamp类型的参数 如果不存在 则返回当前时间
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public Timestamp getTimestamp(HttpServletRequest request, String name) {
		String date = get(name);
		if (StringUtils.isNotBlank(date)) {
			if(date.length() < 11){
				date += " 00:00:00";
			}
			return Timestamp.valueOf(date);
		}
		return Timestamp.valueOf(DateUtils.getStringDate());
	}
	
	public Calendar getCalendar(HttpServletRequest request, String name) {
		String date = get(name);
		if (StringUtils.isNotBlank(date)) {
			return CalendarUtils.convertBeginCalendar(date);
		}
		return Calendar.getInstance();
	}

	/**
	 * 把request对应的参数赋值到对象中
	 * 赋值字段类型：String、Long、Integer、Double、Float、Timestamp、Boolean（1：true 0：false）
	 * 
	 * @param object
	 * @param request
	 * @param isEdit
	 *            是否编辑模式，如果编辑模式，则忽略null或不存在对应字段的赋值
	 */
	public void setFieldValues(Object object, HttpServletRequest request, boolean isEdit) {
		if (object == null)
			return;
		Field[] fields = object.getClass().getDeclaredFields();
		String simpleName = "";
		try {
			for (Field f : fields) {
				if (f.getModifiers() > 2) {
					continue;
				}
				f.setAccessible(true);
				simpleName = f.getType().getSimpleName();
				if (simpleName.equals("String")) {
					if (this.get(f.getName()) == null && isEdit) {
						continue;
					}
					f.set(object, this.get(f.getName()));
					continue;
				} else if (simpleName.equals("Long")) {
					if (this.getLong(f.getName()) == null && isEdit) {
						continue;
					}
					f.set(object, this.getLong(f.getName()));
					continue;
				} else if (simpleName.equals("Integer")) {
					if (this.getInteger(f.getName()) == null && isEdit) {
						continue;
					}
					f.set(object, this.getInteger(f.getName()));
					continue;
				} else if (simpleName.equals("Boolean")) {
					if (this.get(f.getName()) == null && isEdit) {
						continue;
					}
					Boolean bool = ("1".equals(this.get(f.getName())) || "true".equals(this.get(f.getName())));
					f.set(object, bool);
					continue;
				} else if (simpleName.equals("Timestamp")) {
					if (StringUtils.isBlank(this.get(f.getName())) && isEdit) {
						continue;
					}
					f.set(object, this.getTimestamp(request, f.getName()));
					continue;
				} else if (simpleName.equals("Calendar")) {
					if (StringUtils.isBlank(this.get(f.getName())) && isEdit) {
						continue;
					}
					f.set(object, this.getCalendar(request, f.getName()));
					continue;
				} else if (simpleName.equals("Double")) {
					if (StringUtils.isNotBlank(this.get(f.getName()))) {
						f.set(object, Double.valueOf(this.get(f.getName())));
					}
					continue;
				} else if (simpleName.equals("Float")) {
					if (StringUtils.isNotBlank(this.get(f.getName()))) {
						f.set(object, Float.valueOf(this.get(f.getName())));
					}
					continue;
				}else if (simpleName.equals("Byte")) {
					if (StringUtils.isNotBlank(this.get(f.getName()))) {
						f.set(object, Byte.valueOf(this.get(f.getName())));
					}
					continue;
				}else if (simpleName.equals("BigDecimal")) {
					if (StringUtils.isNotBlank(this.get(f.getName()))) {
						f.set(object, BigDecimal.valueOf(Double.valueOf(this.get(f.getName()))));
					}
					continue;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param object
	 * @param isIgnoreNull
	 * @return
	 */
	public Map getPropertiesMap(Object bean, boolean isIgnoreNull) {
		Map map = new HashMap();
		if (bean == null)
			return map;
		Field[] fields = bean.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		Object obj = null;
		for (int i = 0; i < fields.length; i++) {
			try {
				obj = fields[i].get(bean);
				if (isIgnoreNull && obj == null) {
					continue;
				}
				map.put(fields[i].getName(), obj);
			} catch (IllegalArgumentException e) {
				log.error(e.getMessage());
			} catch (IllegalAccessException e) {
				log.error(e.getMessage());
			}
		}
		return map;
	}

	/**
	 * 根据属性名获得对应的属性值
	 * 
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	public Object getProperty(Object bean, String fieldName) {
		Field[] fields = bean.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		Object obj = null;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (fieldName.equals(field.getName())) {
				try {
					obj = field.get(bean);
				} catch (IllegalArgumentException e) {
					log.error(e.getMessage());
				} catch (IllegalAccessException e) {
					log.error(e.getMessage());
				}
			}
		}
		return obj;
	}
	
	/**
	 * 以json形式响应移动端结果
	 * @param response
	 * @param code
	 * @param error
	 * @param result
	 */
	public void outMobileJson(HttpServletResponse response, Object code, Object error, Object result){
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("code", code);
		obj.put("error", error);
		obj.put("result", result);
		this.outJson(response, obj);
	}
	
	/**
	 * 获取request传来的为空的参数名
	 * @param request
	 * @return
	 */
	public String getNullParamName(HttpServletRequest request){
		for (String s : request.getParameterMap().keySet()) {
			String value = request.getParameter(s);
			if(null == value || "".equals(value.trim())){
				return s;
			}
		}
		return null;
	}
}
