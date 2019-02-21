package com.xwd.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * 邮件消息
 * @author ljl
 *
 */
public class MailMessage implements Serializable{
	
	private static final long serialVersionUID = -1345005580064098817L;
	
	private String to; // 发送到那里的邮件地址
	private String subject; // 邮件的主题
	// 默认为html的utf-8编码
	private String templateName; // 模板的名称去掉后缀名 要放到classpath路径下
	//邮件内容参数
	private  Map<String,String> mailMap;

	public MailMessage() {
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, String> getMailMap() {
		return mailMap;
	}

	public void setMailMap(Map<String, String> mailMap) {
		this.mailMap = mailMap;
	}

}
