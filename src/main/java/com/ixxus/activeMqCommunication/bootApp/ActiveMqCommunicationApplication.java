package com.ixxus.activeMqCommunication.bootApp;

import com.ixxus.activeMqCommunication.jms.consumer.Consumer;
import com.ixxus.activeMqCommunication.jms.publisher.Producer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;

//@SpringBootApplication
@EnableJms
public class ActiveMqCommunicationApplication {

	public static void main(String[] args) {

		//SpringApplication.run(ActiveMqCommunicationApplication.class, args);

		final ApplicationContext context =
				new ClassPathXmlApplicationContext("bootApp/spring-config.xml");

        String text = "Hello World!";

       // JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		Producer producer = (Producer)context.getBean("producer");
        Consumer consumer = (Consumer)context.getBean("consumer");

		try {
			producer.create("producer1", "queue1");
            producer.sendText(text);

            consumer.create("consumer1", "queue1");
            String greeting = consumer.getTextMessage(1000, true);
            System.out.println("Message: "+greeting);
		} catch (JMSException e) {
			e.printStackTrace();
		}

        try {
            producer.close();
            consumer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        ((ClassPathXmlApplicationContext)context).close();
	}
}
