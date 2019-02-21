package com.xwd.customer.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 持仓表
 */
public class CustomerProduct extends BaseEntity {
		
	private static final long serialVersionUID = 5889041798543882526L;
	//columns START
	//id
	private Long id;
	//产品编号
	private Long productCode;
	//customerNo
	private Long customerNo;
	//总数量
	private Long totalNum = 0L;
	//当前价格
	private BigDecimal price = new BigDecimal(0);
	//摊薄持仓成本 成本价=(累计买入金额 + 当然买入金额 - 累计卖出金额 - 当日卖出金额)/证券余额
	private BigDecimal cost = new BigDecimal(0);
	//可卖数量
	private Long sellNum = 0L;
	//买累计手续费
	private BigDecimal buyPoundage = new BigDecimal(0);
	//卖累计手续费
	private BigDecimal sellPoundage = new BigDecimal(0);
	//买入累计总成本,不含手续费
	private BigDecimal buyCost = new BigDecimal(0);
	//累计套现资金,不含手续费
	private BigDecimal cashFund = new BigDecimal(0);
	//columns END
	
	private String imageUrl;
	private String productName;
	
	private BigDecimal profitAndLoss;
	private BigDecimal marketValue;

	public CustomerProduct(){
	}

	public CustomerProduct(
		Long id
	){
		this.id = id;
	}

	/**
	 * id
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * id
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}
	/**
	 * productCode
	 * @return
	 */
	public Long getProductCode() {
		return this.productCode;
	}
	
	/**
	 * productCode
	 * @param value
	 */
	public void setProductCode(Long value) {
		this.productCode = value;
	}
	/**
	 * customerNo
	 * @return
	 */
	public Long getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * customerNo
	 * @param value
	 */
	public void setCustomerNo(Long value) {
		this.customerNo = value;
	}
	/**
	 * 总数量
	 * @return
	 */
	public Long getTotalNum() {
		return this.totalNum;
	}
	
	/**
	 * 总数量
	 * @param value
	 */
	public void setTotalNum(Long value) {
		this.totalNum = value;
	}
	/**
	 * 当前价格
	 * @return
	 */
	public BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * 当前价格
	 * @param value
	 */
	public void setPrice(BigDecimal value) {
		this.price = value;
	}
	/**
	 * 持仓成本 摊薄成本算法
	 * 成本价=(累计买入金额 + 当然买入金额 - 累计卖出金额 - 当日卖出金额)/证券余额
	 * @return
	 */
	public BigDecimal getCost() {
		BigDecimal c = this.buyCost.add(this.buyPoundage);
		c = c.subtract(this.cashFund);
		this.cost = c.divide(new BigDecimal(this.totalNum), 2, BigDecimal.ROUND_CEILING);
		return this.cost;
	}
	
	/**
	 * 持仓成本
	 * @param value
	 */
	public void setCost(BigDecimal value) {
		this.cost = value;
	}
	/**
	 * 可卖数量
	 * @return
	 */
	public Long getSellNum() {
		return this.sellNum;
	}
	
	/**
	 * 可卖数量
	 * @param value
	 */
	public void setSellNum(Long value) {
		this.sellNum = value;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getBuyPoundage() {
		return buyPoundage;
	}

	public void setBuyPoundage(BigDecimal buyPoundage) {
		this.buyPoundage = buyPoundage;
	}

	public BigDecimal getSellPoundage() {
		return sellPoundage;
	}

	public void setSellPoundage(BigDecimal sellPoundage) {
		this.sellPoundage = sellPoundage;
	}

	public BigDecimal getBuyCost() {
		return buyCost;
	}

	public void setBuyCost(BigDecimal buyCost) {
		this.buyCost = buyCost;
	}

	public BigDecimal getCashFund() {
		return cashFund;
	}

	public void setCashFund(BigDecimal cashFund) {
		this.cashFund = cashFund;
	}
	
	/**
	 * 盈亏
	 * 盈亏金额=(市值-卖出费用+累计卖出清算金额+当日卖出清算金额)-(累计买入清算金额+当日买入清算金额)
	 * @return
	 */
	public BigDecimal getProfitAndLoss(){
		BigDecimal result = new BigDecimal(0);
		//当前市值
		result = this.price.multiply(new BigDecimal(this.totalNum));
		result = result.add(this.cashFund);
		result = result.subtract(this.buyCost);
		result = result.subtract(this.buyPoundage);
		this.profitAndLoss = result;
		return profitAndLoss;
	}
	
	/**
	 * 获得当前市值
	 * @return
	 */
	public BigDecimal getMarketValue(){
		this.marketValue = this.price.multiply(new BigDecimal(this.totalNum));
		return marketValue;
	}
	
	
	public void print(){
		System.out.println("--------- 持仓信息 ------------");
		System.out.println("客户编号>>>>>>>" + customerNo);
		System.out.println("sellNum>>>>>>>" + sellNum);
		System.out.println("buyPoundage>>>>>>>" + buyPoundage);
		System.out.println("sellPoundage>>>>>>>" + sellPoundage);
		System.out.println("buyCost>>>>>>>" + buyCost);
		System.out.println("cashFund>>>>>>>" + cashFund);
	}
}

