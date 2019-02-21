package com.xwd.product.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl 产品库存表
 */
public class Sku extends BaseEntity {

	private static final long serialVersionUID = -6609855733878593667L;
	
	public static final Byte PUTAWAY_STATUS_PENDING = 0;// 未上架-待商品审核
	public static final Byte PUTAWAY_STATUS_SHELVES = 1;// 已上架
	public static final Byte PUTAWAY_STATUS_SOLD_OUT = 2;// 售罄下架
	public static final Byte PUTAWAY_STATUS_EXPIRE = 3;//已下架
	public static final Byte PUTAWAY_STATUS_INVALID = 4;
	
	// columns START
	// id
	private Long id;
	// 产品ID
	private Long productCode;
	// sku码
	private String sku;
	// 属性值, 逗号分隔
	private String av;
	// 总库存量
	private Long totalStock;
	// 库存
	private Integer stock;
	// 上架标识（0 未上架-待商品审核；1 已上架；2 售罄下架；3 已下架；4 无效作废）
	private Byte putaway;
	// 图片ID
	private String imageId;
	// 图片服务器ID
	private Long imageServerId;
	// columns END
	// 属性值, 逗号分隔
	
	private String productName;
	private String avName;
	
	private String imageUrl;
	
	public Sku() {
	}

	public Sku(Long id) {
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
	 * 属性值, 逗号分隔
	 * 
	 * @return
	 */
	public String getSku() {
		return this.sku;
	}

	/**
	 * 属性值, 逗号分隔
	 * 
	 * @param value
	 */
	public void setSku(String value) {
		this.sku = value;
	}

	/**
	 * 库存
	 * 
	 * @return
	 */
	public Integer getStock() {
		return this.stock;
	}

	/**
	 * 库存
	 * 
	 * @param value
	 */
	public void setStock(Integer value) {
		this.stock = value;
	}

	/**
	 * 上架标识（0 未上架-待商品审核；1 已上架；2 售罄下架；3 已下架；4 无效作废）
	 * 
	 * @return
	 */
	public Byte getPutaway() {
		return this.putaway;
	}

	/**
	 * 上架标识（0 未上架-待商品审核；1 已上架；2 售罄下架；3 已下架；4 无效作废）
	 * 
	 * @param value
	 */
	public void setPutaway(Byte value) {
		this.putaway = value;
	}

	/**
	 * 图片ID
	 * 
	 * @return
	 */
	public String getImageId() {
		return this.imageId;
	}

	/**
	 * 图片ID
	 * 
	 * @param value
	 */
	public void setImageId(String value) {
		this.imageId = value;
	}

	/**
	 * 图片服务器ID
	 * 
	 * @return
	 */
	public Long getImageServerId() {
		return this.imageServerId;
	}

	/**
	 * 图片服务器ID
	 * 
	 * @param value
	 */
	public void setImageServerId(Long value) {
		this.imageServerId = value;
	}

	public String getAv() {
		return av;
	}

	public void setAv(String av) {
		this.av = av;
	}

	public Long getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Long totalStock) {
		this.totalStock = totalStock;
	}

	public String getAvName() {
		return avName;
	}

	public void setAvName(String avName) {
		this.avName = avName;
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
	
}
