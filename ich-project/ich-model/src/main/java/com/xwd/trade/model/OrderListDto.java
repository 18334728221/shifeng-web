package com.xwd.trade.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/*
 * 订单列表
 */
public class OrderListDto extends BaseEntity {
	
	private static final long serialVersionUID = -3936517837933393396L;
	//产品编号
	private Long productCode;
	private String productName;
	//订单id
	private Long orderId;
	//收货人
	private String customerName;
	private String address;
	private String moblie;
	//商铺名称
	private String shopName;
	//手艺人名称
	private String cratSmaName;
	//手艺人手机号 
	private String craftMobile;
	//产品描述
	private String proDescrip;
	//订单状态
	private Byte flow;
	//订单号
	private String orderNo;
	//运单号
	private String trackingNo;
	//购买数量
	private int num;
	private BigDecimal Amount;
	//支付金额
	private BigDecimal paymentAmount;
	//单价
	private BigDecimal price;
	//支付方式
	private int payWay;
	//订单状态
	private String orderType;
	//订单状态
	private String tipInfo;
	//头像
	private String HeadImageUrl;
	//订单留言
	private String note;
	//产品图片
	private String imageUrl;
	
	private String avName;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCratSmaName() {
		return cratSmaName;
	}
	public void setCratSmaName(String cratSmaName) {
		this.cratSmaName = cratSmaName;
	}
	public String getProDescrip() {
		return proDescrip;
	}
	public void setProDescrip(String proDescrip) {
		this.proDescrip = proDescrip;
	}
	public Byte getFlow() {
		return flow;
	}
	public void setFlow(Byte flow) {
		this.flow = flow;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public BigDecimal getAmount() {
		return Amount;
	}
	public void setAmount(BigDecimal amount) {
		Amount = amount;
	}
	public String getHeadImageUrl() {
		return HeadImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		HeadImageUrl = headImageUrl;
	}
	
	public Long getProductCode() {
		return productCode;
	}
	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getOrderType() {
		if(this.flow!=null){
			if(this.flow==0){
				orderType = "待支付";
			}else if(this.flow==1){
				orderType = "待发货";
			}else if(this.flow==2){
				orderType = "待收货";
			}else if(this.flow==3){
				orderType = "待评价";
			}else if(this.flow==4){
				orderType = "已撤单";
			}
		}
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getTipInfo() {
		if(this.flow!=null){
			if(this.flow==0){
				tipInfo = "等待买家付款";
			}else if(this.flow==1){
				tipInfo = "等待卖家发货";
			}else if(this.flow==2){
				tipInfo = "卖家已发货";
			}else if(this.flow==3){
				tipInfo = "交易成功";
			}
		}
		return tipInfo;
	}
	public void setTipInfo(String tipInfo) {
		this.tipInfo = tipInfo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMoblie() {
		return moblie;
	}
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAvName() {
		return avName;
	}
	public void setAvName(String avName) {
		this.avName = avName;
	}
	public String getCraftMobile() {
		return craftMobile;
	}
	public void setCraftMobile(String craftMobile) {
		this.craftMobile = craftMobile;
	}
	
}
