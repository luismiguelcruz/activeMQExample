package com.ixxus.activeMqCommunication.bootApp;

import com.ixxus.activeMqCommunication.jms.publisher.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;

//@SpringBootApplication
public class ActiveMqCommunicationApplication {

	public static void main(String[] args) {

		//SpringApplication.run(ActiveMqCommunicationApplication.class, args);

		final ApplicationContext context =
				new ClassPathXmlApplicationContext("com/ixxus/activeMqCommunication/beans/spring-config.xml");

		Producer producer = new Producer();

		try {
			producer.create("producer1", "queue1");
		} catch (JMSException e) {
			e.printStackTrace();
		}

		System.out.println();

		((ClassPathXmlApplicationContext)context).close();
	}
}
