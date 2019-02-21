package com.xwd.mall.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwd.base.util.NoUtils;
import com.xwd.customer.model.Customer;
import com.xwd.mall.model.GoodsOrder;
import com.xwd.mall.model.OrderItem;
import com.xwd.mall.service.GoodsOrderService;
import com.xwd.mall.service.OrderComprisedService;
import com.xwd.mall.service.OrderItemService;
import com.xwd.product.model.Product;
import com.xwd.product.model.Sku;
import com.xwd.product.provider.ProductProvider;
import com.xwd.product.service.ProductService;
import com.xwd.product.service.SkuService;

/**
 * 订单组合类
 *
 */
@Component
public class OrderComprisedServiceImpl implements OrderComprisedService {

	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductProvider productProvider;

	/**
	 * 提交订单
	 */
	public GoodsOrder save(Customer user, Long productCode, String addressId, String description, String av, String num,
			String amount, String paymentAmount) {
		// 创建订单
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setOrderNo(NoUtils.getOrderNo());
		// 收货地址
		goodsOrder.setAddressId(Long.valueOf(addressId));
		goodsOrder.setCustomerNo(user.getCustomerNo());
		// 总金额
		goodsOrder.setAmount(new BigDecimal(amount));
		// 支付金额
		goodsOrder.setPaymentAmount(new BigDecimal(paymentAmount));
		// 支付方式 0:微信 1:支付宝
		goodsOrder.setPayWey(GoodsOrder.GOODS_ORDER_PAY_WEY_BALANCE);
		// 支付状态 0:未支付 1:已支付 2:
		goodsOrder.setPayStatus(GoodsOrder.ORDER_PAY_STATUS_NOT);
		// 订单状态 订单状态 0:创建 1:取消
		goodsOrder.setOrderStatus(GoodsOrder.GOODS_ORDER_STATUS_CREATE);
		goodsOrder.setAreaPlatMark(user.getAreaPlatMark());
		goodsOrder.setDescription(description);
		int save = goodsOrderService.save(goodsOrder);
		if (save != 1) {
			return null;
		}
		// 单号
		String orderNo = goodsOrder.getOrderNo();
		GoodsOrder goodsOrder2 = goodsOrderService.findUniqueBy("orderNo", orderNo);
		// 接收参数
		String av2 = av.replace("_", ",");
		Sku sku = skuService.findUniqueBy("productCode", productCode, "av", av2);
		if (sku == null) {
			return null;
		}
		Product product = productProvider.get(productCode);
		if (product == null) {
			product = productService.findUniqueBy("code", productCode);
			productProvider.save(product);
		}
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(goodsOrder2.getId());
		orderItem.setOrderNo(orderNo);
		orderItem.setCustomerNo(user.getCustomerNo());
		orderItem.setAmount(new BigDecimal(amount));
		orderItem.setPaymentAmount(new BigDecimal(paymentAmount));
		orderItem.setProductCode(productCode);
		orderItem.setProductSkuId(sku.getId());
		orderItem.setNum(Integer.valueOf(num));
		// 0:待支付 1:待发货 2:待收货 3:待评价 4:已撤单
		orderItem.setFlow(OrderItem.ORDER_ITEM_FLOW_WAIT_PAY);
		orderItem.setSku(sku.getSku());
		orderItem.setPrice(product.getPrice());
		orderItem.setPayPrice(product.getPrice());
		int save2 = orderItemService.save(orderItem);
		if (save2 != 1) {
			return null;
		}

		GoodsOrder goodsOrder3 = goodsOrderService.findUniqueBy("orderNo", orderNo);
		return goodsOrder3;

	}

	@Override
	public GoodsOrder settleAccounts(Customer user, String addressId, String description,
			String [] productSku, String [] num, String amount, String paymentAmount) {
		// 创建订单
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setOrderNo(NoUtils.getOrderNo());
		// 收货地址
		goodsOrder.setAddressId(Long.valueOf(addressId));
		goodsOrder.setCustomerNo(user.getCustomerNo());
		// 总金额
		goodsOrder.setAmount(new BigDecimal(amount));
		// 支付金额
		goodsOrder.setPaymentAmount(new BigDecimal(paymentAmount));
		// 支付方式 0:微信 1:支付宝
		goodsOrder.setPayWey(GoodsOrder.GOODS_ORDER_PAY_WEY_BALANCE);
		// 支付状态 0:未支付 1:已支付 2:
		goodsOrder.setPayStatus(GoodsOrder.ORDER_PAY_STATUS_NOT);
		// 订单状态 订单状态 0:创建 1:取消
		goodsOrder.setOrderStatus(GoodsOrder.GOODS_ORDER_STATUS_CREATE);
		goodsOrder.setAreaPlatMark(user.getAreaPlatMark());
		goodsOrder.setDescription(description);
		int save = goodsOrderService.save(goodsOrder);
		if (save != 1) {
			return null;
		}
		// 单号
		String orderNo = goodsOrder.getOrderNo();
		GoodsOrder goodsOrder2 = goodsOrderService.findUniqueBy("orderNo", orderNo);
		
		if(productSku != null && productSku.length >0){
			for (int i = 0; i < productSku.length; i++) {
				// 接收参数
				Sku sku = skuService.findUniqueBy("sku", productSku[i]);
				
				if (sku == null) {
					return null;
				}
				Product product = productProvider.get(sku.getProductCode());
				if (product == null) {
					product = productService.findUniqueBy("code", sku.getProductCode());
					productProvider.save(product);
				}
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(goodsOrder2.getId());
				orderItem.setOrderNo(orderNo);
				orderItem.setCustomerNo(user.getCustomerNo());
				orderItem.setAmount(new BigDecimal(amount));
				orderItem.setPaymentAmount(new BigDecimal(paymentAmount));
				orderItem.setProductCode(sku.getProductCode());
				orderItem.setProductSkuId(sku.getId());
				orderItem.setNum(Integer.valueOf(num[i]));
				// 0:待支付 1:待发货 2:待收货 3:待评价 4:已撤单
				orderItem.setFlow(OrderItem.ORDER_ITEM_FLOW_WAIT_PAY);
				orderItem.setSku(sku.getSku());
				orderItem.setPrice(product.getPrice());
				orderItem.setPayPrice(product.getPrice());
				int save2 = orderItemService.save(orderItem);
				if (save2 != 1) {
					return null;
				}
			}
		}

		GoodsOrder findUniqueBy = goodsOrderService.findUniqueBy("orderNo", orderNo);
		return findUniqueBy;
	}
}
