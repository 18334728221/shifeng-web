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

import com.alibaba.druid.support.json.JSONUtils;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.xwd.base.constant.MqThemeConstant;
import com.xwd.base.constant.SmsTemplateConstant;
import com.xwd.base.util.ByteUtils;
import com.xwd.msg.constant.MsgConstant;
import com.xwd.msg.model.SmsCustomer;
import com.xwd.msg.model.SmsUser;
import com.xwd.msg.service.SmsCustomerService;
import com.xwd.msg.service.SmsUserService;
import com.xwd.sms.util.SmsUtils;


/**
 * 短信队列消费者
 * @author ljl
 *
 */
public class SmsConsumer {

	private final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;
    private String namesrvAddr;
    private String consumerGroup;
    private SmsUserService smsUserService;
    private SmsCustomerService smsCustomerService;

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
        defaultMQPushConsumer = new DefaultMQPushConsumer(MsgConstant.MQ_MSG_SMS);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));

        // 订阅指定MyTopic下tags等于MyTag

        defaultMQPushConsumer.subscribe(MqThemeConstant.MQ_TOPIC_SMS, "*");

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //defaultMQPushConsumer.setConsumeMessageBatchMaxSize(100);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {

            // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            	for(MessageExt msg : msgs){
            		logger.info(msg.getTags());
            		// 执行Topic的消费逻辑
	                if(MqThemeConstant.MQ_TOPIC_TAGS_USER.equals(msg.getTags())){
	                	Object obj = ByteUtils.convert(msg.getBody());
	                	if(obj != null){
	                		SmsUser entity = (SmsUser)obj;
	                		//去重
	                		SmsUser vo = smsUserService.findUniqueBy("nanoTime", entity.getNanoTime());
	                		if(vo == null){
	                			smsUserService.save(entity);
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
	                	        try {
									SmsUtils.sendSms(request);
								} catch (ClientException e) {
									e.printStackTrace();
								}
	                		}
	                	}
	                }else if(MqThemeConstant.MQ_TOPIC_TAGS_CUSTOMER.equals(msg.getTags())){
	                	Object obj = ByteUtils.convert(msg.getBody());
 	                	if(obj != null){
	                		SmsCustomer entity = (SmsCustomer)obj;
	                		//去重
	                		SmsCustomer vo = smsCustomerService.findUniqueBy("nanoTime", entity.getNanoTime());
	                		if(vo == null){
	                			smsCustomerService.save(entity);
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
	                	        try {
									SmsUtils.sendSms(request);
								} catch (ClientException e) {
									e.printStackTrace();
								}
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

	public void setSmsUserService(SmsUserService smsUserService) {
		this.smsUserService = smsUserService;
	}

	public void setSmsCustomerService(SmsCustomerService smsCustomerService) {
		this.smsCustomerService = smsCustomerService;
	}

}
