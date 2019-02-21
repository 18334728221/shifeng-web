package com.wxd.queue.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xwd.msg.constant.MsgConstant;

/**
 * 日志生产者
 * 
 * @author ljl
 *
 */
public class SmsProducer {

	private final Logger logger = LoggerFactory.getLogger(SmsProducer.class);

	private DefaultMQProducer defaultMQProducer;
	private String producerGroup;
	private String namesrvAddr;

	/**
	 * Spring bean init-method
	 */
	public void init() throws MQClientException {
		// 参数信息
		logger.info("Sms DefaultMQProducer initialize!");
		logger.info(producerGroup);
		logger.info(namesrvAddr);

		// 初始化
		defaultMQProducer = new DefaultMQProducer(MsgConstant.MQ_MSG_SMS);
		defaultMQProducer.setNamesrvAddr(namesrvAddr);
		defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));

		defaultMQProducer.start();
		logger.info("DefaultMQProudcer start success!");
	}

	/**
	 * Spring bean destroy-method
	 */
	public void destroy() {
		defaultMQProducer.shutdown();
	}

	public DefaultMQProducer getDefaultMQProducer() {
		return defaultMQProducer;
	}

	// ---------------setter -----------------

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}
}
