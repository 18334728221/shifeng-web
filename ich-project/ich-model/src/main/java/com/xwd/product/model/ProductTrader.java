package com.xwd.product.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 产品操盘手表
 */
/**
 * @author dong
 *
 */
public class ProductTrader extends BaseEntity {
		
	
	private static final long serialVersionUID = 682343979812243728L;
	//columns START
	//id
	private Long id;
	//productCode
	private String categoryName;
	//productCode
	private Long productCode;
	//customerNo
	private Long customerNo;
	//持股量
	private Long bidNum;
	//总剩余量
	private Long surplusNum;
	
	//columns END

	public ProductTrader(){
	}

	public ProductTrader(
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

	public Long getBidNum() {
		return bidNum;
	}

	public void setBidNum(Long bidNum) {
		this.bidNum = bidNum;
	}

	public Long getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(Long surplusNum) {
		this.surplusNum = surplusNum;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}

