package com.xwd.mall.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.frame.base.BaseEntity;

/**
 * @author ljl 客户商品订单
 */
public class GoodsOrder extends BaseEntity {

	private static final long serialVersionUID = -7977844021799115372L;
	public static final Byte GOODS_ORDER_STATUS_CREATE = 0;
	public static final Byte GOODS_ORDER_STATUS_CANCEL = 1;
	public static final Byte ORDER_PAY_STATUS_NOT = 0;
	public static final Byte ORDER_PAY_STATUS_FINISH = 1;
	public static final Byte GOODS_ORDER_PAY_WEY_BALANCE = 0;
	public static final Byte GOODS_ORDER_PAY_WEY_WECHA = 1;
	public static final Byte GOODS_ORDER_PAY_WEY_ALIPAY = 2;
	// columns START
	// id
	private Long id;
	// 订单号
	private String orderNo;
	// 交易码
	private String tradeCode;
	// customerNo
	private Long customerNo;
	// 收货地址id
	private Long addressId;
	// 总金额
	private BigDecimal amount;
	// 支付金额
	private BigDecimal paymentAmount;
	// 支付方式  默认：0   1:微信2:支付宝
	private Byte payWay;
	// 支付状态 0:未支付 1:已支付 
	private Byte payStatus;
	// 订单状态 0:创建 1:取消
	private Byte orderStatus;
	// 地区标识
	private Long areaPlatMark;
	// 支付时间
	private Calendar payTime;
	// columns END
	// 支付方式 0:微信 1:支付宝
	private String payWayString;
	// 订单状态
	private String orderStatusString;
	// 支付状态 0:未支付 1:已支付 2:
	private String payStatusString;

	private List<OrderItem> itemList;

	public GoodsOrder() {
	}

	public GoodsOrder(Long id) {
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
	 * 订单号
	 * 
	 * @return
	 */
	public String getOrderNo() {
		return this.orderNo;
	}

	/**
	 * 订单号
	 * 
	 * @param value
	 */
	public void setOrderNo(String value) {
		this.orderNo = value;
	}

	/**
	 * 交易码
	 * 
	 * @return
	 */
	public String getTradeCode() {
		return this.tradeCode;
	}

	/**
	 * 交易码
	 * 
	 * @param value
	 */
	public void setTradeCode(String value) {
		this.tradeCode = value;
	}

	/**
	 * customerNo
	 * 
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}

	/**
	 * customerNo
	 * 
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}

	/**
	 * 总金额
	 * 
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}

	/**
	 * 总金额
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
	 * 支付方式 0:微信 1:支付宝
	 * 
	 * @return
	 */
	public Byte getPayWay() {
		return this.payWay;
	}

	/**
	 * 支付方式 0:微信 1:支付宝
	 * 
	 * @param value
	 */
	public void setPayWey(Byte value) {
		this.payWay = value;
	}

	/**
	 * 支付状态 0:未支付 1:已支付 2:
	 * 
	 * @return
	 */
	public Byte getPayStatus() {
		return this.payStatus;
	}

	/**
	 * 支付状态 0:未支付 1:已支付 2:
	 * 
	 * @param value
	 */
	public void setPayStatus(Byte value) {
		this.payStatus = value;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 地区标识
	 * 
	 * @return
	 */
	public Long getAreaPlatMark() {
		return this.areaPlatMark;
	}

	/**
	 * 地区标识
	 * 
	 * @param value
	 */
	public void setAreaPlatMark(Long value) {
		this.areaPlatMark = value;
	}

	/**
	 * 支付时间
	 * 
	 * @return
	 */
	public String getPayTimeString() {
		return date2String(getPayTime(), DATE_TIME_FORMAT);
	}

	/**
	 * 支付时间
	 * 
	 * @param value
	 */
	public void setPayTimeString(String value) {
		setPayTime(string2Date(value, DATE_TIME_FORMAT));
	}

	/**
	 * 支付时间
	 * 
	 * @return
	 */
	public Calendar getPayTime() {
		return this.payTime;
	}

	/**
	 * 支付时间
	 * 
	 * @param value
	 */
	public void setPayTime(Calendar value) {
		this.payTime = value;
	}

	public List<OrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItem> itemList) {
		this.itemList = itemList;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	// 支付方式 0:微信 1:支付宝
	public String getPayWayString() {
		String payWayString = "";
		if (payWay != null) {
			if (payWay == 0) {
				payWayString = "微信";
			} else if (payWay == 1) {
				payWayString = "支付宝";
			}
		}
		return payWayString;
	}

	public void setPayWayString(String payWayString) {
		this.payWayString = payWayString;
	}

	// 支付状态 0:未支付 1:已支付 2:
	public String getPayStatusString() {
		if (this.payStatus != null) {
			if (this.payStatus == 0) {
				payStatusString = "未支付";
			} else if (this.payStatus == 1) {
				payStatusString = "已支付";
			}
		}
		return payStatusString;
	}

	public void setPayStatusString(String payStatusString) {
		this.payStatusString = payStatusString;
	}

	// 订单状态 0:创建 1:取消
	public String getOrderStatusString() {
		String orderStatusString = "";
		if (this.orderStatus != null) {
			if (this.orderStatus == 0) {
			} else if (this.orderStatus == 1) {
				orderStatusString = "取消";
			}
		}
		return orderStatusString;
	}

	public void setOrderStatusString(String orderStatusString) {
		this.orderStatusString = orderStatusString;
	}

}
