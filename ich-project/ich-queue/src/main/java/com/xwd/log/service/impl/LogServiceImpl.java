package com.xwd.log.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.SecurityUtils;
import com.auth.User;
import com.frame.util.IpUtils;
import com.xwd.auth.model.AuthUser;
import com.xwd.customer.model.Customer;
import com.xwd.log.model.LogAccount;
import com.xwd.log.model.LogCustomer;
import com.xwd.log.model.LogDeposits;
import com.xwd.log.model.LogUser;
import com.xwd.log.model.LogWithdraws;
import com.xwd.log.service.LogAccountService;
import com.xwd.log.service.LogCustomerService;
import com.xwd.log.service.LogDepositsService;
import com.xwd.log.service.LogService;
import com.xwd.log.service.LogUserService;
import com.xwd.log.service.LogWithdrawsService;
import com.xwd.queue.service.QueueService;


@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	private QueueService queueService;
	@Autowired
	private LogUserService logUserService;
	@Autowired
	private LogCustomerService logCustomerService;
	@Autowired
	private LogDepositsService logDepositsService;
	@Autowired
	private LogWithdrawsService logWithdrawsService;
	@Autowired
	private LogAccountService logAccountService;

	@SuppressWarnings("rawtypes")
	@Override
	public void add(HttpServletRequest request, String content) {
		try{
			User user = SecurityUtils.getUser();
			if(user instanceof AuthUser){
				AuthUser u = (AuthUser)user;
				LogUser log = new LogUser();
				log.setCreateTime(Calendar.getInstance());
				log.setUserId(u.getId());
				log.setUserName(u.getTrueName());
				log.setUserIp(IpUtils.getIpAddr(request));
				log.setDescription(content);
				log.setNanoTime(System.nanoTime());
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_LOG_USER), log);
				logUserService.save(log);
			} else if(user instanceof Customer){
				Customer u = (Customer)user;
				LogCustomer log = new LogCustomer();
				log.setCustomerNo(u.getCustomerNo());
				log.setName(u.getName());
				log.setAreaPlatMark(u.getAreaPlatMark());
				log.setCreateTime(Calendar.getInstance());
				log.setIp(IpUtils.getIpAddr(request));
				log.setDescription(content);
				log.setNanoTime(System.nanoTime());
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_LOG_CUSTOMER), log);
				logCustomerService.save(log);
			}
		}catch(Exception e){
		}
	}

	@Override
	public void addQueryLog(HttpServletRequest request, String content) {
		try{
			User user = SecurityUtils.getUser();
			if(user instanceof AuthUser){
				AuthUser u = (AuthUser)user;
				LogUser log = new LogUser();
				log.setCreateTime(Calendar.getInstance());
				log.setUserId(u.getId());
				log.setUserName(u.getTrueName());
				log.setUserIp(IpUtils.getIpAddr(request));
				log.setDescription(content);
				log.setNanoTime(System.nanoTime());
				log.setType(0);
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_LOG_USER), log);
				logUserService.save(log);
			} else if(user instanceof Customer){
				Customer u = (Customer)user;
				LogCustomer log = new LogCustomer();
				log.setCustomerNo(u.getCustomerNo());
				log.setName(u.getName());
				log.setAreaPlatMark(u.getAreaPlatMark());
				log.setCreateTime(Calendar.getInstance());
				log.setIp(IpUtils.getIpAddr(request));
				log.setDescription(content);
				log.setNanoTime(System.nanoTime());
				log.setType(0);
				//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_LOG_CUSTOMER), log);
				logCustomerService.save(log);
			}
		}catch(Exception e){
		}
		
	}
	
	@Override
	public void addDepositLog(BigDecimal amount, String txNo) {
		Customer u = (Customer)SecurityUtils.getUser();
		LogDeposits entity = new LogDeposits();
		entity.setCustomerNo(u.getCustomerNo());
		entity.setTxNo(txNo);
		entity.setUserId(u.getId());
		entity.setAmount(amount);
		entity.setNanoTime(System.nanoTime());
		//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_LOG_DEPOSIT), entity);
		logDepositsService.save(entity);
	}
	
	@Override
	public void addWithdrawLog(BigDecimal amount, String txNo) {
		Customer u = (Customer)SecurityUtils.getUser();
		LogWithdraws entity = new LogWithdraws();
		entity.setTxNo(txNo);
		entity.setCustomerNo(u.getCustomerNo());
		entity.setAmount(amount);
		entity.setUserId(u.getId());
		entity.setNanoTime(System.nanoTime());
		//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_LOG_WITHDRAW), entity);
		logWithdrawsService.save(entity);
	}
	
	@Override
	public void addAccountLog(Long customerNo, BigDecimal amount, int sourceType, String content, Calendar tradeTime, boolean isIncome) {
		LogAccount entity = new LogAccount();
		if(isIncome){
			entity.setIncome(amount);
		} else {
			entity.setExpenses(amount);
		}
		entity.setCustomerNo(customerNo);
		entity.setSourceType(sourceType);
		entity.setDescription(content);
		entity.setCreateTime(tradeTime);
		//queueService.sendMessage(BaseDataConstant.BASE_MQ_THEME_MAP.get(MqThemeConstant.MQ_TOPIC_LOG_ACCOUNT), entity);
		this.logAccountService.save(entity);
	}
}
