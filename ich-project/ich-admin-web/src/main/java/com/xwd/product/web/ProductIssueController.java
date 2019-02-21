package com.xwd.product.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.frame.util.CalendarUtils;
import com.xwd.account.provider.AccountProvider;
import com.xwd.base.web.BaseAdminController;
import com.xwd.bean.SelectModel;
import com.xwd.customer.model.Customer;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.provider.CustomerProductProvider;
import com.xwd.customer.service.CustomerProductService;
import com.xwd.customer.service.CustomerService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.service.ProductIssueService;
import com.xwd.product.service.ProductPurchaseService;
import com.xwd.product.service.ProductService;

/**
 * 发行表
 * 
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/issue")
public class ProductIssueController extends BaseAdminController {

	@Autowired
	private ProductIssueService productIssueService;
	@Autowired
	private LogService logService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private CustomerProductProvider customerProductProvider;
	@Autowired
	private AccountProvider accountProvider;


	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productIssue";
	}

	/**
	 * 发行
	 * 
	 * @return
	 */
	@RequestMapping("/allotIssue")
	public String allotSku() {

		// 产品编号
		String productCode = this.get("productCode");

		Product product = productService.findUniqueBy("code", productCode);
		// 产品流通量
		Long circulatingStock = product.getCirculatingStock();

		Long issueAmount = productIssueService.findProductAmount(Long.valueOf(productCode));
		Long skuAmount = productIssueService.findProductAmount(Long.valueOf(productCode));

		Long productAmount = circulatingStock - issueAmount - skuAmount;

		request.setAttribute("productAmount", productAmount);
		request.setAttribute("productCode", productCode);

		return "/admin/product/allotIssue";
	}

	/**
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<ProductIssue> page = productIssueService.findByPageRequest(pageRequest);
		logService.add(request, "查询发行");
		this.outPageJson(response, page, true);
	}

	/**
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		ProductIssue entity =  new ProductIssue();
		
		String productCode =  request.getParameter("productCode");
		entity.setId(0L);
		entity.setProductCode(Long.valueOf(productCode));
		entity.setCirculatingStock(Long.valueOf(request.getParameter("circulatingStock")));
		entity.setUnderwritingStartTime(CalendarUtils.convertCalendarPattern(request.getParameter("underwritingStartTime"), "yyyy-MM-dd HH:MM:ss"));
		//承销结束时间
		entity.setUnderwritingEndTime(CalendarUtils.convertCalendarPattern(request.getParameter("underwritingEndTime"), "yyyy-MM-dd HH:MM:ss"));
		
		//申购开始时间
		entity.setPurchaseStartTime(CalendarUtils.convertCalendarPattern(request.getParameter("purchaseStartTime"), "yyyy-MM-dd HH:MM:ss"));
		//申购结束时间
		entity.setPurchaseEndTime(CalendarUtils.convertCalendarPattern(request.getParameter("purchaseEndTime"), "yyyy-MM-dd HH:MM:ss"));
		
		//流通时间
		entity.setCirculateTime(CalendarUtils.convertCalendarPattern(request.getParameter("circulateTime"), "yyyy-MM-dd HH:MM:ss"));
		//积分会员申购数
		entity.setIntegralPurchaseNum(Long.valueOf(request.getParameter("integralPurchaseNum")));  
		//申购总数
		entity.setPurchaseTotalAmount(Long.valueOf( request.getParameter("purchaseTotalAmount")));
		//申购参与积分人数
		entity.setIntegralNum(Integer.valueOf( request.getParameter("integralNum")));
		//申购价格
		entity.setPurchasePrice(new BigDecimal( request.getParameter("purchasePrice")));
		//是否申购结束
		entity.setIsPurchaseEnd(false);
		//保存发行产品
		productIssueService.save(entity);
		//产品进入交易所
		Product product = productService.findUniqueBy("code",entity.getProductCode());
		product.setIsInExchange(true);
		productService.update(product);
		
		this.outSuccessJson(response);
	}

	/**
	 * 删除对象. 这里接受一个名称为“ids”的字符串，id之间用英文半角的逗号“,”分隔。
	 **/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = get("ids");
		productIssueService.deleteByIds(ids);
		logService.add(request, "删除发行");
		this.outSuccessJson(response);
	}

	/**
	 * 查找客户customer
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findcustomerNo")
	@ResponseBody
	public void findcustomerNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int purchaseNum = Integer.parseInt(get("purchaseNum"));
		List<Customer> list = customerService.findPurchaseNum(purchaseNum);

		List<SelectModel> cdmList = new ArrayList<SelectModel>();

		for (int i = 0; i < list.size(); i++) {
			SelectModel cdModel = new SelectModel();
			Customer customer = list.get(i);
			cdModel.setText(customer.getCustomerNo().toString());
			cdModel.setValue(customer.getCustomerNo().toString());
			cdmList.add(cdModel);
		}
		this.outJson(response, cdmList);
	}

	@RequestMapping("/saveCalculate")
	@ResponseBody
	public void savCalculate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long productCode = this.getLong("productCode");// 产品编号
		long customerNo = this.getLong("customerNo");// 客户编号
		long totalNum = this.getLong("totalNum");// 承销数量
		
		BigDecimal price = new BigDecimal( this.get("price"));// 当前价格

		CustomerProduct customerProduct = new CustomerProduct();
		customerProduct.setCustomerNo(customerNo);
		customerProduct.setProductCode(productCode);
		customerProduct.setTotalNum((long) totalNum);
		customerProduct.setBuyCost(price.multiply(new BigDecimal(totalNum)));
		customerProduct.setPrice(price);
		customerProduct.setCost(price);// 持仓成本
		customerProduct.setSellNum(totalNum);
		CustomerProduct entity = customerProductService.findUniqueBy("productCode",productCode, "customerNo",customerNo);
		if (entity == null) {
			customerProductService.save(customerProduct);
			this.customerProductProvider.save(customerProduct);
			// 买入解冻对应的资金
			this.accountProvider.unfrozenFund(entity.getCustomerNo(), entity.getBuyCost());
		} else {
			this.customerProductProvider.increaseTotal(entity.getCustomerNo(), entity.getProductCode(),	totalNum);
			this.customerProductProvider.increaseBuyCost(entity.getCustomerNo(), entity.getProductCode(), price.multiply(new BigDecimal(totalNum)));
			//更新账户总成本和总市值
			this.accountProvider.increaseCost(entity.getCustomerNo(),price.multiply(new BigDecimal(totalNum)));
			this.accountProvider.increaseMarketValue(entity.getCustomerNo(), price.multiply(new BigDecimal(totalNum)));
		}
		this.outSuccessJson(response);
	}
}
