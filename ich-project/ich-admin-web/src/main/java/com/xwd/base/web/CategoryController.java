package com.xwd.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alipay.api.domain.Product;
import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Category;
import com.xwd.base.model.ImageServer;
import com.xwd.base.provider.CategoryProvider;
import com.xwd.base.service.CategoryService;
import com.xwd.base.util.ZimgUtils;
import com.xwd.bean.SelectModel;
import com.xwd.log.service.LogService;
import com.xwd.product.service.ProductService;

/**
 * 产品种类
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/base/category")
public class CategoryController extends BaseAdminController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryProvider categoryProvider;
	@Autowired
	private LogService logService;

	@RequestMapping("/index")
	public String index() {
		return "/admin/base/category";
	}

	/**
	 * 查询列表
	 **/
	@RequestMapping("/findAll")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<Category> page = categoryService.findByPageRequest(pageRequest);
		logService.add(request, "分页查询产品分类信息。");
		this.outPageJson(response, page, true);
	}

	/**
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AuthUser user = (AuthUser) SecurityUtils.getUser();
		Category entity;
		Long id = this.getLong("id");
		if (id == null) {
			entity = new Category();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增产品分类信息");
			categoryProvider.save(entity);
		} else {
			entity = categoryService.get(id);
			if (entity == null) {
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
			logService.add(request, "修改产品分类信息");
			categoryProvider.update(entity);
		}
		categoryService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = get("ids");
		categoryService.deleteByIds(ids);
		logService.add(request, "删除产品分类信息");
		if(StringUtils.isNotBlank(ids)){
			String [] id = get("ids").split(",");
			for (String string : id) {
				categoryProvider.delete(Long.valueOf(string));
			}
		}
		this.outSuccessJson(response);
	}

	/**
	 * 首页上传页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUploadImage")
	public String toUploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = this.get("id");
		request.setAttribute("id", id);
		return "/admin/base/uploadImage";
	}

	/**
	 * 首页图片上传 接收图片
	 * 
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
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_MALL);
		String uploadUrl = imageServer.getImageUrl();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

		byte[] imgData = file.getBytes();

		String retJson = ZimgUtils.sendPost(uploadUrl, imgData, fileType);

		int tIndex = retJson.indexOf("md5");

		String id = multipartRequest.getParameter("id").toString();
		Category category = categoryService.get(Long.parseLong(id));
		category.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
		category.setImageServerId(imageServer.getId());
		categoryService.update(category);
		
		// 修改学生表中的对应的图片地址的路径
		this.outSuccessJson(response, imageServer.getImageUrl() + category.getImageId());
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
	 * 是否主推
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/isTop")
	@ResponseBody
	public void IsTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(this.get("id")) ;
		int isTop = Integer.parseInt(this.get("isTop"));
		
		if(isTop==0){
			isTop=1;
		}else{
			isTop=0;
		}
		Category entity = new Category();
		entity.setId(id);
		entity.setIsTop(isTop);
		categoryService.isTop(entity);
		this.outSuccessJson(response);
	}
	
	
	
	/**
	 * 查找产品种类id
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findColumnByCode")
	@ResponseBody
	public void findColumnByCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productCode = request.getParameter("productCode");
		com.xwd.product.model.Product product = productService.findUniqueBy("code",productCode);
		long categoryId = product.getCategoryId();
		this.outJson(response,categoryId);
	}
}
