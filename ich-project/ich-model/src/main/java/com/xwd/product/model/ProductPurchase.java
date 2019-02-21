package com.xwd.product.model;

import java.math.BigDecimal;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 申购表
 */
public class ProductPurchase extends BaseEntity {
		
	private static final long serialVersionUID = 4444886856511991789L;
	
	public static final byte PURCHASE_STATUS_APPLY = 0;// 申购
	public static final byte PURCHASE_STATUS_PART_BID = 1;// 部分中签
	public static final byte PURCHASE_STATUS_ALL_BID = 2;// 全部中签
	public static final byte PURCHASE_STATUS_CANCEL = 3;// 撤单
	
	public static long bidNumInfo = 0;
	//columns START
	//id
	private Long id;
	//交易号
	private String txNo;
	//productCode
	private Long productCode;
	//productName
	private String productName;
	//productName
	private BigDecimal purchasePrice;
	//customerNo
	private Long customerNo;
	//申购数量
	private Long purchaseNum;
	//中签数
	private Long bidNum;
	//0:申购 1:部分中签 2:全部中签 3:撤单
	private Byte purchaseStatus;
	//columns END
	private String imageUrl;
	public ProductPurchase(){
	}

	public ProductPurchase(
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
	 * 交易号
	 * @return
	 */
	public String getTxNo() {
		return this.txNo;
	}
	
	/**
	 * 交易号
	 * @param value
	 */
	public void setTxNo(String value) {
		this.txNo = value;
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
	 * 申购数量
	 * @return
	 */
	public Long getPurchaseNum() {
		return this.purchaseNum;
	}
	
	/**
	 * 申购数量
	 * @param value
	 */
	public void setPurchaseNum(Long value) {
		this.purchaseNum = value;
	}
	/**
	 * 中签数
	 * @return
	 */
	public Long getBidNum() {
		return this.bidNum;
	}
	
	/**
	 * 中签数
	 * @param value
	 */
	public void setBidNum(Long value) {
		this.bidNum = value;
	}
	/**
	 * 0:申购 1:部分中签 2:全部中签 3:撤单
	 * @return
	 */
	public Byte getPurchaseStatus() {
		return this.purchaseStatus;
	}
	
	/**
	 * 0:申购 1:部分中签 2:全部中签 3:撤单
	 * @param value
	 */
	public void setPurchaseStatus(Byte value) {
		this.purchaseStatus = value;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPurchasePrice() { 
		return purchasePrice;
	} 

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}

