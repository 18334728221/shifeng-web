package com.xwd.mall.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.SecurityUtils;
import com.frame.page.Page;
import com.frame.page.PageRequest;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.constant.RedisBaseConstant;
import com.xwd.base.model.Category;
import com.xwd.base.model.CategoryProperty;
import com.xwd.base.model.ImageServer;
import com.xwd.base.service.CategoryPropertyService;
import com.xwd.base.service.CategoryService;
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Customer;
import com.xwd.log.service.LogService;
import com.xwd.mall.dto.ShoppingCart;
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.OrderComprisedService;
import com.xwd.product.model.Product;
import com.xwd.product.model.Sku;
import com.xwd.product.provider.ProductProvider;
import com.xwd.product.service.ProductService;
import com.xwd.product.service.SkuService;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.service.CraftsmanService;
import com.xwd.trade.model.OrderListDto;

/**
 * 购物车
 * 
 * @author admin
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/mall/shopping/cart")
public class ShoppingCartMobile extends BaseAdminController {

	@Autowired
	private LogService logService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryPropertyService categoryPropertyService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductProvider productProvider;
	@Autowired
	private SkuService skuService;
	@Autowired
	private CraftsmanService  craftsmanService;
	@Autowired
	private OrderComprisedService orderComprisedService;
	@Autowired
	private RedisTemplate<String, ShoppingCart> redisTemplate;

	/**
	 * 查询产品分类列表
	 **/
	@RequestMapping("/findAllCategory")
	@ResponseBody
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Category> categoryList = categoryService.findAll();
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		if (categoryList != null && categoryList.size() > 0) {
			for (Category category : categoryList) {
				category.setImageUrl(imageServer.getImageUrl() + "/" + category.getImageId());
			}
		}
		logService.add(request, "查询产品分类列表");
		outJson(response, categoryList);
	}

	/**
	 * 查询产品列表(根据产品种类)
	 **/
	@RequestMapping("/findAllProduct")
	@ResponseBody
	public void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 产品分类Id
		String categoryId = get("categoryId");
		// 是否进入交易所
		String isInExchange = get("isInExchange");

		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件

		// 添加查询条件
		if (StringUtils.isNotBlank(categoryId)) {
			mapFilters.put("categoryId", categoryId);
		}
		if (StringUtils.isNotBlank(isInExchange)) {
			mapFilters.put("isInExchange", isInExchange);
		}
		// 添加排序条件
		mapFilters.put("sortColumns", "IS_NEW asc,IS_HOT asc");

		Page<Product> page = productService.findByPageRequest(pageRequest);

		List<Product> result = page.getResult();
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		if (result != null && result.size() > 0) {
			for (Product product : result) {
				product.setImageUrl(imageServer.getImageUrl() + "/" + product.getImageId());
			}
		}

		logService.add(request, "查询产品列表");

		outJson(response, page);
	}

	/**
	 * 推荐商品
	 */
	@RequestMapping("/findGroomProduct")
	@ResponseBody
	public void findGroomProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String productCode = get("productCode");
		String categoryId = get("categoryId");
		if (StringUtils.isBlank(productCode)) {
			this.outFailureJson(response, "传入参数有误");
			return;
		}
		if (StringUtils.isBlank(categoryId)) {
			this.outFailureJson(response, "传入参数有误");
			return;
		}

		List<Product> page = productService.findExceptByProductId("productCode", productCode, "categoryId", categoryId);

		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		if (page != null && page.size() > 0) {
			for (Product product : page) {
				product.setImageUrl(imageServer.getImageUrl() + "/" + product.getImageId());
			}
		}

		this.outJson(response, page);
	}

	/**
	 * 库存
	 */
	@RequestMapping("/findStock")
	@ResponseBody
	public void findStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String string = get("av");
		if (StringUtils.isBlank(string)) {
			outFailureJson(response, "产品属性为空");
			return;
		}
		String av = string.replaceAll("_", ",");
		String productCode = get("productCode");
		if (StringUtils.isBlank(productCode)) {
			outFailureJson(response, "产品编号为空");
			return;
		}
		Sku findUniqueBy = skuService.findUniqueBy("productCode", productCode, "av", av);
		if (findUniqueBy == null) {
			outSuccessJson(response, "0");
			return;
		}
		outSuccessJson(response, findUniqueBy.getStock());
	}

	/**
	 * 加入购物车
	 */
	@RequestMapping("/saveShoppingCart")
	@ResponseBody
	public void saveShoppingCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		// 接收移动端传入参数
		String string = get("av");
		if (StringUtils.isBlank(string)) {
			outFailureJson(response, "产品属性为空");
			return;
		}
		String av = string.replaceAll("_", ",");
		String productCode = get("productCode");
		if (StringUtils.isBlank(productCode)) {
			outFailureJson(response, "产品编号为空");
			return;
		}
		
		String num = get("num");
		if (StringUtils.isBlank(num)) {
			outFailureJson(response, "产品数量为空");
			return;
		}
		String amount = get("amount");
		if (StringUtils.isBlank(amount)) {
			outFailureJson(response, "金额为空");
			return;
		}
		String paymentAmount = get("paymentAmount");
		if (StringUtils.isBlank(amount)) {
			outFailureJson(response, "支付金额为空");
			return;
		}
		Sku findUniqueBy = skuService.findUniqueBy("productCode", productCode, "av", av);
		if (findUniqueBy == null) {
			outFailureJson(response, "无此产品库存信息");
			return;
		}
		String sku = findUniqueBy.getSku();

		if (StringUtils.isBlank(sku) && StringUtils.isBlank(num)) {
			outFailureJson(response, "参数有误");
			return;
		}
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		ShoppingCart shoppingCart2 = redisTemplate.boundValueOps(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo()).get();
		// 判断购物车内有无产品
		if (shoppingCart2 != null) {

			Map<String, OrderItem> orderItemMap = shoppingCart2.getOrderItemMap();
			if (orderItemMap != null && orderItemMap.size()>0) {
				for (Entry<String, OrderItem> entry : orderItemMap.entrySet()) {
					// 如果购物车有
					if (entry.getKey().equals(sku)) {
						entry.getValue().setNum(entry.getValue().getNum() + Integer.valueOf(num));
						// 更新数量 金额
						shoppingCart2.addBuyNum(sku, Integer.valueOf(num));

						redisTemplate.boundValueOps(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo()).set(shoppingCart2);
						redisTemplate.expire(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo(), 7,TimeUnit.DAYS);
						
						outSuccessJson(response);
						return;
					} else {
						OrderItem orderItem = new OrderItem();
						
						// 购物车 无此产品
						Product findUniqueBy2 = productService.findUniqueBy("code", productCode);
						if (findUniqueBy2 == null) {
							outFailureJson(response, "产品信息为空");
							return;
						}
						Craftsman findUniqueBy4 = craftsmanService.findUniqueBy("craftsmanNo",findUniqueBy2.getCraftsmanNo());
						if (findUniqueBy4 != null) {
							orderItem.setShopName(findUniqueBy4.getShopName());
						}
						
						orderItem.setSku(sku);
						orderItem.setNum(Integer.valueOf(num));
						orderItem.setAmount(new BigDecimal(amount));
						orderItem.setPaymentAmount(new BigDecimal(paymentAmount));
						orderItem.setPrice(findUniqueBy2.getPrice());
						orderItem.setPayPrice(findUniqueBy2.getPrice());
						orderItem.setProductName(findUniqueBy2.getName());
						orderItem.setImageUrl(imageServer.getImageUrl() +"/"+ findUniqueBy2.getImageId());
						
						Sku findUniqueBy3 = skuService.findUniqueBy("productCode", productCode, "sku", sku);
						if (findUniqueBy3 != null) {
							String avs = findUniqueBy3.getAv();
							String avName = "";
							if (StringUtils.isBlank(avs)) {
								String[] ids = avs.split(",");
								for (String id : ids) {
									CategoryProperty categoryProperty = categoryPropertyService.get(Long.valueOf(id));
									avName += categoryProperty.getValue() + " ";
								}

							}
							orderItem.setAvName(avName);
						}
						orderItemMap.put(sku, orderItem);

						shoppingCart2.addBuyNum(sku, Integer.valueOf(num));

						redisTemplate.boundValueOps(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo()).set(shoppingCart2);
						redisTemplate.expire(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo(), 7,TimeUnit.DAYS);
						
						outSuccessJson(response);
						return;
					}
				}
			}else{
				OrderItem orderItem = new OrderItem();
				
				// 购物车 无此产品
				Product findUniqueBy2 = productService.findUniqueBy("code", productCode);
				if (findUniqueBy2 == null) {
					outFailureJson(response, "产品信息为空");
					return;
				}
				Craftsman findUniqueBy4 = craftsmanService.findUniqueBy("craftsmanNo",findUniqueBy2.getCraftsmanNo());
				if (findUniqueBy4 != null) {
					orderItem.setShopName(findUniqueBy4.getShopName());
				}
				
				orderItem.setSku(sku);
				orderItem.setNum(Integer.valueOf(num));
				orderItem.setAmount(new BigDecimal(amount));
				orderItem.setPaymentAmount(new BigDecimal(paymentAmount));
				orderItem.setPrice(findUniqueBy2.getPrice());
				orderItem.setPayPrice(findUniqueBy2.getPrice());
				orderItem.setProductName(findUniqueBy2.getName());
				orderItem.setImageUrl(imageServer.getImageUrl() +"/"+ findUniqueBy2.getImageId());
				
				Sku findUniqueBy3 = skuService.findUniqueBy("productCode", productCode, "sku", sku);
				if (findUniqueBy3 != null) {
					String avs = findUniqueBy3.getAv();
					String avName = "";
					if (StringUtils.isNotBlank(avs)) {
						String[] ids = avs.split(",");
						for (String id : ids) {
							CategoryProperty categoryProperty = categoryPropertyService.get(Long.valueOf(id));
							avName += categoryProperty.getValue() + " ";
						}

					}
					orderItem.setAvName(avName);
				}
				orderItemMap.put(sku, orderItem);
				
				shoppingCart2.addBuyNum(sku, Integer.valueOf(num));

				redisTemplate.boundValueOps(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo()).set(shoppingCart2);
				redisTemplate.expire(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo(), 7,TimeUnit.DAYS);
				
				outSuccessJson(response);
				return;
			}
			// 判断购物车内有无产品
		} else {
			ShoppingCart shoppingCart = new ShoppingCart();

			OrderItem orderItem = new OrderItem();
			
			// 购物车 无此产品
			Product findUniqueBy2 = productService.findUniqueBy("code", productCode);
			if (findUniqueBy2 == null) {
				outFailureJson(response, "产品信息为空");
				return;
			}
			Craftsman findUniqueBy4 = craftsmanService.findUniqueBy("craftsmanNo",findUniqueBy2.getCraftsmanNo());
			if (findUniqueBy4 != null) {
				orderItem.setShopName(findUniqueBy4.getShopName());
			}
			
			orderItem.setSku(sku);
			orderItem.setNum(Integer.valueOf(num));
			orderItem.setAmount(new BigDecimal(amount));
			orderItem.setPaymentAmount(new BigDecimal(paymentAmount));
			orderItem.setPrice(findUniqueBy2.getPrice());
			orderItem.setPayPrice(findUniqueBy2.getPrice());
			orderItem.setProductName(findUniqueBy2.getName());
			orderItem.setImageUrl(imageServer.getImageUrl() +"/"+ findUniqueBy2.getImageId());
			
			Sku findUniqueBy3 = skuService.findUniqueBy("productCode", productCode, "sku", sku);
			if (findUniqueBy3 != null) {
				String avs = findUniqueBy3.getAv();
				String avName = "";
				if (StringUtils.isNotBlank(avs)) {
					String[] ids = avs.split(",");
					for (String id : ids) {
						CategoryProperty categoryProperty = categoryPropertyService.get(Long.valueOf(id));
						avName += categoryProperty.getValue() + " ";
					}

				}
				orderItem.setAvName(avName);
			}

			shoppingCart.setId(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo());
			// 购买数量
			shoppingCart.addBuyNum(sku, Integer.valueOf(num));
			// 订单项
			shoppingCart.addOrderItemMap(sku, orderItem);

			redisTemplate.boundValueOps(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo()).set(shoppingCart);

			redisTemplate.expire(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo(), 7, TimeUnit.DAYS);
			
			outSuccessJson(response);
			return;
		}
	}

	/**
	 * 查看购物车
	 */
	@RequestMapping("/findShoppingCart")
	@ResponseBody
	public void findShoppingCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String key = RedisBaseConstant.SHOPPING_CART_EFFECTIVE +""+user.getCustomerNo();
		if (!redisTemplate.hasKey(key)) {
			outFailureJson(response, "无数据");
			return;
		}
		
		ShoppingCart shoppingCartList = redisTemplate.boundValueOps(key).get();
		if (shoppingCartList == null) {
			outJson(response, "无数据");
		}
		outJson(response, shoppingCartList);
	}

	/**
	 * 删除购物车产品
	 */
	@RequestMapping("/deleteShoppingCart")
	@ResponseBody
	public void deleteShoppingCart(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String sku = get("sku");
		if(StringUtils.isBlank(sku)){
			outFailureJson(response,"请选择产品");
			return;
		}
		String skus [] = sku.split("_");
		
		String key = RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo();
		if (!redisTemplate.hasKey(key)) {
			outFailureJson(response, "参数有误");
			return;
		}
		ShoppingCart shoppingCart = redisTemplate.boundValueOps(key).get();
		if (shoppingCart == null) {
			outFailureJson(response, "无数据信息");
			return;
		}
		for (String string : skus) {
			Map<String, OrderItem> orderItemMap = shoppingCart.getOrderItemMap();
			if (orderItemMap.containsKey(string) == false) {
				outFailureJson(response, "无数据信息");
				return;
			}
			orderItemMap.remove(string);
		}
		
		redisTemplate.boundValueOps(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo()).set(shoppingCart);

		redisTemplate.expire(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo(), 7, TimeUnit.DAYS);

		outJson(response, shoppingCart);
	}
	
	/**
	 * 结算购物车
	 */
	@RequestMapping("/settleAccounts")
	@ResponseBody
	public void settleAccounts(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String  description = get("description");
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String addressId = get("addressId");
		if(StringUtils.isBlank(addressId)){
			outFailureJson(response, "收货地址为空");
			return;
		}
		String amount = get("amount");
		if(StringUtils.isBlank(amount)){
			outFailureJson(response, "金额不能为空");
			return;
		}
		String paymentAmount = get("paymentAmount");
		if(StringUtils.isBlank(paymentAmount)){
			outFailureJson(response, "实付金额不能为空");
			return;
		}
		
		
		if(StringUtils.isBlank(get("sku"))){
			outFailureJson(response, "请选择产品属性");
			return;
		}
		String [] skus = get("sku").split("_");
		
		if(StringUtils.isBlank(get("num"))){
			outFailureJson(response, "产品数量为空");
			return;
		}
		String [] nums = get("num").split("_");
		
		GoodsOrder goodsOrder = orderComprisedService.settleAccounts(user, addressId, description, skus, nums, amount, paymentAmount);
		//清空购物车
		if(StringUtils.isBlank(get("num"))){
			outFailureJson(response, "数量不能为空");
			return;
		}
		String key = RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo();
		if (!redisTemplate.hasKey(key)) {
			outFailureJson(response, "参数有误");
			return;
		}
		ShoppingCart shoppingCart = redisTemplate.boundValueOps(key).get();
		if (shoppingCart == null) {
			outFailureJson(response, "无数据信息");
			return;
		}
		for (String string : skus) {
			Map<String, OrderItem> orderItemMap = shoppingCart.getOrderItemMap();
			if (orderItemMap.containsKey(string) == false) {
				outFailureJson(response, "无数据信息");
				return;
			}
			orderItemMap.remove(string);
		}
		
		redisTemplate.boundValueOps(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo()).set(shoppingCart);
		redisTemplate.expire(RedisBaseConstant.SHOPPING_CART_EFFECTIVE + "" + user.getCustomerNo(), 7, TimeUnit.DAYS);
		
		
		if(goodsOrder == null){
			outFailureJson(response,"提交失败");
		}else{
			outSuccessJson(response,goodsOrder);
		}
	}
	
	/**
	 * 购物车结算清单
	 */
	@RequestMapping("/findCart")
	@ResponseBody
	public void findCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response, "请登录");
			return;
		}
		
		if(StringUtils.isBlank(get("sku"))){
			outFailureJson(response, "请选择产品属性");
			return;
		}
		String [] skus = get("sku").split("_");
		
		
		if(StringUtils.isBlank(get("num"))){
			outFailureJson(response, "数量不能为空");
			return;
		}
		String [] nums = get("num").split("_");
		
		if(skus.length != nums.length){
			outFailureJson(response, "参数格式不正确");
			return;
		}
		List<OrderListDto> listDto = new ArrayList<OrderListDto>();
		
		ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
		for(int i = 0; i < skus.length; i++){
			Sku productSku = skuService.findUniqueBy("sku",skus[i]);
			if(productSku == null){
				outFailureJson(response, "无此产品数据");
				return;
			}
			Product product = productProvider.get(productSku.getProductCode());
			if(product == null){
				product = productService.findUniqueBy("code",productSku.getProductCode());
				if(product == null){
					outFailureJson(response, "无此产品数据");
					return;
				}
				productProvider.save(product);
			}
			Craftsman craftsman = craftsmanService.findUniqueBy("craftsmanNo",product.getCraftsmanNo());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setProductName(product.getName());
			orderListDto.setNum(Integer.valueOf(nums[i]));
			orderListDto.setPrice(product.getPrice());
			orderListDto.setImageUrl(imageServer.getImageUrl() +"/"+ product.getImageId());
			if(craftsman == null || craftsman.getShopName() == null){
				orderListDto.setShopName("");
			}else{
				orderListDto.setShopName(craftsman.getShopName());
			}
			
			String avName = "";
			if (StringUtils.isNotBlank(productSku.getAv())) {
				String[] avs = productSku.getAv().split(",");
				for (String av : avs) {
					CategoryProperty categoryProperty = categoryPropertyService.get(Long.valueOf(av));
					avName += categoryProperty.getValue() + " ";
				}
				orderListDto.setAvName(avName);
			}else{
				orderListDto.setAvName("");
			}
			listDto.add(orderListDto);
		}
		outSuccessJson(response,listDto);
	}
}
