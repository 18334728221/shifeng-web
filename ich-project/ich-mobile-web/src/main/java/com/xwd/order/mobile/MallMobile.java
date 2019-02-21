package com.xwd.order.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

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
import com.xwd.base.web.BaseAdminController;
import com.xwd.customer.model.Address;
import com.xwd.customer.model.Customer;
import com.xwd.customer.service.AddressService;
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.GoodsOrderService;
import com.xwd.mall.service.OrderItemService;
import com.xwd.msg.model.CraftsmanLogistics;
import com.xwd.msg.service.CraftsmanLogisticsService;
import com.xwd.product.model.Product;
import com.xwd.product.provider.ProductProvider;
import com.xwd.product.service.ProductService;
import com.xwd.seller.model.Craftsman;
import com.xwd.seller.service.CraftsmanService;
import com.xwd.trade.model.OrderListDto;

/**
 * 商品订单
 * @author admin
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobile/order/mallMobile")
public class MallMobile extends BaseAdminController {
	
	
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductProvider productProvider;
	@Autowired
	private CraftsmanService craftsmanService;
	@Autowired
	private CraftsmanLogisticsService craftsmanLogisticsService;
	@Autowired
	private AddressService AddressService;
	
	/**
	 * 查询商场订单列表
	 **/
	@RequestMapping("/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
 		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String limit = get("limit");
		String start = get("start");
		String flow = get("flow");
		if(StringUtils.isBlank(flow) || StringUtils.isBlank(limit) || StringUtils.isBlank(start)){
			outFailureJson(response,"参数为空");
			return;
		}
		PageRequest<HashMap<String, Object>> pageRequest = setPageValue(request);
		Map<String, Object> mapFilters = pageRequest.getFilters();// 设置分页，获取查询条件
		
		if(OrderItem.ORDER_ITEM_FLOW_WAIT_CANCEL != Byte.valueOf(flow)){
			mapFilters.put("flow", flow);
		}
		
		mapFilters.put("customerNo",user.getCustomerNo());
		Page<OrderItem> orderItmList = orderItemService.findByPageRequest(pageRequest);
		Page<OrderListDto> pageDto = new Page<>();
		pageDto.setPageNumber(orderItmList.getPageNumber());
		pageDto.setTotalCount(orderItmList.getTotalCount());
		pageDto.setPageSize(Integer.parseInt(limit));
		List<OrderListDto> orderDtoList = new ArrayList<>();
		for (OrderItem item : orderItmList.getResult()) {
			OrderListDto orderDto = new OrderListDto();
			Long orderId = item.getOrderId();//订单id
			GoodsOrder goodsOrder = goodsOrderService.findUniqueBy("id",orderId);
			orderDto.setOrderNo(goodsOrder.getOrderNo());//订单号
			int payWay = goodsOrder.getPayWay();
			orderDto.setOrderId(orderId);
			orderDto.setPayWay(payWay);
			orderDto.setFlow(item.getFlow());
			orderDto.setOrderType(flow);
			BigDecimal payPrice = item.getPaymentAmount();//支付金额
			orderDto.setPaymentAmount(payPrice);
			int num = item.getNum();//购买数量
			orderDto.setNum(num);
			Long productCode = item.getProductCode();
			Product product = productService.findUniqueBy("code",productCode);
			Craftsman craftsman = new Craftsman();
			if(product!=null){
				orderDto.setPrice(product.getPrice());			//单价
				String cratSmaName = product.getCraftsmanName();//手艺人名称
				orderDto.setCratSmaName(cratSmaName);
				String proDescrip = product.getDescription();//产品描述
				orderDto.setProDescrip(proDescrip);
				if(product.getCraftsmanNo()!=null){
					craftsman = craftsmanService.findUniqueBy("craftsmanNo",product.getCraftsmanNo());//手艺人信息
				}
			}
				
			if(craftsman!=null){
				String shopName = craftsman.getShopName();//商铺名称
				orderDto.setShopName(shopName);
				String mobile = craftsman.getMobile();
				orderDto.setCraftMobile(mobile);
			}
			ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
			if(imageServer!=null&&craftsman!=null){
				orderDto.setHeadImageUrl(imageServer.getImageUrl() + "/" + craftsman.getHeadImageId());//头像
			}
			CraftsmanLogistics craftsLog = craftsmanLogisticsService.findUniqueBy("orderNo",goodsOrder.getOrderNo());
			if(craftsLog!=null){
				orderDto.setTrackingNo(craftsLog.getTrackingNo());//运单号
			}
			orderDtoList.add(orderDto);
		}
		pageDto.setResult(orderDtoList);
		this.outSuccessJson(response, pageDto);
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping("/findOrderOne")
	@ResponseBody
	public void findOrderOne(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer user = (Customer) SecurityUtils.getUser();
		if(user == null){
			outFailureJson(response,"请登录");
			return;
		}
		String orderNo = get("orderNo");
    	if(StringUtils.isBlank(orderNo)){
    		outFailureJson(response,"订单编号为空");
    		return;
    	}
    	GoodsOrder goodsOrder = goodsOrderService.findUniqueBy("orderNo",orderNo);
    	List<OrderItem> orderItemList = orderItemService.findBy("orderNo",orderNo);
    	if(goodsOrder == null){
    		outFailureJson(response,"无此订单信息");
			return;
    	}
    	OrderListDto orderDto = new OrderListDto();
    	Address address = AddressService.get(goodsOrder.getAddressId());
    	orderDto.setCustomerName(address.getConsignee());
    	orderDto.setMoblie(address.getMobile());
    	orderDto.setAddress(address.getDetailedAddress());
    	orderDto.setOrderNo(orderNo);
    	orderDto.setNote(goodsOrder.getDescription());
    	
    	ImageServer imageServer = BaseDataConstant.getImageServer(ImageServer.IMAGE_SERVER_SYSTEM);
    	if(!orderItemList.isEmpty()){
    		Product product = productProvider.get(orderItemList.get(0).getProductCode());
    		if(product == null ){
    			product = productService.findUniqueBy("code",orderItemList.get(0).getProductCode());
    			productProvider.save(product);
    		}
    		Craftsman craftsman = craftsmanService.findUniqueBy("craftsmanNo",product.getCraftsmanNo());
    		orderDto.setPrice(product.getPrice());
    		orderDto.setShopName(craftsman.getShopName());
    		orderDto.setProductName(product.getName());
    		orderDto.setImageUrl(imageServer.getImageUrl() +"/"+ product.getImageId());
    		orderDto.setNum(orderItemList.get(0).getNum());
    		orderDto.setPaymentAmount(orderItemList.get(0).getPaymentAmount());
    		orderDto.setAmount(orderItemList.get(0).getAmount());
    		orderDto.setFlow(orderItemList.get(0).getFlow());
    	}
    	this.outSuccessJson(response, orderDto);
	}
}
