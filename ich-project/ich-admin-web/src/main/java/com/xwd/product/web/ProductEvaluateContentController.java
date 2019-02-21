package com.xwd.product.web;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.ProductEvaluateContent;
import com.xwd.product.service.ProductEvaluateContentService;

/**
 * 产品评价内容
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/evaluate")
public class ProductEvaluateContentController extends BaseAdminController {
	
	@Autowired
	private ProductEvaluateContentService productEvaluateContentService;
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productEvaluateContent";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<ProductEvaluateContent> page = productEvaluateContentService.findByPageRequest(pageRequest);
		logService.add(request, "查询产品评价内容");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		ProductEvaluateContent  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new ProductEvaluateContent();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增产品评价内容");
		}else{
			entity = productEvaluateContentService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
			logService.add(request, "修改产品评价内容");
		}
		productEvaluateContentService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}
	
	/**
	 * 删除对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public 	void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("ids");
		productEvaluateContentService.deleteByIds(ids);
		logService.add(request, "删除产品评价内容");
		this.outSuccessJson(response);
	}
	
}

