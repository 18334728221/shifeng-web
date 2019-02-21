package com.xwd.securities.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 股票表
 */
public class Stock extends BaseEntity {
		
	private static final long serialVersionUID = -7070324046385780797L;
	//columns START
	//id
	private Long id;
	//编号
	private Long code;
	//名称
	private String name;
	//是否新股 0:否 1:是
	private Boolean isNew = false;
	//是否停牌 0:否 1:是
	private Boolean isSuspended = false;
	//最新价
	private BigDecimal price;
	//涨幅
	private Float rose = 0f;
	//流通盘
	private Long circulatingStock = 0L;
	//开盘价
	private BigDecimal openingPrice;
	//上一个交易日收盘价
	private BigDecimal closingPrice;
	//最高价
	private BigDecimal highestPrice;
	//最低价格
	private BigDecimal lowestPrice;
	//成交总金额
	private BigDecimal totalAmount;
	//成交量
	private Long volume;
	//涨跌幅
	private Float priceFluctuation;
	//振幅
	private Float amplitude;
	//换手率
	private Float turnoverRate;
	//涨跌价
	private BigDecimal changePrice;
	//外盘
	private Long outerAmount;
	//内盘
	private Long innerAmount;
	// 产品分类ID
	private Long categoryId;
	// 可申购总数
	private Long purchaseTotalAmount;
	//columns END
	
	//辅助字段
	//图片路径
	private String imageUrl = "";
	//涨停价
	private BigDecimal limitUpPrice = new BigDecimal(0);
	//跌停价
	private BigDecimal limitDownPrice = new BigDecimal(0);
	
	public Stock(){
	}

	public Stock(
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
	 * 编号
	 * @return
	 */
	public Long getCode() {
		return this.code;
	}
	
	/**
	 * 编号
	 * @param value
	 */
	public void setCode(Long value) {
		this.code = value;
	}
	/**
	 * 名称
	 * @return
	 */
	public String getName() {
		if(this.isNew){
			return "N" + this.name;
		}
		return this.name;
	}
	
	/**
	 * 名称
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}
	/**
	 * 是否新股 0:否 1:是
	 * @return
	 */
	public Boolean getIsNew() {
		return this.isNew;
	}
	
	/**
	 * 是否新股 0:否 1:是
	 * @param value
	 */
	public void setIsNew(Boolean value) {
		this.isNew = value;
	}
	/**
	 * 是否停牌 0:否 1:是
	 * @return
	 */
	public Boolean getIsSuspended() {
		return this.isSuspended;
	}
	
	/**
	 * 是否停牌 0:否 1:是
	 * @param value
	 */
	public void setIsSuspended(Boolean value) {
		this.isSuspended = value;
	}
	/**
	 * 最新价格
	 * @return
	 */
	public BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * 收盘价格
	 * @param value
	 */
	public void setPrice(BigDecimal value) {
		this.price = value;
	}
	
	public Float getRose() {
		return rose;
	}

	public void setRose(Float rose) {
		this.rose = rose;
	}
	
	public String getRoseString(){
		//DecimalFormat myformat= (DecimalFormat)NumberFormat.getPercentInstance(); 
		//myformat.applyPattern("0%"); 
		//myformat.setMaximumFractionDigits(2);
		return rose + "%"; 
	}

	/**
	 * 流通盘
	 * @return
	 */
	public Long getCirculatingStock() {
		return this.circulatingStock;
	}
	
	/**
	 * 流通盘
	 * @param value
	 */
	public void setCirculatingStock(Long value) {
		this.circulatingStock = value;
	}
	/**
	 * 开盘价
	 * @return
	 */
	public BigDecimal getOpeningPrice() {
		return this.openingPrice;
	}
	
	/**
	 * 开盘价
	 * @param value
	 */
	public void setOpeningPrice(BigDecimal value) {
		this.openingPrice = value;
	}
	/**
	 * 收盘价
	 * @return
	 */
	public BigDecimal getClosingPrice() {
		return this.closingPrice;
	}
	
	/**
	 * 收盘价
	 * @param value
	 */
	public void setClosingPrice(BigDecimal value) {
		this.closingPrice = value;
	}
	/**
	 * 最高价
	 * @return
	 */
	public BigDecimal getHighestPrice() {
		return this.highestPrice;
	}
	
	/**
	 * 最高价
	 * @param value
	 */
	public void setHighestPrice(BigDecimal value) {
		this.highestPrice = value;
	}
	/**
	 * 最低价格
	 * @return
	 */
	public BigDecimal getLowestPrice() {
		return this.lowestPrice;
	}
	
	/**
	 * 最低价格
	 * @param value
	 */
	public void setLowestPrice(BigDecimal value) {
		this.lowestPrice = value;
	}
	/**
	 * 成交总金额
	 * @return
	 */
	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}
	
	/**
	 * 成交总金额
	 * @param value
	 */
	public void setTotalAmount(BigDecimal value) {
		this.totalAmount = value;
	}
	/**
	 * 成交量
	 * @return
	 */
	public Long getVolume() {
		return this.volume;
	}
	
	/**
	 * 成交量
	 * @param value
	 */
	public void setVolume(Long value) {
		this.volume = value;
	}
	/**
	 * 振幅
	 * @return
	 */
	public Float getPriceFluctuation() {
		return this.priceFluctuation;
	}
	
	/**
	 * 振幅
	 * @param value
	 */
	public void setPriceFluctuation(Float value) {
		this.priceFluctuation = value;
	}
	
	public String getPriceFluctuationString() {
		return this.priceFluctuation + "%";
	}
	
	public Float getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(Float amplitude) {
		this.amplitude = amplitude;
	}
	
	public String getAmplitudeString() {
		return amplitude + "%";
	}

	/**
	 * 换手率
	 * @return
	 */
	public Float getTurnoverRate() {
		return this.turnoverRate;
	}
	
	/**
	 * 换手率
	 * @param value
	 */
	public void setTurnoverRate(Float value) {
		this.turnoverRate = value;
	}
	/**
	 * 涨跌价
	 * @return
	 */
	public BigDecimal getChangePrice() {
		return this.changePrice;
	}
	
	/**
	 * 涨跌价
	 * @param value
	 */
	public void setChangePrice(BigDecimal value) {
		this.changePrice = value;
	}
	/**
	 * 外盘
	 * @return
	 */
	public Long getOuterAmount() {
		return this.outerAmount;
	}
	
	/**
	 * 外盘
	 * @param value
	 */
	public void setOuterAmount(Long value) {
		this.outerAmount = value;
	}
	/**
	 * 内盘
	 * @return
	 */
	public Long getInnerAmount() {
		return this.innerAmount;
	}
	
	/**
	 * 内盘
	 * @param value
	 */
	public void setInnerAmount(Long value) {
		this.innerAmount = value;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public BigDecimal getLimitUpPrice() {
		return limitUpPrice;
	}

	public void setLimitUpPrice(BigDecimal limitUpPrice) {
		this.limitUpPrice = limitUpPrice;
	}

	public BigDecimal getLimitDownPrice() {
		return limitDownPrice;
	}

	public void setLimitDownPrice(BigDecimal limitDownPrice) {
		this.limitDownPrice = limitDownPrice;
	}

	/**
	 * 初始化盘面信息
	 */
	public void init(){
		this.closingPrice = this.price;
		//设置涨停跌停价
		if(this.rose > 0f){
			BigDecimal p = this.closingPrice.multiply(new BigDecimal(this.rose));
			BigDecimal up = p.setScale(2, BigDecimal.ROUND_CEILING);
			BigDecimal down = p.setScale(2, BigDecimal.ROUND_DOWN);
			this.limitUpPrice = this.closingPrice.add(up);
			this.limitDownPrice = this.closingPrice.subtract(down);
		}
		this.price = new BigDecimal(0);
		this.openingPrice = new BigDecimal(0);
		this.highestPrice = new BigDecimal(0);
		this.lowestPrice = new BigDecimal(0);
		this.totalAmount = new BigDecimal(0);
		this.volume = 0L;
		this.amplitude = 0f;
		this.priceFluctuation = 0f;
		this.turnoverRate = 0f;
		this.changePrice = new BigDecimal(0);
		this.outerAmount = 0L;
		this.innerAmount = 0L;
	}
	
	/**
	 * 是否超过涨停价
	 * @param price
	 * @return
	 */
	public boolean isOverLimitUpPrice(BigDecimal price){
		if(this.rose > 0){
			if(this.limitUpPrice.compareTo(price) < 0){
				return true;
			}
		}
		return false;
	}

	public Long getPurchaseTotalAmount() {
		return purchaseTotalAmount;
	}

	public void setPurchaseTotalAmount(Long purchaseTotalAmount) {
		this.purchaseTotalAmount = purchaseTotalAmount;
	}
	
	
	
}

