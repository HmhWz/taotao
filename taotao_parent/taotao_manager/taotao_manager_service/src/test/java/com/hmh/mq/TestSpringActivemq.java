package com.hmh.mq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;


public class TestSpringActivemq {

	@Test
	public void testQueueProducer() {
		// 初始化Spring容器
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		// 从容器中获得JMSTemplate对象
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		// 从容器中获得Destination对象
		Queue queue = (Queue) applicationContext.getBean("queueDestination");
		// 第一个参数：指定发送的目的地
		// 第二个参数：消息的构造器对象，就是创建一个消息
//		jmsTemplate.send(queue, new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				TextMessage textMessage = session.createTextMessage("使用Spring和ActiveMQ整合发送queue消息");
//				return textMessage;
//			}
//		});

		jmsTemplate.send(queue, (Session session) ->
				session.createTextMessage("使用Spring和ActiveMQ整合发送queue消息1122"));
	}

}
