package com.xwd.product.web;
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

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.CategoryService;
import com.xwd.base.util.NoUtils;
import com.xwd.base.util.ZimgUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.bean.SelectModel;
import com.xwd.customer.service.CustomerService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductImage;
import com.xwd.product.provider.ProductProvider;
import com.xwd.product.service.ProductImageService;
import com.xwd.product.service.ProductService;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.service.CraftsmanService;

/**
 * 产品管理
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/product")
public class ProductController extends BaseAdminController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductProvider productProvider;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private CraftsmanService craftsmanService;

	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/product";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		String id= "";
		if(StringUtils.isNotBlank(id)){
			mapFilters.put("id", id);
		}
		Page<Product> page = productService.findByPageRequest(pageRequest);
		
		List<Product> result = page.getResult();
		if(result != null && result.size()>0){
			for (Product product : result) {
				/*if(product.getCategoryId() != null){
					Category category = categoryService.get(product.getCategoryId());
					if(category!=null){
						product.setCategoryName(category.getName());
					}
				}*/
				if(product.getCraftsmanNo() != null){
					Craftsman craftsman = craftsmanService.findUniqueBy("craftsmanNo",product.getCraftsmanNo());
					if(craftsman!=null){
						product.setCraftsmanName(craftsman.getName());
					}
				}
			}
			
		}
		
		logService.add(request, "分页查询产品信息");
		this.outPageJson(response, page, true);
	}
	
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		/*String categoryIdNew = request.getParameter("categoryIdNew");
		long cateId =0L;
		if(!"".equals(categoryIdNew)){
			Category category = new Category();
			category.setName(categoryIdNew);
			category.setDescription(categoryIdNew);
			categoryService.save(category);
			cateId = category.getId();
		}*/
		Product  entity;
		//查询最后一条保存的数据
		Product lastProduct = productService.findLastProduct();
		Long id = this.getLong("id");
		if(id == null){
			entity = new Product();
			this.setFieldValues(entity, request, false);
			Product lastProductByCategory = productService.findLastProductByCategory("categoryId",entity.getCategoryId());
			if(lastProductByCategory==null){
				entity.setCategoryCode(NoUtils.getCategoryCode(entity.getCategoryId()));
			}else{
				entity.setCategoryCode(lastProductByCategory.getCategoryCode()+1);
			}
			//产品编号
			if(lastProduct == null ){
				entity.setCode(100001L);
				entity.setImageServerId(Long.valueOf(ImageServer.IMAGE_SERVER_SYSTEM));
			}else{
				entity.setCode(lastProduct.getCode()+1);
			}
			logService.add(request, "新增产品信息");
			productProvider.save(entity);
		}else{
			entity = productService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改产品信息");
			this.setFieldValues(entity, request, true);
			Product lastProductByCategory = productService.findLastProductByCategory("categoryId",entity.getCategoryId());
			if(lastProductByCategory==null){
				entity.setCategoryCode(NoUtils.getCategoryCode(entity.getCategoryId()));
			}else{
				entity.setCategoryCode(lastProductByCategory.getCategoryCode()+1);
			}
			productProvider.update(entity);
		}
		productService.saveOrUpdate(entity);
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
		productService.deleteByIds(ids);
		logService.add(request, "删除产品信息");
		if(StringUtils.isNotBlank(ids)){
			String [] id = ids.split(",");
			for (String string : id) {
				Product product = productService.get(Long.valueOf(string));
				productProvider.delete(product.getCode());
			}
		}
		this.outSuccessJson(response);
	}
	
	/**
	 * 查找产品
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	/*@RequestMapping("/findProduct")
	@ResponseBody
	public void findProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Product> list = productService.findAll();
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		for (int i = 0; i < list.size(); i++) {
			SelectModel cdModel = new SelectModel();
			Product product = list.get(i);
			cdModel.setText(product.getName());
			cdModel.setValue(product.getId().toString());
			cdmList.add(cdModel);
		}
		this.outJson(response, cdmList);
	}*/
	
	/**
	 * 上传主图页面
	 * @return
	 */
	@RequestMapping("/toUploadImage")
	public String toUploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = this.get("id");
		request.setAttribute("id", id);
		//@PathVariable("id") String id,
		return "/admin/product/uploadImage";
	}
	
	/**
	 * 上传详情图页面
	 * @return
	 */
	@RequestMapping("/uploadImageDetail")
	public String toUploadImageDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = this.get("id");
		request.setAttribute("id", id);
		//@PathVariable("id") String id,
		return "/admin/product/uploadImageDetail";
	}
	
	
	/**
	 * 上传详情图页面
	 * @return
	 */
	@RequestMapping("/uploadShowImage")
	public String uploadShowImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = this.get("id");
		request.setAttribute("id", id);
		//@PathVariable("id") String id,
		return "/admin/product/uploadShowImage";
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
		String filNameVal = multipartRequest.getParameter("fileName");
		String fiNameArr[] = filNameVal.split(",");
		String fileName = "";
		
		String isMain = multipartRequest.getParameter("isMain");//是否是主图
		String fileLh = multipartRequest.getParameter("fileLh");//图片个数
		int fileNum = Integer.valueOf(fileLh);
		// 文件对象
		CommonsMultipartFile file = null;
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		String uploadUrl = imageServer.getImageUrl();
		
		String ImageId = "";
		for(int i=0;i<fileNum;i++){
			file = (CommonsMultipartFile) multipartRequest.getFile("file"+i);
			fileName = fiNameArr[i];
			
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			//接收参数  产品id
			String productId = multipartRequest.getParameter("id").toString();
			byte[] imgData = file.getBytes();
			//查询产品信息
			Product product = productService.findUniqueBy("id",Long.valueOf(productId));
			String retJson = ZimgUtils.sendPost(uploadUrl, imgData, fileType);
			int tIndex = retJson.indexOf("md5");
			
			if("3".equals(isMain)){
				product.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
				product.setImageServerId(imageServer.getId());
				productService.update(product);
				this.outSuccessJson(response,imageServer.getImageUrl()+"/"+product.getImageId());
				return;
			}
			//插入产品图片表
			ProductImage productImage = new ProductImage();
			if(product != null && product.getCode() != null){
				productImage.setProductCode(product.getCode());
			}
			productImage.setImageId(retJson.substring(tIndex + 6, tIndex + 38));
			ImageId += retJson.substring(tIndex + 6, tIndex + 38)+",";
			productImage.setImageServerId(imageServer.getId());
			if("1".equals(isMain)){
				productImage.setIsMain(true);
			}else{
				productImage.setIsMain(false);
			}
			productImageService.save(productImage);
		}
		if(!"".equals(ImageId)){
			ImageId = ImageId.substring(0, ImageId.length()-1);
		}
		this.outSuccessJson(response,imageServer.getImageUrl()+"-"+ImageId);
	}
	
	
	/**
	 * 查找产品
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findProduct")
	@ResponseBody
	public void findCategory(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long categoryId = Long.valueOf(get("categoryId"));
		List<Product> list = productService.findBy("categoryId",Long.valueOf(categoryId));
		List<SelectModel> cdmList = new ArrayList<SelectModel>();
		for (int i = 0; i < list.size(); i++) {
			SelectModel cdModel = new SelectModel();
			Product product = list.get(i);
			cdModel.setText(product.getName());
			cdModel.setValue(product.getCode().toString());
			cdmList.add(cdModel);
		}
		this.outJson(response, cdmList);
	}
}

