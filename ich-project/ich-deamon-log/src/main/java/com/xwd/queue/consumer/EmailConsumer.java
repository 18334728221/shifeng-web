package com.xwd.queue.consumer;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xwd.base.constant.MqThemeConstant;
import com.xwd.base.util.ByteUtils;
import com.xwd.email.service.EmailService;
import com.xwd.msg.constant.MsgConstant;
import com.xwd.msg.model.EmailCustomer;
import com.xwd.msg.model.EmailUser;
import com.xwd.msg.service.EmailCustomerService;
import com.xwd.msg.service.EmailUserService;


/**
 * 短信队列消费者
 * @author ljl
 *
 */
public class EmailConsumer {

	private final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;
    private String namesrvAddr;
    private String consumerGroup;
    private EmailUserService emailUserService;
    private EmailCustomerService emailCustomerService;
    private EmailService emailService;

    /**
     * Spring bean init-method
     */
    public void init() throws InterruptedException, MQClientException {

        // 参数信息
        logger.info("DefaultMQPushConsumer initialize!");
        logger.info(consumerGroup);
        logger.info(namesrvAddr);

        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
        // 注意：ConsumerGroupName需要由应用来保证唯一
        defaultMQPushConsumer = new DefaultMQPushConsumer(MsgConstant.MQ_MSG_EMAIL);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));

        // 订阅指定MyTopic下tags等于MyTag

        defaultMQPushConsumer.subscribe(MqThemeConstant.MQ_TOPIC_EMAIL, "*");

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(100);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {

            // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            	for(MessageExt msg : msgs){
            		// 执行Topic的消费逻辑
	                if(MqThemeConstant.MQ_TOPIC_TAGS_USER.equals(msg.getTags())){
	                	Object obj = ByteUtils.convert(msg.getBody());
	                	if(obj != null){
	                		EmailUser entity = (EmailUser)obj;
	                		//去重
	                		EmailUser vo = emailUserService.findUniqueBy("nanoTime", entity.getNanoTime());
	                		if(vo == null){
	                			emailUserService.save(entity);
	                			//发送邮件
	                			emailService.sendEmail(entity.getMailMessage());
	                		}
	                	}
	                }else if(MqThemeConstant.MQ_TOPIC_TAGS_CUSTOMER.equals(msg.getTags())){
	                	Object obj = ByteUtils.convert(msg.getBody());
	                	if(obj != null){
	                		EmailCustomer entity = (EmailCustomer)obj;
	                		//去重
	                		EmailCustomer vo = emailCustomerService.findUniqueBy("nanoTime", entity.getNanoTime());
	                		if(vo == null){
	                			emailCustomerService.save(entity);
	                			//发送邮件
	                			emailService.sendEmail(entity.getMailMessage());
	                		}
	                	}
	                }
            	}
                // 如果没有return success ，consumer会重新消费该消息，直到return success
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();

        logger.info("DefaultMQPushConsumer start success!");
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    // ----------------- setter --------------------

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

	public void setEmailUserService(EmailUserService emailUserService) {
		this.emailUserService = emailUserService;
	}

	public EmailCustomerService getEmailCustomerService() {
		return emailCustomerService;
	}

	public void setEmailCustomerService(EmailCustomerService emailCustomerService) {
		this.emailCustomerService = emailCustomerService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
}
