package com.xwd.queue.service.impl;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wxd.queue.producer.EmailProducer;
import com.wxd.queue.producer.LogProducer;
import com.wxd.queue.producer.SmsProducer;
import com.xwd.base.constant.BaseDataConstant;
import com.xwd.base.model.Mq;
import com.xwd.base.model.MqTheme;
import com.xwd.base.util.ByteUtils;
import com.xwd.msg.constant.MsgConstant;
import com.xwd.queue.service.QueueService;


@Service
public class QueueServiceImpl implements QueueService {

	private final Logger logger = LoggerFactory.getLogger(QueueServiceImpl.class);
	private EmailProducer emailProducer;
	private LogProducer logProducer;
	private SmsProducer smsProducer;

	public void sendMessage(String topic, String tags, Object object) {
		Message msg = new Message(topic, tags, ByteUtils.convert(object));
		SendResult sendResult = null;
		try {
			if (MsgConstant.MQ_MSG_EMAIL.equals(topic)) {
				msg.setDelayTimeLevel(3);
				sendResult = emailProducer.getDefaultMQProducer().send(msg);
			} else if (MsgConstant.MQ_MSG_LOG.equals(topic)) {
				sendResult = logProducer.getDefaultMQProducer().send(msg);
			} else {
				msg.setDelayTimeLevel(2);
				sendResult = smsProducer.getDefaultMQProducer().send(msg);
			}
		} catch (MQClientException e) {
			logger.error(e.getMessage() + String.valueOf(sendResult));
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 当消息发送失败时如何处理
		if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {

		}
	}

	public void sendMessage(String topic, String tags, String keys, Object object) {
		Message msg = new Message(topic, tags, keys, ByteUtils.convert(object));
		SendResult sendResult = null;
		try {
			if (MsgConstant.MQ_MSG_EMAIL.equals(topic)) {
				msg.setDelayTimeLevel(3);
				sendResult = emailProducer.getDefaultMQProducer().send(msg);
			} else if (MsgConstant.MQ_MSG_LOG.equals(topic)) {
				sendResult = logProducer.getDefaultMQProducer().send(msg);
			} else {
				msg.setDelayTimeLevel(2);
				sendResult = smsProducer.getDefaultMQProducer().send(msg);
			}
		} catch (MQClientException e) {
			logger.error(e.getMessage() + String.valueOf(sendResult));
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 当消息发送失败时如何处理
		if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
			
		}
	}

	
	@Override
	public void sendMessage(MqTheme topic, Object object) {
		if(topic != null && object != null){
			if(topic.getMqKeys() == null){
				this.sendMessage(topic.getTopic(), topic.getTags(), object);
			}else{
				this.sendMessage(topic.getTopic(), topic.getTags(), topic.getMqKeys(), object);
			}
		}
	}

	public void init() {
		String namesrvAddr = "";
		for (Entry<Long, Mq> entry : BaseDataConstant.BASE_MQ_MAP.entrySet()) {
			namesrvAddr += entry.getValue().getIp() + ":" + entry.getValue().getPort() + ";";
		}
		if (StringUtils.isNotBlank(namesrvAddr)) {
			namesrvAddr = namesrvAddr.substring(0, namesrvAddr.length() - 1);
			try {
				emailProducer = new EmailProducer();
				emailProducer.setNamesrvAddr(namesrvAddr);
				emailProducer.init();

				logProducer = new LogProducer();
				logProducer.setNamesrvAddr(namesrvAddr);
				logProducer.init();

				smsProducer = new SmsProducer();
				smsProducer.setNamesrvAddr(namesrvAddr);
				smsProducer.init();
			} catch (Exception e) {

			}
		}
	}

}
