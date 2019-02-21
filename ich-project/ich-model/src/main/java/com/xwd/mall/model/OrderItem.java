package com.xwd.mall.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl 订单项目表
 */
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = -548669642956913672L;
	//0:待支付
	public static final Byte ORDER_ITEM_FLOW_WAIT_PAY = 0;
	//1:待发货 
	public static final Byte ORDER_ITEM_FLOW_WAIT_DELIVER = 1;
	// 2:待收货
	public static final Byte ORDER_ITEM_FLOW_WAIT_RECEIPT = 2;
	//3:待评价
	public static final Byte ORDER_ITEM_FLOW_WAIT_EVALUATE = 3;
	// 4:撤单
	public static final Byte ORDER_ITEM_FLOW_WAIT_CANCEL = 4;
	// columns START
	// id
	private Long id;
	// 订单ID
	private Long orderId;
	// 订单编号
	private String orderNo;
	// 顾客编号
	private Long customerNo;
	// 金额
	private BigDecimal amount;
	// 支付金额
	private BigDecimal paymentAmount;
	// 产品ID
	private Long productCode;
	//产品名称
	private String productName;
	// 产品存货ID
	private Long productSkuId;
	// 购买数量
	private Integer num;
	// 订单项流程 0:待支付 1:待发货 2:待收货 3:待评价 4:已撤单
	private Byte flow;
	// columns END
	private String flowString;
	
	// 购买的商品，购物车里面判断是否同一产品
	private String sku;
	//单价
	private BigDecimal price;
	//支付单价
	private BigDecimal payPrice;
	
	
	//产品型号
	private String avName;
	private String shopName;
	private String imageUrl;
	
	public OrderItem() {
	}

	public OrderItem(Long id) {
		this.id = id;
	}

	/**
	 * id
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * id
	 * 
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}

	/**
	 * 订单ID
	 * 
	 * @return
	 */
	public Long getOrderId() {
		return this.orderId;
	}

	/**
	 * 订单ID
	 * 
	 * @param value
	 */
	public void setOrderId(Long value) {
		this.orderId = value;
	}

	/**
	 * 订单编号
	 * 
	 * @return
	 */
	public String getOrderNo() {
		return this.orderNo;
	}

	/**
	 * 订单编号
	 * 
	 * @param value
	 */
	public void setOrderNo(String value) {
		this.orderNo = value;
	}

	/**
	 * 金额
	 * 
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}

	/**
	 * 金额
	 * 
	 * @param value
	 */
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}

	/**
	 * 支付金额
	 * 
	 * @return
	 */
	public BigDecimal getPaymentAmount() {
		return this.paymentAmount;
	}

	/**
	 * 支付金额
	 * 
	 * @param value
	 */
	public void setPaymentAmount(BigDecimal value) {
		this.paymentAmount = value;
	}

	/**
	 * 产品ID
	 * 
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}

	/**
	 * 产品ID
	 * 
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}

	/**
	 * 产品存货ID
	 * 
	 * @return
	 */
	public Long getProductSkuId() {
		return this.productSkuId;
	}

	/**
	 * 产品存货ID
	 * 
	 * @param value
	 */
	public void setProductSkuId(Long value) {
		this.productSkuId = value;
	}

	/**
	 * 购买数量
	 * 
	 * @return
	 */
	public Integer getNum() {
		return this.num;
	}

	/**
	 * 购买数量
	 * 
	 * @param value
	 */
	public void setNum(Integer value) {
		this.num = value;
	}
	
	/*public void addNum(Integer value){
		if(this.num == null){
			this.num = 0;
		}
		this.num += value;
	}*/

	/**
	 * 订单项流程
	 * 
	 * @return
	 */
	public Byte getFlow() {
		return this.flow;
	}

	/**
	 * 订单项流程
	 * 
	 * @param value
	 */
	public void setFlow(Byte value) {
		this.flow = value;
	}
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public Long getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAvName() {
		return avName;
	}

	public void setAvName(String avName) {
		this.avName = avName;
	}
	public String getFlowString() {
		if(this.flow!=null){
			if(this.flow==0){
				flowString = "待支付";
			}else if(this.flow==1){
				flowString = "待发货";
			}else if(this.flow==2){
				flowString = "待收货";
			}else if(this.flow==3){
				flowString = "待评价";
			}else if(this.flow==4){
				flowString = "已撤单";
			}
		}
		return flowString;
	}

	public void setFlowString(String flowString) {
		this.flowString = flowString;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
