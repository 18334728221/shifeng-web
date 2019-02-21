package com.xwd.product.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.util.ZimgUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductImage;
import com.xwd.product.provider.ProductProvider;
import com.xwd.product.service.ProductImageService;
import com.xwd.product.service.ProductService;

/**
 * 产品展示图片表
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/image")
public class ProductImageController extends BaseAdminController {
	
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductProvider productProvider;
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String currPage =request.getParameter("currPage");
		String productCode =request.getParameter("productCode");
		if(!"".equals(productCode)){
			request.setAttribute("productCode",productCode );
		}
		request.setAttribute("currPage",currPage );
		// response.sendRedirect("/admin/product/productImage.jsp");
		 return "/admin/product/productImage";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productCode = get("productCode");
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		if(StringUtils.isNotBlank(productCode)){
			mapFilters.put("productCode", productCode);
		}
		Page<ProductImage> page = productImageService.findByPageRequest(pageRequest);
		List<ProductImage> result = page.getResult();
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		for (ProductImage productImage : result) {
			Product product = productService.findUniqueBy("code",productImage.getProductCode());
			if(product != null){
				productImage.setName(product.getName());
			}
			productImage.setImageUrl(imageServer.getImageUrl()+"/"+productImage.getImageId());
		}
		logService.add(request, "分页查询产品图片信息");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//AuthUser user = (AuthUser) SecurityUtils.getUser();
		ProductImage  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new ProductImage();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增产品图片信息");
		}else{
			entity = productImageService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改产品图片信息");
			this.setFieldValues(entity, request, true);
		}
		productImageService.saveOrUpdate(entity);
		this.outSuccessJson(response);
	}
	
	/**
	 * 删除对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete/{id}")
	public 	String delete(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response)throws Exception {
		int currPage = Integer.valueOf(request.getParameter("currPage"));
		String productCode = request.getParameter("productCode");
		productImageService.deleteByIds(id);
		//this.outSuccessJson(response);
		return "redirect:/admin/product/image/index?currPage="+currPage+"&productCode="+productCode;
	}
	
	/**
	 * 删除对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	public 	String deleteImg(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = get("delVal");
		int currPage = Integer.valueOf(request.getParameter("currPage"));
		String productCode = request.getParameter("productCode");
		productImageService.deleteByIds(ids);
		//this.outSuccessJson(response);
		return "redirect:/admin/product/image/index?currPage="+currPage+"&productCode="+productCode;
	}
	

	/**
	 * 设置主图(产品头像)
	 **/
	@RequestMapping("/isMain/{productCode}/{id}")
	public 	String isMain(@PathVariable("productCode") String productCode,@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		int currPage = Integer.valueOf(request.getParameter("currPage").trim());
		ProductImage findUniqueBy2 = productImageService.findUniqueBy("id",id);
		findUniqueBy2.setIsMain(true);
		productImageService.update(findUniqueBy2);
		//修改product表记录dd
		/*Product findUniqueBy = productService.findUniqueBy("code",productCode);
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		if(findUniqueBy2 !=null ){
			findUniqueBy.setImageId(findUniqueBy2.getImageId());
			findUniqueBy.setImageServerId(imageServer.getId());
			productService.update(findUniqueBy);
			//跟新缓存数据
			productProvider.update(findUniqueBy);
		}*/
		
		/*ProductImage entity = new ProductImage();
		entity.setId(Long.valueOf(id));
		entity.setIsMain(true);
		productImageService.update(entity);*/
		
		return "redirect:/admin/product/image/index?currPage="+currPage+"&productCode="+productCode;
	}
	
	/**
	 * 取消主图
	 **/
	@RequestMapping("/noMain/{id}")
	public 	String noMain(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response)throws Exception {

		int currPage = Integer.valueOf(request.getParameter("currPage").trim());
		String productCode = request.getParameter("productCode");
		if(StringUtils.isNotBlank(id)){
			//更新productImage 
			ProductImage entity = productImageService.get(Long.valueOf(id));
			if(entity != null){
				entity.setId(Long.valueOf(id));
				entity.setIsMain(false);
				productImageService.update(entity);
				//更新product
				/*Product product = productService.findUniqueBy("code",entity.getProductCode());
				if(product != null){
					product.setImageId("");
					productService.update(product);
				}*/
			}
		}
		return "redirect:/admin/product/image/index?currPage="+currPage+"&productCode="+productCode;
	}
	
	/**
	 * 设为展示图
	 **/
	@RequestMapping("/setShow/{id}")
	public 	String setShow(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		int currPage = Integer.valueOf(request.getParameter("currPage").trim());
		String productCode = request.getParameter("productCode");
		if(StringUtils.isNotBlank(id)){
			//更新productImage 
			Product product = productService.findUniqueBy("code",productCode);
			ProductImage findUniqueBy2 = productImageService.findUniqueBy("id",id);
			if(product !=null ){
				product.setImageId(findUniqueBy2.getImageId());
				productService.update(product);
				//跟新缓存数据
				productProvider.update(product);
			}
		}
		return "redirect:/admin/product/image/index?currPage="+currPage+"&productCode="+productCode;
	}
	
	/**
	 * 上传页面
	 * @return
	 */
	@RequestMapping("/toUploadImage")
	public String toUploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productCode = this.get("productCode");
		request.setAttribute("productCode", productCode);
	
		return "/admin/product/uploadProductImage";
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
		String productCode = multipartRequest.getParameter("productCode").toString();
		byte[] imgData = file.getBytes();
		//查询产品信息
		//Product product = productService.findUniqueBy("id",Long.valueOf(productId));
		String retJson = ZimgUtils.sendPost(uploadUrl, imgData, fileType);
		
		int tIndex = retJson.indexOf("md5");
		//插入产品图片表
		ProductImage productImage = new ProductImage();
	
		productImage.setProductCode(Long.valueOf(productCode));
	
		productImage.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
		productImage.setImageServerId(imageServer.getId());
		productImageService.save(productImage);
		
		this.outSuccessJson(response,imageServer.getImageUrl()+productImage.getImageId()); 
	}
	
	
}

