package com.xwd.trade.mobile;

import java.math.BigDecimal;
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

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.ImageServer;
import com.xwd.base.provider.SysConfigProvider;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.product.model.Product;
import com.xwd.product.model.ProductIssue;
import com.xwd.product.model.ProductPurchase;
import com.xwd.product.service.ProductIssueService;
import com.xwd.product.service.ProductPurchaseService;
import com.xwd.product.service.ProductService;
import com.xwd.securities.provider.StockProvider;
import com.xwd.trade.dto.KLine;
import com.xwd.trade.model.TradeOrder;
import com.xwd.trade.provider.KLineProvider;
import com.xwd.trade.provider.TradeOrderProvider;
import com.xwd.trade.service.TradeComprisedService;
import com.xwd.trade.service.TradeOrderService;

/**
 * 交易
 * 
 * @author ljl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/trade")
public class TradeMobile extends BaseAdminController {

	@Autowired
	private TradeOrderService tradeOrderService;
	@Autowired
	private TradeOrderProvider tradeOrderProvider;
	@Autowired
	private TradeComprisedService tradeComprisedService;
	@Autowired
	private ProductPurchaseService productPurchaseService;
	@Autowired
	private KLineProvider kLineProvider;
	@Autowired
	private StockProvider stockProvider;
	@Autowired
	private SysConfigProvider sysConfigProvider;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductIssueService productIssueService;

	/**
	 * 买入
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/buy")
	@ResponseBody
	public void buy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer)SecurityUtils.getUser();
		
		if (user == null) {
			this.outFailureJson(response, "用户未登录.");
			return;
		}
		
		Long code = getLong("productCode");
		if (null == code) {
			this.outFailureJson(response, "产品编号不能为空.");
			return;
		}
		String temp = get("price");
		if (StringUtils.isBlank(temp)) {
			this.outFailureJson(response, "价格不能为空.");
			return;
		}
		BigDecimal price = new BigDecimal(temp);
		Long num = getLong("num");
		if (null == num) {
			this.outFailureJson(response, "数量不能为空.");
			return;
		}
		if(this.stockProvider.isOverLimitUpPrice(code, price)){
			this.outFailureJson(response, "委托价格不能超过涨停价.");
			return;
		}
		TradeOrder entity = tradeComprisedService.buy(code, price, num, TradeOrder.PRICE_METHOD_LIMIT, TradeOrder.ENTRUST_METHOD_MOBILE);
		if (entity == null) {
			this.outFailureJson(response, "账户余额不足.");
			return;
		}
		this.outSuccessJson(response, "1");
	}

	/**
	 * 卖出
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/sell")
	@ResponseBody
	public void sell(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer)SecurityUtils.getUser();
		
		if (user == null) {
			this.outFailureJson(response, "用户未登录.");
			return;
		}
		
		/*Boolean tradeTime = sysConfigProvider.isTradeTime();
		if(tradeTime == false){
			this.outFailureJson(response, "不是交易时间");
			return;
		}*/
		Long code = getLong("productCode");
		if (null == code) {
			this.outFailureJson(response, "产品编号不能为空.");
			return;
		}
		String temp = get("price");
		if (StringUtils.isBlank(temp)) {
			this.outFailureJson(response, "价格不能为空.");
			return;
		}
		BigDecimal price = new BigDecimal(temp);
		Long num = getLong("num");
		if (null == num) {
			this.outFailureJson(response, "数量不能为空.");
			return;
		}
		if(this.stockProvider.isOverLimitUpPrice(code, price)){
			this.outFailureJson(response, "委托价格不能低于跌停价.");
			return;
		}
		TradeOrder entity = tradeComprisedService.sell(code, price, num, TradeOrder.PRICE_METHOD_LIMIT,	TradeOrder.ENTRUST_METHOD_MOBILE);
		if (entity == null) {
			this.outFailureJson(response, "可卖数量不足.");
		}
		this.outSuccessJson(response, "1");
	}

	/**
	 * 撤单,根据委托号
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/cancel")
	@ResponseBody
	public void cancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer)SecurityUtils.getUser();
		
		if (user == null) {
			this.outFailureJson(response, "用户未登录.");
			return;
		}
		String txNo = get("txNo");
		if (StringUtils.isBlank(txNo)) {
			this.outFailureJson(response, "单号为空");
			return;
		}
		boolean cancel = this.tradeComprisedService.cancel(txNo);

		TradeOrder entity =tradeOrderProvider.get(user.getCustomerNo(), txNo);
		if(entity!=null){
			tradeOrderProvider.delete(entity);
		}
		if(cancel == false){
			this.outSuccessJson(response, "委托单撤销失败");
		}else{
			this.outSuccessJson(response, "委托单撤销成功。");
		}
	}

	/**
	 * 查询申购列表
	 **/
	@RequestMapping("/finPurchase")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		if (SecurityUtils.getUser() == null) {
			this.outFailureJson(response, "用户未登录.");
			return;
		}
		// 获取登录人信息
		Customer user = (Customer) SecurityUtils.getUser();
		pageRequest.getFilters().put("customerNo", user.getCustomerNo());
		Page<ProductPurchase> page = productPurchaseService.findByPageRequest(pageRequest);
		this.outPageJson(response, page, true);
	}

	/**
	 * 申购
	 */
	@RequestMapping("/purchase")
	@ResponseBody
	public void purchase(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long productCode = getLong("productCode");// 产品编号
		if (null == productCode) {
			this.outFailureJson(response, "产品编号不能为空");
			return;
		}
		Long purchaseNum = getLong("purchaseNum");// 申购数量
		if (null == purchaseNum) {
			this.outFailureJson(response, "申购数量不能为空");
			return;
		}
		if (SecurityUtils.getUser() == null) {
			this.outFailureJson(response, "用户未登录");
			return;
		}
		this.tradeComprisedService.purchase(productCode, purchaseNum);
		this.outSuccessJson(response);
	}
	
	/**
	 * 申购记录
	 */
	@RequestMapping("/findPurchase")
	@ResponseBody
	public void findPurchase(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer)SecurityUtils.getUser();
		if (null == user) {
			this.outFailureJson(response, "请登录");
			return;
		}
		List<ProductPurchase> findBy = productPurchaseService.findBy("customerNo",user.getCustomerNo());
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		for (ProductPurchase entity : findBy) {
			Product product = productService.findUniqueBy("code",entity.getProductCode());
			ProductIssue productIssue = productIssueService.findUniqueBy("productCode",entity.getProductCode());
			entity.setProductName(product.getName());
			entity.setPurchasePrice(productIssue.getPurchasePrice());
			if(StringUtils.isNotBlank(product.getImageId())){
				entity.setImageUrl(imageServer.getImageUrl() +"/"+ product.getImageId());
			}else{
				entity.setImageUrl("");
			}
		}
		if(findBy == null || findBy.size() <= 0){
			this.outSuccessJson(response,"无申购记录");
			return;
		}
		this.outSuccessJson(response,findBy);
	}

	/**
	 * 委托单明细
	 */
	@RequestMapping("/findEntrust")
	@ResponseBody
	public void findEntrust(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if (user == null) {
			this.outFailureJson(response, "用户未登录");
			return;
		}
		String productCode = get("productCode");
		if (StringUtils.isBlank(productCode)) {
			this.outFailureJson(response, "产品编号为空");
			return;
		}
		
		List<TradeOrder> findBy = tradeOrderProvider.getByCustomer(user.getCustomerNo());
		
		if (findBy == null || findBy.size() <= 0) {
			this.outSuccessJson(response, "无数据");
			return;
		}
		this.outSuccessJson(response, findBy);
	}

	/**
	 * 成交记录
	 */
	@RequestMapping("/findSuccess")
	@ResponseBody
	public void findSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long productCode = getLong("productCode");
		if (productCode == null) {
			this.outSuccessJson(response, "产品编号为空");
			return;
		}
		List<TradeOrder> findSuccess = tradeOrderService.findSuccess("productCode", productCode);
		if (findSuccess == null || findSuccess.size() <= 0) {
			this.outSuccessJson(response, "无数据");
			return;
		}
		this.outSuccessJson(response, findSuccess);
	}

	/**
	 * 查询klineData
	 * 
	 * @param code
	 * @param kLineType
	 * @param type 第一次:0000  当前时间:0001
	 */
	@RequestMapping("/getKlineData")
	public void getKlineData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<KLine> kLineList = null;
		String code = get("productCode");
		if (StringUtils.isBlank(code)) {
			this.outFailureJson(response, "产品编号为空");
			return ;
		}
		String kLineType = get("kLineType");
		if (StringUtils.isBlank(kLineType)) {
			this.outFailureJson(response, "K线类型为空");
			return;
		}
		String type = get("type");
		if(StringUtils.isBlank(type)){
			this.outFailureJson(response, "请求参数为空");
			return;
		}
		if(type.equals("0000")){
			kLineList = kLineProvider.find(Long.valueOf(code), getKLineType(kLineType));
			if(kLineList == null || kLineList.size() == 0){
				this.outFailureJson(response, "无数据");
				return;
			}
		}
		if(type.equals("0001")){
			KLine kLine = kLineProvider.get(Long.valueOf(code), getKLineType(kLineType));
			if(kLine != null){
				kLineList = new ArrayList<KLine>();
				kLineList.add(kLine);
			}else{
				this.outFailureJson(response, "无数据");
				return;
			}
		}

		String priceHigh = "[]";
		String priceLow = "[]";
		String priceOpen = "[]";
		String priceLast = "[]";
		String amount = "[]";
		String time = "[]";
		for (KLine kLine : kLineList) {
			priceHigh = priceHigh.substring(0, priceHigh.lastIndexOf("]")) + kLine.getHighestPrice() + "]";
			priceLow = priceLow.substring(0, priceLow.lastIndexOf("]")) + kLine.getLowestPrice() + "]";
			priceOpen = priceOpen.substring(0, priceOpen.lastIndexOf("]")) + kLine.getOpeningPrice() + "]";
			priceLast = priceLast.substring(0, priceLast.lastIndexOf("]")) + kLine.getClosingPrice() + "]";
			amount = amount.substring(0, amount.lastIndexOf("]")) + kLine.getTotalAmount() + "]";
			time = time.substring(0, time.lastIndexOf("]")) + kLine.getTimestamps() + "]";
		}
		StringBuilder result = new StringBuilder("5:::{\"name\":\"request\",\"args\":[{\"version\":1,\"msgType\":\"reqKLine\",\"requestIndex\":1492163094949,\"payload\":{");
		result.append("\"time\":");
		result.append(time);
		result.append(",");
		result.append("\"priceLast\":");
		result.append(priceLast);
		result.append(",");
		result.append("\"priceOpen\":");
		result.append(priceOpen);
		result.append(",");
		result.append("\"priceLow\":");
		result.append(priceLow);
		result.append(",");
		result.append("\"priceHigh\":");
		result.append(priceHigh);
		result.append(",");
		result.append("\"amount\":");
		result.append(amount);
		result.append(",");
		result.append("\"period\":\"");
		result.append(kLineType + "\"");
		result.append("}}]}");
		
		outSuccessJson(response, result.toString());
		kLineList.clear();
	}
	
	/**
	 * 获取K线类型
	 */
	private static String getKLineType(String kLineType){
		String type = "";
		if(StringUtils.isNoneBlank(kLineType)){
			if(kLineType.equals("1")){
				type = KLine.ONE_MINUTE_K_LINE;
			}else if(kLineType.equals("5")){
				type = KLine.FIVE_MINUTE_K_LINE;
			}else if(kLineType.equals("15")){
				type = KLine.FIFTEEN_MINUTE_K_LINE;
			}else if(kLineType.equals("30")){
				type = KLine.THIRTY_MINUTE_K_LINE;
			}else if(kLineType.equals("60")){
				type = KLine.SIXTY_MINUTE_K_LINE;
			}else if(kLineType.equals("61")){
				type = KLine.DAY_K_LINE;
			}else if(kLineType.equals("62")){
				type = KLine.WEEK_K_LINE;
			}else if(kLineType.equals("63")){
				type = KLine.MONTH_K_LINE;
			}else if(kLineType.equals("64")){
				type = KLine.QUARTER_K_LINE;
			}else if(kLineType.equals("65")){
				type = KLine.YEAR_K_LINE;
			}
		}
		return type;
	}
}
