package com.ixxus.activeMqCommunication.bootApp;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
public class ApplicationConfiguration {
    public static final String BROKER_URL = "tcp://localhost:61616";
    public static final String QUEUE_HELLO_WORLD = "queue1";

    @Bean(name="jmsListenerContainerFactory")
    public JmsListenerContainerFactory<?> getJmsListenerContainerFactory(@Qualifier("connectionFactory") final ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean(name="connectionFactory")
    public ConnectionFactory getConnectionFactory() {
        return new ActiveMQConnectionFactory(BROKER_URL);
    }

    @Bean(name="jmsTemplate")
    public JmsTemplate getJmsTemplate(@Qualifier("connectionFactory") final ConnectionFactory connectionFactory) {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        return jmsTemplate;
    }

    @Bean(name="queue")
    public Queue getQueue() {
        final Queue queue = new ActiveMQQueue(QUEUE_HELLO_WORLD);
        return queue;
    }
}
