package com.xwd.message.service.impl;

import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.auth.SecurityUtils;
import com.auth.User;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.constant.MqThemeConstant;
import com.xwd.base.constant.SmsTemplateConstant;
import com.xwd.customer.model.Customer;
import com.xwd.message.service.SmsService;
import com.xwd.msg.model.SmsCustomer;
import com.xwd.msg.model.SmsUser;
import com.xwd.queue.service.QueueService;
import com.xwd.sms.util.SmsUtils;


@Service
public class SmsServiceImpl implements SmsService{
	
	@Autowired
	private QueueService queueService;

	public void send(String mobile, String smsTemplateId, Map<String, String> map) {
		try{
			User user = SecurityUtils.getUser();
			if(user instanceof AuthUser){
				AuthUser u = (AuthUser)user;
				SmsUser entity = new SmsUser();
				entity.setCreateTime(Calendar.getInstance());
				entity.setUserId(u.getId());
				entity.setArgMap(map);
				entity.setMobile(u.getMobile());
				entity.setSmsTemplateId(smsTemplateId);
				entity.setNanoTime(System.nanoTime());
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_SMS_USER), entity);
				//组装请求对象-具体描述见控制台-文档部分内容
    	        SendSmsRequest request = new SendSmsRequest();
    	        //必填:待发送手机号
    	        request.setPhoneNumbers(entity.getMobile());
    	        //必填:短信签名-可在短信控制台中找到
    	        request.setSignName(SmsTemplateConstant.SMS_SERVER_SIGN_NAME);
    	        //必填:短信模板-可在短信控制台中找到
    	        request.setTemplateCode(entity.getSmsTemplateId());
    	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    	        request.setTemplateParam(JSONUtils.toJSONString(entity.getArgMap()));
    	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    	        request.setOutId(entity.getNanoTime() + "");
				SmsUtils.sendSms(request);
			} else if(user instanceof Customer){
				Customer u = (Customer)user;
				SmsCustomer entity = new SmsCustomer();
				entity.setCreateTime(Calendar.getInstance());
				entity.setCustomerNo(u.getCustomerNo());
				entity.setCustomerName(u.getName());
				entity.setArgMap(map);
				entity.setMobile(u.getMobile());
				entity.setSmsTemplateId(smsTemplateId);
				entity.setAreaPlatMark(u.getAreaPlatMark());
				entity.setNanoTime(System.nanoTime());
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_SMS_CUSTOMER), entity);
				//组装请求对象-具体描述见控制台-文档部分内容
    	        SendSmsRequest request = new SendSmsRequest();
    	        //必填:待发送手机号
    	        request.setPhoneNumbers(entity.getMobile());
    	        //必填:短信签名-可在短信控制台中找到
    	        request.setSignName(SmsTemplateConstant.SMS_SERVER_SIGN_NAME);
    	        //必填:短信模板-可在短信控制台中找到
    	        request.setTemplateCode(entity.getSmsTemplateId());
    	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    	        request.setTemplateParam(JSONUtils.toJSONString(entity.getArgMap()));
    	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    	        request.setOutId(entity.getNanoTime() + "");
				SmsUtils.sendSms(request);
			}
		} catch (Exception e){
			
		}
	}
	
	public void send(User user,String mobile, String smsTemplateId, Map<String, String> map) {
		try{
			
			if(user instanceof AuthUser){
				AuthUser u = (AuthUser)user;
				SmsUser entity = new SmsUser();
				entity.setCreateTime(Calendar.getInstance());
				entity.setUserId(u.getId());
				entity.setArgMap(map);
				entity.setMobile(u.getMobile());
				entity.setSmsTemplateId(smsTemplateId);
				entity.setNanoTime(System.nanoTime());
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_SMS_USER), entity);
				//组装请求对象-具体描述见控制台-文档部分内容
    	        SendSmsRequest request = new SendSmsRequest();
    	        //必填:待发送手机号
    	        request.setPhoneNumbers(entity.getMobile());
    	        //必填:短信签名-可在短信控制台中找到
    	        request.setSignName(SmsTemplateConstant.SMS_SERVER_SIGN_NAME);
    	        //必填:短信模板-可在短信控制台中找到
    	        request.setTemplateCode(entity.getSmsTemplateId());
    	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    	        request.setTemplateParam(JSONUtils.toJSONString(entity.getArgMap()));
    	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    	        request.setOutId(entity.getNanoTime() + "");
				SmsUtils.sendSms(request);
			} else if(user instanceof Customer){
				Customer u = (Customer)user;
				SmsCustomer entity = new SmsCustomer();
				entity.setCreateTime(Calendar.getInstance());
				entity.setCustomerNo(u.getCustomerNo());
				entity.setCustomerName(u.getName());
				entity.setArgMap(map);
				entity.setMobile(u.getMobile());
				entity.setSmsTemplateId(smsTemplateId);
				entity.setAreaPlatMark(u.getAreaPlatMark());
				entity.setNanoTime(System.nanoTime());
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_SMS_CUSTOMER), entity);
				//组装请求对象-具体描述见控制台-文档部分内容
    	        SendSmsRequest request = new SendSmsRequest();
    	        //必填:待发送手机号
    	        request.setPhoneNumbers(entity.getMobile());
    	        //必填:短信签名-可在短信控制台中找到
    	        request.setSignName(SmsTemplateConstant.SMS_SERVER_SIGN_NAME);
    	        //必填:短信模板-可在短信控制台中找到
    	        request.setTemplateCode(entity.getSmsTemplateId());
    	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    	        request.setTemplateParam(JSONUtils.toJSONString(entity.getArgMap()));
    	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    	        request.setOutId(entity.getNanoTime() + "");
				SmsUtils.sendSms(request);
			}
		} catch (Exception e){
			
		}
	}

	@Override
	public void send(SmsUser entity) {
		if(entity != null){
			queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_SMS_USER), entity);
		}
	}

	@Override
	public void send(SmsCustomer entity) {
		if(entity != null){
			queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_SMS_CUSTOMER), entity);
		}
	}

}
