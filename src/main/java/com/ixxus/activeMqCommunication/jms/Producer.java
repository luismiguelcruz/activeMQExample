package com.ixxus.activeMqCommunication.jms;

import com.ixxus.activeMqCommunication.bootApp.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

@Service
public class Producer {

    public static final String HELLO_WORLD = "Hello World!";
    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) throws JMSException{
        jmsTemplate.convertAndSend(ApplicationConfiguration.QUEUE_HELLO_WORLD, message);
    }

    @Scheduled(fixedDelay=5000)
    public void sendHelloWorld() throws JMSException{
        sendMessage(HELLO_WORLD);
    }
}
