package com.hmh.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivemqTest {

	@Test
	public void testQueueConsumer() throws Exception{
		ApplicationContext applicationContext =
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");

		System.in.read();
	}
}
