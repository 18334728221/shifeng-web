package com.xwd.email.service.iml;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.EmailServer;
import com.xwd.bean.MailMessage;
import com.xwd.email.service.EmailService;

import freemarker.template.Template;

/**
 * 
 * Description: Spring邮件服务 Copyright (c) Department of Research and
 * Development/Beijing/Digital Heaven. All Rights Reserved.
 * 
 * @version 1.0 Dec 9, 2008 2:30:15 PM by 林金良（linjinliang@d-heaven.com）创建
 */
@Service
public class EmailServiceImpl implements EmailService{

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private FreeMarkerConfigurationFactoryBean freeMarkerConfigurer;
	//private FreeMarkerConfigurer freeMarkerConfigurer;
	
	/**
	 * 
	 * Description: 发送邮件
	 * 
	 * @Version1.0 Dec 9, 2008 3:02:59 PM by 林金良（linjinliang@d-heaven.com）创建
	 * @param msg
	 * @param map
	 */
	public boolean sendEmail(MailMessage msg) {
	    EmailServer emailServer = BaseDataConstant.BASE_EMAIL_SERVER_MAP.get(1L);
	    if(emailServer != null){
	    	//Properties prop = new Properties();  
	        //prop.setProperty("mail.smtp.auth", "true");   
	        //mailSender.setJavaMailProperties(prop);  
	        mailSender.setUsername(emailServer.getFromName());
	        mailSender.setPassword(emailServer.getPassword());
	        mailSender.setHost(emailServer.getSmtp());
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();	
			try {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,	"utf-8");
				helper.setTo(msg.getTo());			
				String text = this.doFreemarkerMail(msg.getMailMap(), msg.getTemplateName());
				helper.setText(text, true);
				helper.setSubject(msg.getSubject());
				helper.setFrom(emailServer.getFromName());
				this.mailSender.send(mimeMessage);		
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	    }else{
	    	logger.info("邮件配置配置信息出错！");
	    }
		return true;
	}

	/**
	 * 
	 * Description: 获得邮件内容
	 * 
	 * @Version1.0 Dec 9, 2008 2:43:39 PM by 林金良（linjinliang@d-heaven.com）创建
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private String doFreemarkerMail(Map map, String mailTemplateName)
			throws Exception {
		// 查找并加载freemarker模板文件
		Template template = freeMarkerConfigurer.createConfiguration().getTemplate(mailTemplateName);
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
	}

}
