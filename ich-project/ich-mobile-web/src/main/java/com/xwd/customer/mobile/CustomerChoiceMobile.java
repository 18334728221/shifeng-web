package com.xwd.customer.mobile;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.xwd.customer.model.CustomerChoice;
import com.xwd.customer.service.CustomerChoiceService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.service.ProductService;
import com.xwd.securities.model.Stock;
import com.xwd.securities.service.StockService;

/**
 * 我的自选
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/customer/choice")
public class CustomerChoiceMobile extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private CustomerChoiceService customerChoiceService;
	@Autowired
	private StockService stockService;
	@Autowired
	private ProductService productService;
	
	/** 
	 * 查询列表 
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		List<CustomerChoice> list = customerChoiceService.findChoiceBy("customerNo",user.getCustomerNo());
		for (CustomerChoice customerChoice : list) {
			Product product = productService.findUniqueBy("code",customerChoice.getProductCode());
			customerChoice.setProductName(product.getName());
			customerChoice.setPrice(product.getPrice());
			customerChoice.setImageUrl(imageServer.getImageUrl()+"/"+product.getImageId());
			Stock stock = stockService.findUniqueBy("code",customerChoice.getProductCode());
			if(stock!=null){
				customerChoice.setPriceFluctuation(stock.getPriceFluctuation());
			}else{
				customerChoice.setPriceFluctuation((float) 0.0);
			}
		}
		
		if(list == null || list.size() <= 0){
			outSuccessJson(response,"没有自选信息");
			return;
		}
		this.outSuccessJson(response, list);
	}
	
	/** 
	 * 自选 
	 * @param type:0自选
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
		
		String productCode = get("productCode");
		
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号不能为空");
			return;
		}
		if(user.getCustomerNo()==null){
			outFailureJson(response,"顾客编号不能为空");
			return;
		}
		
		CustomerChoice entity = customerChoiceService.findUniqueBy("productCode",productCode,"customerNo",user.getCustomerNo());
		
		if(entity == null){
			entity = new CustomerChoice();
			
			entity.setProductCode(Long.valueOf(productCode));
			entity.setCustomerNo(user.getCustomerNo());
			entity.setType(true);
			logService.add(request, "添加我的自选");
			customerChoiceService.save(entity);
			this.outMobileJson(response, "1", "成功", "");
		}else{
			customerChoiceService.deleteById(entity.getId());
			logService.add(request, "取消收藏");
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
		
		customerChoiceService.deleteByIds(ids);
		logService.add(request, "删除我的收藏");
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
		/*String type = get("type");
		if(StringUtils.isBlank(type)){
			outFailureJson(response,"类型参数为空");
			return;
		}*/
		String productCode = get("productCode");
		
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号不能为空");
			return;
		}
		
		CustomerChoice findUniqueBy = customerChoiceService.findUniqueBy("productCode",productCode,"customerNo",user.getCustomerNo());
		if(findUniqueBy!=null){
			//已自选
			outSuccessJson(response,"1");
			return;
		}
		if(findUniqueBy==null){
			//未自选
			outSuccessJson(response,"0");
			return;
		}
	}
	
}

