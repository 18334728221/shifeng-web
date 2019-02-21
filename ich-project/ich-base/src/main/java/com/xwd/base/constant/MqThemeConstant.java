package com.xwd.base.constant;

/**
 * 主题常量
 * 
 * @author ljl
 *
 */
public class MqThemeConstant {

	// 公司用户日志消息标题ID
	public static final Long MQ_TOPIC_LOG_USER = 1L;
	// 顾客日志消息标题ID
	public static final Long MQ_TOPIC_LOG_CUSTOMER = 2L;
	// 账户流水
	public static final Long MQ_TOPIC_LOG_ACCOUNT = 3L;
	// 充值
	public static final Long MQ_TOPIC_LOG_DEPOSIT = 4L;
	// 提现
	public static final Long MQ_TOPIC_LOG_WITHDRAW = 5L;
	// 买入佣金
	public static final Long MQ_TOPIC_LOG_BUYER_COMMISSION = 6L;
	// 卖出佣金
	public static final Long MQ_TOPIC_LOG_SELLER_COMMISSION = 7L;
		
	// 公司用户短信消息标题ID
	public static final Long MQ_TOPIC_SMS_USER = 5L;
	// 顾客短信消息标题ID
	public static final Long MQ_TOPIC_SMS_CUSTOMER = 6L;

	// 公司用户邮件消息标题ID
	public static final Long MQ_TOPIC_EMAIL_USER = 9L;
	// 顾客邮件消息标题ID
	public static final Long MQ_TOPIC_EMAIL_CUSTOMER = 10L;

	// 日志
	public static final String MQ_TOPIC_LOG = "Log";
	// 短信
	public static final String MQ_TOPIC_SMS = "Sms";
	// 邮件
	public static final String MQ_TOPIC_EMAIL = "Email";
	
	public static final String MQ_TOPIC_TAGS_USER = "user";
	public static final String MQ_TOPIC_TAGS_CUSTOMER= "customer";
	public static final String MQ_TOPIC_TAGS_ACCOUNT= "account";
	public static final String MQ_TOPIC_TAGS_DEPOSIT= "deposit";
	public static final String MQ_TOPIC_TAGS_WITHDRAW= "withdraw";
	public static final String MQ_TOPIC_TAGS_BUYER_COMMISSION= "buyer_commission";
	public static final String MQ_TOPIC_TAGS_SELLER_COMMISSION= "seller_commission";
}
