package com.xwd.product.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl 产品表
 */
public class Product extends BaseEntity {

	private static final long serialVersionUID = 7894452823489392065L;
	// columns START
	// id
	private Long id;
	// 产品名称
	private String name;
	// 产品代码
	private Long code;
	//产品属性编码
	private Long categoryCode;
	// 价格
	private BigDecimal price;
	//促销价格
	private BigDecimal promotionPrice;
	// 总股本
	private Long totalShareCapital;
	// 手艺人编号
	private Long craftsmanNo;
	// 产品分类ID
	private Long categoryId;
	// 库存量
	private Long stock;
	// 流通总量
	private Long circulatingStock;
	// 是否置顶
	private Boolean isTop;
	//是否最热
	private Boolean isHot;
	//是否最新
	private Boolean isNew;
	// 是否进入交易所交易 0:否 1:是
	private Boolean isInExchange;
	//描述
	private String description;

	private String imageId;
	private Long imageServerId;
	// columns END
	private String imageUrl;
	
	//购买的商品，购物车里面判断是否同一产品
	private String sku;
	
	// 手艺人名称
	private String craftsmanName;
	// 产品分类名称
	private String categoryName;
	// 是否置顶
	private String isTopString;
	//是否最热
	private String isHotString;
	//是否最新
	private String isNewString;
	// 是否进入交易所交易 0:否 1:是
	private String isInExchangeString;
	//收藏次数
	private String countCollection;
	//评价次数
	private String countEvaluate;
	//涨跌幅
	private Float priceFluctuation;

	public Product() {
	}

	public Product(Long id) {
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
	 * 产品名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 产品名称
	 * 
	 * @param value
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * 产品代码
	 * 
	 * @return
	 */
	public Long getCode() {
		return this.code;
	}

	/**
	 * 产品代码
	 * 
	 * @param value
	 */
	public void setCode(Long value) {
		this.code = value;
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
	
	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	/**
	 * 总股本
	 * 
	 * @return
	 */
	public Long getTotalShareCapital() {
		return this.totalShareCapital;
	}

	/**
	 * 总股本
	 * 
	 * @param value
	 */
	public void setTotalShareCapital(Long value) {
		this.totalShareCapital = value;
	}

	public Long getCraftsmanNo() {
		return craftsmanNo;
	}

	public void setCraftsmanNo(Long craftsmanNo) {
		this.craftsmanNo = craftsmanNo;
	}

	/**
	 * 产品分类ID
	 * 
	 * @return
	 */
	public Long getCategoryId() {
		return this.categoryId;
	}

	/**
	 * 产品分类ID
	 * 
	 * @param value
	 */
	public void setCategoryId(Long value) {
		this.categoryId = value;
	}

	/**
	 * 是否置顶
	 * 
	 * @return
	 */
	public Boolean getIsTop() {
		return this.isTop;
	}

	/**
	 * 是否置顶
	 * 
	 * @param value
	 */
	public void setIsTop(Boolean value) {
		this.isTop = value;
	}
	
	public Boolean getIsHot() {
		return isHot;
	}

	public void isHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void isNew(Boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * 是否进入交易所交易 0:否 1:是
	 * 
	 * @return
	 */
	public Boolean getIsInExchange() {
		return this.isInExchange;
	}

	/**
	 * 是否进入交易所交易 0:否 1:是
	 * 
	 * @param value
	 */
	public void setIsInExchange(Boolean value) {
		this.isInExchange = value;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Long getImageServerId() {
		return imageServerId;
	}

	public void setImageServerId(Long imageServerId) {
		this.imageServerId = imageServerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getCirculatingStock() {
		return circulatingStock;
	}

	public void setCirculatingStock(Long circulatingStock) {
		this.circulatingStock = circulatingStock;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCraftsmanName() {
		return craftsmanName;
	}

	public void setCraftsmanName(String craftsmanName) {
		this.craftsmanName = craftsmanName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIsTopString() {
		String isTopString = "";
		if(this.isTop!=null){
			if(this.isTop == true){
				isTopString = "是";
			}else{
				isTopString = "否";
			}
		}
		return isTopString;
	}


	public String getIsHotString() {
		String isHotString = "";
		if(this.isHot!=null){
			if(this.isHot == true){
				isHotString = "是";
			}else{
				isHotString = "否";
			}
		}
		return isHotString;
	}

	public String getIsNewString() {
		String isNewString = "";
		if(this.isNew!=null){
			if(this.isNew == true){
				isNewString = "是";
			}else{
				isNewString = "否";
			}
		}
		return isNewString;
	}

	public String getIsInExchangeString() {
		String isInExchangeString = "";
		if(this.isInExchange!=null){
			if(this.isInExchange == true){
				isInExchangeString = "是";
			}else{
				isInExchangeString = "否";
			}
		}
		return isInExchangeString;
	}

	public void setIsTopString(String isTopString) {
		this.isTopString = isTopString;
	}

	public void setIsHotString(String isHotString) {
		this.isHotString = isHotString;
	}

	public void setIsNewString(String isNewString) {
		this.isNewString = isNewString;
	}

	public void setIsInExchangeString(String isInExchangeString) {
		this.isInExchangeString = isInExchangeString;
	}

	public String getCountCollection() {
		return countCollection;
	}

	public void setCountCollection(String countCollection) {
		this.countCollection = countCollection;
	}

	public String getCountEvaluate() {
		return countEvaluate;
	}

	public void setCountEvaluate(String countEvaluate) {
		this.countEvaluate = countEvaluate;
	}

	public Long getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(Long categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Float getPriceFluctuation() {
		return priceFluctuation;
	}

	public void setPriceFluctuation(Float priceFluctuation) {
		this.priceFluctuation = priceFluctuation;
	}
	
}
