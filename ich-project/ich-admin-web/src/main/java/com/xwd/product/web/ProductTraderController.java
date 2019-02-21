package com.xwd.product.web;
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

import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.model.Category;
import com.xwd.base.service.CategoryService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.CustomerProduct;
import com.xwd.customer.service.CustomerProductService;
import com.xwd.log.service.LogService;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.model.ProductTrader;
import com.xwd.product.service.ProductIssueService;
import com.xwd.product.service.ProductService;
import com.xwd.product.service.ProductTraderService;

/**
 * 产品操盘手表
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin/product/trader")
public class ProductTraderController extends BaseAdminController {
	
	@Autowired
	private ProductTraderService productTraderService;
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private ProductIssueService productIssueService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/product/productTrader";
	}
	
	/** 
	 * 查询列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		Page<ProductTrader> page = productTraderService.findByPageRequest(pageRequest);
		
		List<ProductTrader> result = page.getResult();
		if(result!=null){
			for (ProductTrader productTrader : result) {
				long customerNo = productTrader.getCustomerNo();
				long productCode = productTrader.getProductCode();
				CustomerProduct customerProduct = customerProductService.findUniqueBy("customerNo",customerNo,"productCode",productCode);
				ProductIssue productIssue= productIssueService.findUniqueBy("productCode",productCode);
				Product product = productService.findUniqueBy("code",productCode);
				Category category = categoryService.findUniqueBy("id",product.getCategoryId());
				if(customerProduct!=null){
					long totalNum = customerProduct.getTotalNum();
					productTrader.setBidNum(totalNum);
				}
				if(productIssue!=null){
					long liu = productIssue.getPurchaseTotalAmount() - productIssue.getPurchaseNum();
					productTrader.setSurplusNum(liu);
				}
				if(category!=null){
					productTrader.setCategoryName(category.getName());
				}
			}
		}
		logService.add(request, "查询产品操盘手");
		this.outPageJson(response, page, true);
	}
	
	
	@RequestMapping("/distrBidNum")
	@ResponseBody
	public 	void distrBidNum(HttpServletRequest request, HttpServletResponse response)throws Exception {
		long customerNo = getLong("customerNo");
		long productCode = getLong("productCode");
		long totalNum = getLong("totalNum");
		CustomerProduct entity = customerProductService.findUniqueBy("customerNo",customerNo,"productCode",productCode);
		ProductIssue productIssue= productIssueService.findUniqueBy("productCode",productCode);
		if(entity == null){
			entity =new CustomerProduct();
			entity.setTotalNum(totalNum);
			entity.setCustomerNo(customerNo);
			entity.setProductCode(productCode);
			if(productIssue!=null){
				entity.setCost(productIssue.getPurchasePrice());
				entity.setPrice(productIssue.getPurchasePrice());
			}
			entity.setSellNum(totalNum);
			logService.add(request, "新增操盘手股份");
		}else{
			entity.setTotalNum(totalNum+entity.getTotalNum());
			logService.add(request, "修改操盘手股份");
		}
		if(productIssue.getPurchaseTotalAmount()>(productIssue.getPurchaseNum()+totalNum)){
			long productIssueNum = productIssue.getPurchaseNum();
			productIssue.setPurchaseNum(productIssueNum+totalNum);
			productIssueService.update(productIssue);
			customerProductService.saveOrUpdate(entity);
			this.outSuccessJson(response);
			return;
		}else{
			this.outFailureJson(response,"剩余股数不足");
			return;
		}
	}
	/** 
	 * 保存或更新对象.
	 **/
	@RequestMapping("/save")
	@ResponseBody
	public 	void save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		ProductTrader  entity;
		Long id = this.getLong("id");
		if(id == null){
			entity = new ProductTrader();
			this.setFieldValues(entity, request, false);
			logService.add(request, "新增产品操盘手");
		}else{
			entity = productTraderService.get(id);
			if(entity == null){
				this.outFailureJson(response);
				return;
			}
			logService.add(request, "修改产品操盘手");
			this.setFieldValues(entity, request, true);
		}
		productTraderService.saveOrUpdate(entity);
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
		productTraderService.deleteByIds(ids);
		logService.add(request, "删除产品操盘手");
		this.outSuccessJson(response);
	}
	
}

