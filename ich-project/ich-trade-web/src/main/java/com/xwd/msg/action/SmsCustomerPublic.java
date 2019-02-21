package com.xwd.msg.action;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xwd.base.constant.SmsTemplateConstant;
import com.xwd.base.util.RandomUtils;
import com.xwd.base.web.BaseAdminController;
import com.xwd.log.service.LogService;
import com.xwd.message.service.SmsService;
import com.xwd.msg.model.SmsCustomer;
import com.xwd.msg.service.SmsCustomerService;

@Controller
@Scope("prototype")
@RequestMapping("/public/msg/smsCustomer")
public class SmsCustomerPublic extends BaseAdminController {
	
	@Autowired
	private LogService logService;
	@Autowired
	private SmsCustomerService smsCustomerService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private SmsService smsService;
	
	@RequestMapping("sendRegister")
	public void sendSMS(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String mobile=this.get("mobile");
		String code= RandomUtils.generateNumString(4);
		redisTemplate.opsForValue().set(mobile,code);
		redisTemplate.expire(mobile,3, TimeUnit.MINUTES);
		SmsCustomer smsCustomer=new SmsCustomer();
		smsCustomer.setMobile(mobile);
		smsCustomer.setNanoTime(System.nanoTime());
		Map<String, String> map = new HashMap<String,String>();
		map.put("", "");
		smsCustomer.setArgMap(map);
		smsCustomer.setSmsTemplateId(SmsTemplateConstant.SMS_REGISTRY);
		smsService.send(smsCustomer);
		this.outSuccessJson(response,"发送成功");
	}
	
}

