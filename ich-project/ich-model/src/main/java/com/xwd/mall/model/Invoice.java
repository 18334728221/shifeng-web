package com.xwd.mall.model;

import com.frame.base.BaseEntity;

/**
 * @author ljl
 * 发票
 */
public class Invoice extends BaseEntity {
		
	private static final long serialVersionUID = -7433281832425285200L;
	//columns START
	//id
	private Long id;
	//订单编号
	private Long orderNo;
	//userId
	private Long userId;
	private Long customerNo;
	//发票类型（0 不要发票；1 普通定额发票；2 机打发票；3 打印增值税发票；）
	private Byte type;
	//发票类型（0 不要发票；1 普通定额发票；2 机打发票；3 打印增值税发票；）
	private String typeString;
	//发票抬头
	private String title;
	//发票内容
	private String content;
	//columns END

	public Invoice(){
	}

	public String getTypeString() {
		if(this.type!=null){
			if(this.type==0){
				typeString = "不要发票";
			}else if(this.type==1){
				typeString = "普通定额发票";
			}else if(this.type==2){
				typeString = "机打发票";
			}else if(this.type==3){
				typeString = "打印增值税发票";
			}
		}
		return typeString;
	}
	

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public Invoice(
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
	 * 订单编号
	 * @return
	 */
	public Long getOrderNo() {
		return this.orderNo;
	}
	
	/**
	 * 订单编号
	 * @param value
	 */
	public void setOrderNo(Long value) {
		this.orderNo = value;
	}
	/**
	 * userId
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * userId
	 * @param value
	 */
	public void setUserId(Long value) {
		this.userId = value;
	}
	/**
	 * 发票类型（0 不要发票；1 普通定额发票；2 机打发票；3 打印增值税发票；）
	 * @return
	 */
	public Byte getType() {
		return this.type;
	}
	
	/**
	 * 发票类型（0 不要发票；1 普通定额发票；2 机打发票；3 打印增值税发票；）
	 * @param value
	 */
	public void setType(Byte value) {
		this.type = value;
	}
	/**
	 * 发票抬头
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 发票抬头
	 * @param value
	 */
	public void setTitle(String value) {
		this.title = value;
	}
	/**
	 * 发票内容
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * 发票内容
	 * @param value
	 */
	public void setContent(String value) {
		this.content = value;
	}

	public Long getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}
	
}

