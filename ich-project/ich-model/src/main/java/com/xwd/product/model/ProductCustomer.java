package com.xwd.product.model;

import java.math.BigDecimal;
import java.util.Calendar;

import com.frame.base.BaseEntity;

/**
 * @author ljl 产品会员指定表
 */
public class ProductCustomer extends BaseEntity {

	private static final long serialVersionUID = -5308969462814122375L;
	// columns START
	// id
	private Long id;
	// productCode
	private Long productCode;
	// customerNo
	private Long customerNo;
	// 股票数量
	private Long sharesNumber;
	// price
	private BigDecimal price;
	// 购买时间
	private Calendar buyTime;
	// 可卖时间
	private Calendar sellTime;
	// columns END
	
	// createTime
	private Calendar createTime;
	// updateTime
	private Calendar updateTime;

	public ProductCustomer() {
	}

	public ProductCustomer(Long id) {
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
	 * productCode
	 * 
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}

	/**
	 * productCode
	 * 
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
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
	 * 股票数量
	 * 
	 * @return
	 */
	public Long getSharesNumber() {
		return this.sharesNumber;
	}

	/**
	 * 股票数量
	 * 
	 * @param value
	 */
	public void setSharesNumber(Long value) {
		this.sharesNumber = value;
	}

	/**
	 * price
	 * 
	 * @return
	 */
	public BigDecimal getPrice() {
		return this.price;
	}

	/**
	 * price
	 * 
	 * @param value
	 */
	public void setPrice(BigDecimal value) {
		this.price = value;
	}

	/**
	 * buyTime
	 * 
	 * @return
	 */
	public String getBuyTimeString() {
		return date2String(getBuyTime(), DATE_TIME_FORMAT);
	}

	/**
	 * buyTime
	 * 
	 * @param value
	 */
	public void setBuyTimeString(String value) {
		setBuyTime(string2Date(value, DATE_TIME_FORMAT));
	}

	/**
	 * buyTime
	 * 
	 * @return
	 */
	public Calendar getBuyTime() {
		return this.buyTime;
	}

	/**
	 * buyTime
	 * 
	 * @param value
	 */
	public void setBuyTime(Calendar value) {
		this.buyTime = value;
	}

	/**
	 * sellTime
	 * 
	 * @return
	 */
	public String getSellTimeString() {
		return date2String(getSellTime(), DATE_TIME_FORMAT);
	}

	/**
	 * sellTime
	 * 
	 * @param value
	 */
	public void setSellTimeString(String value) {
		setSellTime(string2Date(value, DATE_TIME_FORMAT));
	}

	/**
	 * sellTime
	 * 
	 * @return
	 */
	public Calendar getSellTime() {
		return this.sellTime;
	}

	/**
	 * sellTime
	 * 
	 * @param value
	 */
	public void setSellTime(Calendar value) {
		this.sellTime = value;
	}

	/**
	 * createTime
	 * 
	 * @return
	 */
	public String getCreateTimeString() {
		return date2String(getCreateTime(), DATE_TIME_FORMAT);
	}

	/**
	 * createTime
	 * 
	 * @param value
	 */
	public void setCreateTimeString(String value) {
		setCreateTime(string2Date(value, DATE_TIME_FORMAT));
	}

	/**
	 * createTime
	 * 
	 * @return
	 */
	public Calendar getCreateTime() {
		return this.createTime;
	}

	/**
	 * createTime
	 * 
	 * @param value
	 */
	public void setCreateTime(Calendar value) {
		this.createTime = value;
	}

	/**
	 * updateTime
	 * 
	 * @return
	 */
	public String getUpdateTimeString() {
		return date2String(getUpdateTime(), DATE_TIME_FORMAT);
	}

	/**
	 * updateTime
	 * 
	 * @param value
	 */
	public void setUpdateTimeString(String value) {
		setUpdateTime(string2Date(value, DATE_TIME_FORMAT));
	}

}
