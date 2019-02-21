package com.xwd.securities.mobile;
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
import com.xwd.base.constant.SysConfigConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.SysConfigService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.provider.CustomerProductProvider;
import com.xwd.engine.model.MarketOrder;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.service.ProductIssueService;
import com.xwd.product.service.ProductService;
import com.xwd.securities.model.Stock;
import com.xwd.securities.provider.StockProvider;
import com.xwd.securities.service.StockService;
import com.xwd.trade.provider.MarketOrderProvider;

@Controller
@Scope("prototype")
@RequestMapping("/mobile/stock")
public class StockMobile extends BaseAdminController {
	
	@Autowired
	private StockService stockService;
	@Autowired
	private StockProvider stockProvider;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private MarketOrderProvider marketOrderProvider;
	@Autowired
	private ProductIssueService productIssueService;
	@Autowired
	private CustomerProductProvider customerProductProvider;
	@Autowired
	private ProductService productService;
	
	/** 
	 * 查询股票列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer pageNo = getInteger("pageNo");
		if(pageNo == null){
			outFailureJson(response,"分页参数为空");
			return;
		}
		Integer pageSize = getInteger("pageSize");
		if(pageSize == null){
			outFailureJson(response,"分页参数为空");
			return;
		}
		String fieldName = get("fieldName");
		if(getInteger("asc") == null){
			outFailureJson(response,"排序参数为空");
			return;
		}
		boolean isAsc = getInteger("asc") == 1 ? true : false;
		if(StringUtils.isNotBlank(fieldName)){
			List<Stock> findBySort = stockProvider.findBySort(pageNo, pageSize, fieldName, isAsc);
			if(findBySort == null || findBySort.size()<=0){
				outSuccessJson(response,"无数据");
				return;
			}else{
				this.outJson(response, findBySort);
				return;
			}
		}else{
			List<Stock> find = stockProvider.find(pageNo, pageSize);
			if(find == null || find.size()<=0){
				outSuccessJson(response,"无数据");
				return;
			}else{
				this.outJson(response, find);
				return;
			}
		}
	}
	
	
	/**
	 * 查看单个股票信息(总市值)
	 */
	@RequestMapping("/findOne")
	@ResponseBody
	public void findOne(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productCode = get("productCode");
		if(StringUtils.isBlank(productCode)){
			outFailureJson(response,"产品编号为空");
			return;
		}
		Stock stock = stockProvider.get(Long.valueOf(productCode));
		if(stock == null){
			outSuccessJson(response,"无数据");
			return;
		}else{
			outSuccessJson(response,stock);
			return;
		}
	}
	
	/**
	 * 个股个人持仓信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/position/info")
	@ResponseBody
	public void getPositionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long productCode = getLong("productCode");
		if(null == productCode){
			outFailureJson(response,"产品编号为空");
			return;
		}
		Customer user = (Customer) SecurityUtils.getUser();
		if(user==null){
			outFailureJson(response,"未登录");
			return;
		}
		CustomerProduct entity = this.customerProductProvider.get(user.getCustomerNo(), productCode);
		
		if(entity == null){
			outSuccessJson(response,"无数据");
			return;
		}else{
			outSuccessJson(response, entity);
			return;
		}
	}
	
	/**
	 * 买单 降序
	 */
	@RequestMapping("/get/market/order")
	@ResponseBody
	public void getMarketOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long code = getLong("productCode");
		if(null == code){
			outFailureJson(response,"产品编号为空");
			return;
		}
		MarketOrder entity = marketOrderProvider.get(code);
		if(entity == null){
			outSuccessJson(response,"无数据");
			return;
		}else{
			outSuccessJson(response, entity);
			return;
		}
	}
	
	/**
	 * 可申购列表
	 */
	@RequestMapping("/findPurchase")
	@ResponseBody
	public void  findPurchase(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Stock> findBy = stockService.findBy("isNew",true);
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		for (Stock stock : findBy) {
			ProductIssue productIssue = productIssueService.findUniqueBy("productCode",stock.getCode());
			stock.setPurchaseTotalAmount(productIssue.getPurchaseTotalAmount());

			Product product = productService.findUniqueBy("code",stock.getCode());
			if(StringUtils.isNotBlank(product.getImageId())){
				stock.setImageUrl(imageServer.getImageUrl() +"/"+ product.getImageId());
			}else{
				stock.setImageUrl("");
			}
		}
		
		if(findBy == null || findBy.size()<=0){
			outSuccessJson(response,"无可申购产品");
			return;
		}
		outSuccessJson(response,findBy);
	}
	
	/**
	 * 买入手续费
	 */
	@RequestMapping("/getPoundage")
	@ResponseBody
	public void  getPoundage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String value = sysConfigService.getValue(SysConfigConstant.BUY_COMMISSION_RATE);
		outSuccessJson(response,value);
	}
	
	/**
	 * 根据模块查看流通产品列表
	 */
	@RequestMapping("/findByCategoryId")
	@ResponseBody
	public void findByCategoryId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer pageNo = getInteger("pageNo");
		if(pageNo == null){
			outFailureJson(response,"分页参数为空");
			return;
		}
		Integer pageSize = getInteger("pageSize");
		if(pageSize == null){
			outFailureJson(response,"分页参数为空");
			return;
		}
		Long categoryId = getLong("categoryId");
		if(categoryId == null){
			outFailureJson(response,"模块为空");
			return;
		}
		
		List<Stock> find = stockProvider.find(categoryId, pageNo, pageSize);
		if(find == null || find.size() <= 0){
			outSuccessJson(response,"数据为空");
		}
		outSuccessJson(response,find);
	}
}

