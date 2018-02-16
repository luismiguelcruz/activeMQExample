package com.ixxus.activeMqCommunication.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        final JmsTemplate jmsTemplate = new JmsTemplate(this.connectionFactory());
        return jmsTemplate;
    }

    @Bean
    public Queue queue() {
        final Queue queue = new ActiveMQQueue("queue1");
        return queue;
    }
}
