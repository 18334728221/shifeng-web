package com.xwd.seller.web;
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

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.web.BaseAdminController;
import com.xwd.bean.SelectModel;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.model.CraftsmanCategory;
import com.xwd.seller.service.CraftsmanCategoryService;
import com.xwd.seller.service.CraftsmanService;

/**
 * 手艺人种类关系
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/seller/craftsmanCategory")
public class CraftsmanCategoryController extends BaseAdminController {
	
	@Autowired
	private CraftsmanCategoryService craftsmanCategoryService;
	
	@Autowired
	private CraftsmanService craftsmanService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/seller/craftsmanCategory";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<CraftsmanCategory> page = craftsmanCategoryService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		CraftsmanCategory  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new CraftsmanCategory();
			this.setFieldValues(entity, request, false);
		}else{
			entity = craftsmanCategoryService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		craftsmanCategoryService.saveOrUpdate(entity);
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
		craftsmanCategoryService.deleteByIds(ids);
	}
	
	/**
	 * 查找手艺人
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findCraftsman")
	@ResponseBody
	public void findCategory(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long categoryId = Long.valueOf(this.get("categoryId"));
		List<CraftsmanCategory> list = craftsmanCategoryService.findBy("categoryId",categoryId);
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				SelectModel cdModel = new SelectModel();
				Craftsman craftsman = craftsmanService.findUniqueBy("craftsmanNo",list.get(i).getCraftsmanNo());
				cdModel.setText(craftsman.getName());
				cdModel.setValue(craftsman.getCraftsmanNo().toString());
				cdmList.add(cdModel);
			}
		}
		this.outJson(response, cdmList);
	}
	
	
	/**
	 * 根据craftsmanNo查询分配的产品种类
	 *  @param craftsmanNo
	 **/
	@RequestMapping("/findCraftsmanCategoryList")
	@ResponseBody
	public void findCraftsmanCategoryList(HttpServletRequest request, HttpServletResponse response){
		Long craftsmanNo = getLong("craftsmanNo");
		if(null != craftsmanNo){
			List<CraftsmanCategory> aupList = craftsmanCategoryService.findByCraftsmanNo(craftsmanNo);
			outJson(response, aupList);
		}
	}
	
}

