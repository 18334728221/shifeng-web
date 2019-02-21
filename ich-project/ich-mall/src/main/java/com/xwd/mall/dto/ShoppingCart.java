package com.xwd.mall.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.xwd.mall.model.OrderItem;

/**
 * 购物车
 * @author ljl
 *
 */
public class ShoppingCart implements Serializable{
	
	private static final long serialVersionUID = -4791006366984082282L;

	//购物车ID,如果登陆则是key+登录用户id,没有就是key+50位生成码
	private String id;
	//sku 对应产品
	private Map<String, OrderItem> orderItemMap = new HashMap<String, OrderItem>();
	//购买数量
	private int buyNum = 0;
	//总价格
	private BigDecimal totalAmount = new BigDecimal(0);
	//支付价格
	private BigDecimal paymentAmount = new BigDecimal(0);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<String, OrderItem> getOrderItemMap() {
		return orderItemMap;
	}
	
	/**
	 * 添加购买产品
	 * 如果存在对应的sku，对应的数量增加1
	 * 如果不存在，则缓存
	 * @param sku
	 * @param orderItem
	 */
	public void addOrderItemMap(String sku, OrderItem orderItem) {
		OrderItem entity = this.orderItemMap.get(sku);
		if(entity == null){
			buyNum += orderItem.getNum();
			this.orderItemMap.put(sku, orderItem);
		} else {
			//orderItem.addNum(1);
			buyNum++;
		}
		totalAmount = totalAmount.add(orderItem.getAmount());
		paymentAmount = paymentAmount.add(orderItem.getPaymentAmount());
	}
	
	/**
	 * 新增购买数量
	 * @param sku
	 * @param num
	 */
	public void addBuyNum(String sku, int num){
		OrderItem entity = this.orderItemMap.get(sku);
		if(entity != null){
			//entity.addNum(num);
			buyNum += num;
			BigDecimal n = new BigDecimal(num);
			totalAmount = totalAmount.add(n.multiply(entity.getPrice()));
			paymentAmount = paymentAmount.add(n.multiply(entity.getPayPrice()));
		}
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
}
