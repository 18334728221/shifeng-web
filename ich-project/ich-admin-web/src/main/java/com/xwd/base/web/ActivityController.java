package com.xwd.base.web;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Activity;
import com.xwd.base.model.Category;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.ActivityService;
import com.xwd.base.service.CategoryService;
import com.xwd.base.util.ZimgUtils;
import com.xwd.bean.SelectModel;
import com.xwd.product.model.Product;
import com.xwd.product.service.ProductService;

@Controller
@Scope("prototype")
@RequestMapping("/admin/base/activity")
public class ActivityController extends BaseAdminController {
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/base/activity";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		
		Page<Activity> page = activityService.findByPageRequest(pageRequest);
		
		
		List<Activity> result = page.getResult();
		if(result != null && result.size()>0){
			for (Activity acty : result) {
				if(acty.getProductCode() != null){
					Product product = productService.findUniqueBy("code",acty.getProductCode());
					if(product!=null){
						Long categoryId = product.getCategoryId();
						Category category = categoryService.get(categoryId);
						acty.setCategoryName(category.getName());
						acty.setProductName(product.getName());
					}
					
				}
			}
		}
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Activity  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new Activity();
			this.setFieldValues(entity, request, false);
		}else{
			entity = activityService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
		}
		activityService.saveOrUpdate(entity);
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
		activityService.deleteByIds(ids);
		outSuccessJson(response);
	}
	
	
	/**
	 * 上传页面
	 * @return
	 */
	@RequestMapping("/toUploadImage")
	public String toUploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = this.get("id");
		request.setAttribute("id", id);
		return "/admin/base/uploadImageActivty";
	}
	
	/**
	 * 图片上传
	 * 接收图片
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadImage")
	public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String fileName = multipartRequest.getParameter("fileName");
		// 文件对象
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		String uploadUrl = imageServer.getImageUrl();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		//接收参数  产品id
		String id = multipartRequest.getParameter("id").toString();
		byte[] imgData = file.getBytes();
		//查询产品信息
		
		String retJson = ZimgUtils.sendPost(uploadUrl, imgData, fileType);
		
		int tIndex = retJson.indexOf("md5");
		Activity activty = activityService.get(Long.valueOf(id));
		if(activty != null){
			activty.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
			activty.setImageServerId(imageServer.getId());
		}
		
		activityService.update(activty);
		
		this.outSuccessJson(response,imageServer.getImageUrl()+"/"+activty.getImageId()); 
	}
	
	/**
	 * 查找产品种类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findCategory")
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
	}
	
	/**
	 * 查找产品编号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findProductCode")
	@ResponseBody
	public void findProductCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long categoryId = Long.valueOf(this.get("categoryId"));
		
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		List<Product> proList = productService.findBy("categoryId",categoryId);
		if(proList != null && proList.size() > 0){
			for (int i = 0; i < proList.size(); i++) {
				SelectModel cdModel = new SelectModel();
				Product product = productService.findUniqueBy("id",proList.get(i).getId());
				if(product!=null){
					cdModel.setText(product.getName());
					cdModel.setValue(product.getCode().toString());
					cdmList.add(cdModel);
				}
			}
		}
		this.outJson(response, cdmList);
	}
	
	
	/**
	 * 查找产品名称
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findProductName")
	@ResponseBody
	public void findProductName(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long activId = Long.valueOf(this.get("activId"));
		Long categoryId = activityService.findByAcitvId(activId);
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		List<Product> proList = productService.findBy("categoryId",categoryId);
		if(proList != null && proList.size() > 0){
			for (int i = 0; i < proList.size(); i++) {
				SelectModel cdModel = new SelectModel();
				Product product = productService.findUniqueBy("id",proList.get(i).getId());
				if(product!=null){
					cdModel.setText(product.getName());
					cdModel.setValue(product.getCode().toString());
					cdmList.add(cdModel);
				}
			}
		}
		this.outJson(response, cdmList);
	}
}

