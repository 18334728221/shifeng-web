package com.xwd.customer.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.xwd.base.model.Category;
import com.xwd.base.web.BaseAdminController;
import com.xwd.bean.SelectModel;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.service.CustomerProductService;

@Controller
@Scope("prototype")
@RequestMapping("/admin/product/customerProduct")
public class CustomerProductController extends BaseAdminController {
	
	@Autowired
	private CustomerProductService customerProductService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/customerProduct";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<CustomerProduct> page = customerProductService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		CustomerProduct  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new CustomerProduct();
			this.setFieldValues(entity, request, false);
		}else{
			entity = customerProductService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		customerProductService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}
	
	/**
	 * 查找产品种类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	/*@RequestMapping("/fcustomerNo")
	@ResponseBody
	public void findCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Category> list = categoryService.findAll();
		List<SelectModel> cdmList = new ArrayList<SelectModel>();

		for (int i = 0; i < list.size(); i++) {
			SelectModel cdModel = new SelectModel();
			Category category = list.get(i);
			cdModel.setText(category.getName());
			cdModel.setValue(category.getId().toString());
			cdmList.add(cdModel);
		}
		this.outJson(response, cdmList);
	}*/
	
	/**
	 * 删除对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public 	void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("ids");
		customerProductService.deleteByIds(ids);
		outSuccessJson(response);
	}
	
}

