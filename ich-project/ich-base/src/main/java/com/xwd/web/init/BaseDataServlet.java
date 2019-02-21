package com.xwd.web.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;

import com.auth.spring.util.SpringContextUtil;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.constant.AuthRoleConstant;
import com.xwd.auth.model.AuthRole;
import com.xwd.auth.service.AuthRoleService;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Bank;
import com.xwd.base.model.Category;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.model.EmailServer;
import com.xwd.base.model.ImageServer;
import com.xwd.base.model.Mq;
import com.xwd.base.model.MqTheme;
import com.xwd.base.service.CategoryPropertyService;
import com.xwd.base.service.CategoryService;
import com.xwd.base.service.EmailServerService;
import com.xwd.base.service.FestivalService;
import com.xwd.base.service.ImageServerService;
import com.xwd.base.service.MqService;
import com.xwd.base.service.MqThemeService;
import com.xwd.base.service.SysConfigService;
import com.xwd.dd.model.Area;
import com.xwd.dd.service.AreaService;
import com.xwd.dd.service.BankService;

/**
 * 对象序列化初始化
 * 
 * @author linjinliang
 * 
 */
public class BaseDataServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = -3198980332587562173L;

	public void init(ServletConfig config) throws ServletException {
		
		WebApplicationContext wac = (WebApplicationContext) SpringContextUtil.getApplicationContext();
		AreaService areaService = (AreaService) wac.getBean(AreaService.class);
		
		PageRequest<HashMap<String, Object>> pageRequest = new PageRequest<HashMap<String, Object>>();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		pageRequest.setFilters(filters);
		int pageNumber = 1;
		// 地区
		while (true) {
			pageRequest.setPageSize(100);
			pageRequest.setPageNumber(pageNumber);
			Page<Area> page = areaService.findByPageRequest(pageRequest);
			List<Area> areaList = page.getResult();
			for(Area obj : areaList){
				if (obj.getParentId() == 1) {
					BaseDataConstant.DD_PROVINCE_AREA_LIST.add(obj);
				}
				BaseDataConstant.DD_AREA_MAP.put(obj.getPlatMark(), obj);
			}
			if(pageNumber >= page.getLastPageNumber()){
				break;
			}
			pageNumber++;
		}
		
		//银行
		BankService bankService = (BankService) wac.getBean(BankService.class);
		pageNumber = 1;
		while (true) {
			pageRequest.setPageSize(100);
			pageRequest.setPageNumber(pageNumber);
			Page<Bank> page = bankService.findByPageRequest(pageRequest);
			List<Bank> bankList = page.getResult();
			for(Bank obj : bankList){
				BaseDataConstant.DD_BANK_MAP.put(obj.getCode(), obj);
			}
			if(pageNumber >= page.getLastPageNumber()){
				break;
			}
			pageNumber++;
		}
		
		
		// 角色
		AuthRoleService authRoleService = (AuthRoleService) wac.getBean(AuthRoleService.class);
		List<AuthRole> authRoleList = authRoleService.findAll();
		for (AuthRole obj : authRoleList) {
			AuthRoleConstant.AUTH_ROLE_MAP.put(obj.getId(), obj);
		}

		// 消息服务器
		MqService mqService = (MqService) wac.getBean(MqService.class);
		List<Mq> mqList = mqService.findAll();
		for (Mq obj : mqList) {
			BaseDataConstant.BASE_MQ_MAP.put(obj.getId(), obj);
		}

		// 消息主题
		MqThemeService mqThemeService = (MqThemeService) wac.getBean(MqThemeService.class);
		List<MqTheme> mqThemeList = mqThemeService.findAll();
		for (MqTheme obj : mqThemeList) {
			BaseDataConstant.BASE_MQ_THEME_MAP.put(obj.getId(), obj);
		}

		// 邮件服务器
		EmailServerService emailServerService = (EmailServerService) wac.getBean(EmailServerService.class);
		List<EmailServer> emailServerList = emailServerService.findAll();
		for (EmailServer obj : emailServerList) {
			BaseDataConstant.BASE_EMAIL_SERVER_MAP.put(obj.getId(), obj);
		}

		// 图片服务器
		ImageServerService imageServerService = (ImageServerService) wac.getBean(ImageServerService.class);
		List<ImageServer> imageServerList = imageServerService.findAll();
		for (ImageServer obj : imageServerList) {
			BaseDataConstant.BASE_IMAGE_SERVER_MAP.put(obj.getId(), obj);
		}
		imageServerService.initCache();
		
		//分类
		CategoryService categoryService = (CategoryService) wac.getBean(CategoryService.class);
		pageNumber = 1;
		while (true) {
			pageRequest.setPageSize(100);
			pageRequest.setPageNumber(pageNumber);
			Page<Category> page = categoryService.findByPageRequest(pageRequest);
			List<Category> categoryList = page.getResult();
			for(Category obj : categoryList){
				BaseDataConstant.BASE_CATEGORY_MAP.put(obj.getId(), obj);
			}
			if(pageNumber >= page.getLastPageNumber()){
				break;
			}
			pageNumber++;
		}
		
		//产品属性表
		CategoryPropertyService categoryPropertyService = (CategoryPropertyService) wac.getBean(CategoryPropertyService.class);
		pageNumber = 1;
		while (true) {
			pageRequest.setPageSize(100);
			pageRequest.setPageNumber(pageNumber);
			Page<CategoryProperty> page = categoryPropertyService.findByPageRequest(pageRequest);
			List<CategoryProperty> categoryList = page.getResult();
			for(CategoryProperty obj : categoryList){
				BaseDataConstant.BASE_CATEGORY_PROPERTY_MAP.put(obj.getId(), obj);
				List<CategoryProperty> list = BaseDataConstant.BASE_CATEGORY_PROPERTY_LIST_MAP.get(obj.getId());
				if(list == null){
					list = new ArrayList<CategoryProperty>();
				}
				list.add(obj);
				BaseDataConstant.BASE_CATEGORY_PROPERTY_LIST_MAP.put(obj.getId(), list);
			}
			if(pageNumber >= page.getLastPageNumber()){
				break;
			}
			pageNumber++;
		}
		
		FestivalService festivalService = (FestivalService) wac.getBean(FestivalService.class);
		festivalService.initCache();
		
		SysConfigService sysConfigService = (SysConfigService) wac.getBean(SysConfigService.class);
		sysConfigService.initCache();
	}
}
