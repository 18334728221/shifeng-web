package com.xwd.home.mobile;

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

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Activity;
import com.xwd.base.model.Category;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.ActivityService;
import com.xwd.base.service.CategoryService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductImage;
import com.xwd.product.service.ProductImageService;
import com.xwd.product.service.ProductService;
import com.xwd.securities.model.Stock;
import com.xwd.securities.service.StockService;

/**
 * 产品 移动端首页
 * 
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/home/product")
public class HomeMobile extends BaseAdminController {

	@Autowired
	private LogService logService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private StockService stockService;

	/**
	 * 所有产品,首页优选
	 **/
	@RequestMapping("/findAll")
	@ResponseBody
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1 首页展示产品
		String isTop = get("isTop");
		String isHot = get("isHot");
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		
		// 添加排序条件
		if(StringUtils.isNotBlank(isHot)&&StringUtils.isNotBlank(isTop)){
			mapFilters.put("isHot", isHot);
			mapFilters.put("isTop", isTop);
			mapFilters.put("sortColumns", "IS_HOT asc");
		}
		
		Page<Product> page = productService.findByPageRequest(pageRequest);
		List<Product> result = page.getResult();
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		if (result != null && result.size() > 0) {
			for (Product product : result) {
				product.setImageUrl(imageServer.getImageUrl() + "/" + product.getImageId());
			}
		}
		logService.add(request, "首页产品操作");
		outJson(response, page);
	}
	
	/**
	 * 新品推荐
	 **/
	@RequestMapping("/findIsNew")
	@ResponseBody
	public void findIsNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1 首页展示产品
		String isNew = get("isNew");
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		
		// 添加排序条件
		if(StringUtils.isNotBlank(isNew)){
			mapFilters.put("isNew", isNew);
		}else{
			outFailureJson(response,"参数为空");
			return;
		}
		
		Page<Product> page = productService.findByPageRequest(pageRequest);
		List<Product> result = page.getResult();
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		if (result != null && result.size() > 0) {
			for (Product product : result) {
				product.setImageUrl(imageServer.getImageUrl() + "/" + product.getImageId());
			}
		}
		logService.add(request, "首页产品操作");
		outJson(response, page);
	}

	/**
	 * 查看单个品
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 产品Id
		String productCode = get("productCode");
		if (StringUtils.isNotBlank(get("productCode"))&&!"undefined".equals(productCode)) {
			Product product = productService.findUniqueBy("code", Long.valueOf(productCode));
			Stock stock = stockService.findUniqueBy("code",Long.valueOf(productCode));
				if(stock!=null){
					product.setPriceFluctuation(stock.getPriceFluctuation());
				}else{
					product.setPriceFluctuation((float) 0.0);
				}
			if (product == null) {
				this.outFailureJson(response, "无此记录信息");
			} else {
				if (StringUtils.isNotBlank(product.getImageId())) {
					ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
					product.setImageUrl(imageServer.getImageUrl() + "/" + product.getImageId());
				}
				outJson(response, product);
			}
		} else {
			this.outFailureJson(response, "请求参数有误");
		}
		logService.add(request, "查看产品信息");
	}

	/**
	 * 产品图片列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findProductImage")
	@ResponseBody
	public void findProductImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 产品Id
		String productCode = get("productCode");
		// List<ProductImage> list = new ArrayList<ProductImage>();
		Product product = productService.findUniqueBy("code", productCode);
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		// 添加查询条件
		mapFilters.put("productCode", product.getCode());
		Page<ProductImage> page = productImageService.findByPageRequest(pageRequest);
		logService.add(request, "查看产品展示图片");
		outJson(response, page);
	}

	/**
	 * 首页搜索 
	 */
	@RequestMapping("/search")
	@ResponseBody
	public void search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = get("name");
		List<Product> productList = productService.searchProduct("name",name);
		if(productList!=null && productList.size()>0){
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			for (Product product : productList) {
				product.setImageUrl(imageServer.getImageUrl()+"/"+product.getImageId());
			}
			outJson(response, productList);
		}else{
			outJson(response, "0");
		}
	}
	
	/**
	 * 最新产品推荐(显示两条)
	 */
	@RequestMapping("/findProductIsNew")
	@ResponseBody
	public void findHomeIsNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isNew = get("isNew");
		if(StringUtils.isBlank(isNew)){
			outFailureJson(response,"参数错误");
			return;
		}
		//首页最新
		if(StringUtils.isNotBlank(isNew)){
			List<Product> productList = productService.findHomeIsNew("isNew",isNew);
			if(productList !=null &&productList.size()>0 ){
				ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
				for (Product product : productList) {
					product.setImageUrl(imageServer.getImageUrl()+"/"+product.getImageId());
				}
				outSuccessJson(response,productList);
				return;
			}else{
				outSuccessJson(response,"0");
				return;
			}
		}
	}
	
	/**
	 * 最新活动(显示两条)
	 */
	@RequestMapping("/findActivityIsNew")
	@ResponseBody
	public void findActivityIsNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Activity> findActivity = activityService.findActivityIsNew();
		if(findActivity !=null && findActivity.size()>0){
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			for (Activity activity : findActivity) {
				activity.setImageUrl(imageServer.getImageUrl() + "/" + activity.getImageId());
			}
			this.outJson(response, findActivity);
		}else{
			this.outJson(response, "无数据信息");
		}
	}
	
	/**
	 * 首页推送种类
	 */
	@RequestMapping("/findCategoryIsTop")
	@ResponseBody
	public void findCategoryTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询首页推送的产品种类
		Category findUniqueBy = categoryService.findUniqueBy("isTop",1);
		if(findUniqueBy==null){
			List<Category> findAll = categoryService.findAll();
			if(findAll!=null &&findAll.size()>0){
				outSuccessJson(response,findAll.get(0));
				return;
			}else{
				outSuccessJson(response,"系统无产品种类");
				return;
			}
		}else{
			outSuccessJson(response,findUniqueBy);
			return;
		}
	}
	
}
