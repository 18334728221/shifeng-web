package com.xwd.customer.mobile;
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

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.ImageServerService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.customer.model.CustomerCollection;
import com.xwd.customer.service.CustomerCollectionService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.service.ProductService;

/**
 * 我的收藏
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/customer/collection")
public class CustomerCollectionMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CustomerCollectionService collectionService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ImageServerService imageServerService;
	
	/** 
	 * 查询列表  type:1收藏  0:自选
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String type = get("type");
		if(StringUtils.isBlank(type)){
			outFailureJson(response,"类型参数为空");
			return;
		}
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		mapFilters.put("customerNo", user.getCustomerNo());
		mapFilters.put("type", type);
		mapFilters.put("sortColumns", "ID desc");
		Page<CustomerCollection> page = collectionService.findByPageRequest(pageRequest);
		if(page.getResult() == null && page.getResult().size() <= 0){
			outSuccessJson(response,"没有收藏信息");
			return;
		}
		ImageServer imageServer = imageServerService.findUniqueBy("serverType", ImageServer.IMAGE_SERVER_SYSTEM);
		List<CustomerCollection> result = page.getResult();
		for (CustomerCollection customerCollection : result) {
			Product product = productService.findUniqueBy("code",customerCollection.getProductCode());
			if(product == null){
				outFailureJson(response,"无此产品信息");
				return;
			}
			customerCollection.setProductName(product.getName());
			customerCollection.setPrice(product.getPrice());
			if(StringUtils.isBlank(product.getImageId())){
				customerCollection.setImageUrl(imageServer.getImageUrl() +"/"+ product.getImageId());
			}
		}
		this.outJson(response, page);
	}
	
	/** 
	 * 自选 收藏(如果有已收藏则取消,未收藏则收藏)
	 * 1:收藏  0:取消收藏
	 **/
	@RequestMapping("/edit")
	@ResponseBody
	public 	void edit(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String type = get("type");
		if(StringUtils.isBlank(type)){
			outFailureJson(response,"类型参数为空");
			return;
		}
		CustomerCollection entity;
		String productCode = get("productCode");
		Long customerNo = user.getCustomerNo();
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号不能为空");
			return;
		}
		if(user.getCustomerNo()==null){
			outFailureJson(response,"顾客编号不能为空");
			return;
		}
		entity = collectionService.findUniqueBy("productCode",productCode,"customerNo",customerNo,"status","1");
		if(entity == null){
			entity = new CustomerCollection();
			
			entity.setProductCode(Long.valueOf(productCode));
			entity.setCustomerNo(customerNo);
			entity.setType(true);
			collectionService.save(entity);
			logService.add(request, "添加我的收藏.");
			this.outMobileJson(response, "1", "成功", "");
		}else{
			collectionService.deleteById(entity.getId());
			logService.add(request, "取消收藏.");
			this.outMobileJson(response, "0", "取消", "");
		}
		
	}
	
	/**
	 * 删除对象.
	 * 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public 	void delete(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String split = get("ids");
		
		if(StringUtils.isBlank(split)){
			outFailureJson(response,"无选中项");
			return;
		}
		String ids = split.replaceAll("_", ",");
		
		collectionService.deleteByIds(ids);
		logService.add(request, "删除我的收藏.");
		this.outSuccessJson(response,"删除收藏成功");
	}
	
	/**
	 * 登录顾客是否收藏(自选)
	 */
	@RequestMapping("/findIsCollection")
	@ResponseBody
	public 	void findIsCollection(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		
		String productCode = get("productCode");
		
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号不能为空");
			return;
		}
		CustomerCollection findUniqueBy = collectionService.findUniqueBy("productCode",productCode,"customerNo",user.getCustomerNo());
		if(findUniqueBy!=null){
			//已收藏
			outSuccessJson(response,"1");
			return;
		}
		if(findUniqueBy==null){
			//未收藏
			outSuccessJson(response,"0");
			return;
		}
	}
}

