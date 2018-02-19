package com.ixxus.activeMqCommunication.jms;

import com.ixxus.activeMqCommunication.bootApp.ApplicationConfiguration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = ApplicationConfiguration.QUEUE_HELLO_WORLD, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }

}
