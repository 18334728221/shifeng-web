package com.xwd.customer.mobile;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.provider.CustomerProductProvider;
import com.xwd.customer.service.CustomerProductService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.provider.ProductProvider;
import com.xwd.product.service.ProductService;

/**
 * 持仓表
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/customer/product")
public class CustomerProductMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private CustomerProductProvider customerProductProvider;
	@Autowired
	private ProductProvider productProvider;
	@Autowired
	private ProductService productService;
	
	
	/** 
	 *顾客持仓查询
	 **/
	@RequestMapping("/findByNo")
	@ResponseBody
	public void findByNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		List<CustomerProduct> list = customerProductProvider.get(user.getCustomerNo());
		if(list==null || list.size()<=0){
			list = customerProductService.findBy("customerNo",user.getCustomerNo());
		}
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		if(list == null || list.size()<=0){
			outSuccessJson(response,"无数据");
			return;
		}else{
			for (CustomerProduct customerProduct : list) {
				Product product = productProvider.get(customerProduct.getProductCode());
				if(product==null){
					product = productService.findUniqueBy("code",customerProduct.getProductCode());
					if(product == null){
						outFailureJson(response,"无持仓数据信息");
						return;
					}
				}
				customerProduct.setProductName(product.getName());
				customerProduct.setImageUrl(imageServer.getImageUrl()+"/"+product.getImageId());
			}
			outSuccessJson(response,list);
			return;
		}
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
			logService.add(request, "");
		}else{
			entity = customerProductService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			this.setFieldValues(entity, request, true);
			logService.add(request, "");
		}
		customerProductService.saveOrUpdate(entity);
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
		customerProductService.deleteByIds(ids);
		logService.add(request, "");
	}
	
}

