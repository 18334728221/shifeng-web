package com.xwd.msg.model;


import com.frame.base.BaseEntity;
import com.xwd.bean.MailMessage;

/**
 * @author ljl
 * 顾客邮件表
 */
public class EmailCustomer extends BaseEntity {
		
	private static final long serialVersionUID = -635047468927510048L;
	//columns START
	//id
	private Long id;
	//customerNo
	private Long customerNo;
	//老师姓名
	private String customerName;
	//邮件标题
	private String title;
	//地区标识
	private Long areaPlatMark;
	//标记时间
	private Long nanoTime;
	//columns END

	private MailMessage mailMessage;
	
	public EmailCustomer(){
	}

	public EmailCustomer(
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
	 * 老师姓名
	 * @return
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * 老师姓名
	 * @param value
	 */
	public void setCustomerName(String value) {
		this.customerName = value;
	}
	/**
	 * 邮件标题
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 邮件标题
	 * @param value
	 */
	public void setTitle(String value) {
		this.title = value;
	}
	/**
	 * 地区标识
	 * @return
	 */
	public Long getAreaPlatMark() {
		return this.areaPlatMark;
	}
	
	/**
	 * 地区标识
	 * @param value
	 */
	public void setAreaPlatMark(Long value) {
		this.areaPlatMark = value;
	}
	/**
	 * 标记时间
	 * @return
	 */
	public Long getNanoTime() {
		return this.nanoTime;
	}
	
	/**
	 * 标记时间
	 * @param value
	 */
	public void setNanoTime(Long value) {
		this.nanoTime = value;
	}

	public MailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
}

