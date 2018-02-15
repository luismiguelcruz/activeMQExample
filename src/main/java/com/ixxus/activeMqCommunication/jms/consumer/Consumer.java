package com.ixxus.activeMqCommunication.jms.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("consumer")
public class Consumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Consumer.class);

    private static String NO_GREETING = "no greeting";

    private String clientId;
    private Connection connection;
    private MessageConsumer messageConsumer;
    private ConnectionFactory connectionFactory;

    @Autowired
    Consumer(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public void create(String clientId, String queueName)
            throws JMSException {
        this.clientId = clientId;

        // create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);

        // create a Session
        Session session =
                connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        // create the Queue from which messages will be received
        Queue queue = session.createQueue(queueName);

        // create a MessageConsumer for receiving messages
        messageConsumer = session.createConsumer(queue);

        // start the connection in order to receive messages
        connection.start();
    }

    public void close() throws JMSException {
        connection.close();
    }

    public String getGreeting(int timeout, boolean acknowledge)
            throws JMSException {

        String greeting = NO_GREETING;

        // read a message from the queue destination
        Message message = messageConsumer.receive(timeout);

        // check if a message was received
        if (message != null) {
            // cast the message to the correct type
            TextMessage textMessage = (TextMessage) message;

            // retrieve the message content
            String text = textMessage.getText();
            LOGGER.debug(clientId + ": received message with text='{}'",
                    text);

            if (acknowledge) {
                // acknowledge the successful processing of the message
                message.acknowledge();
                LOGGER.debug(clientId + ": message acknowledged");
            } else {
                LOGGER.debug(clientId + ": message not acknowledged");
            }

            // create greeting
            greeting = "Hello " + text + "!";
        } else {
            LOGGER.debug(clientId + ": no message received");
        }

        LOGGER.info("greeting={}", greeting);
        return greeting;
    }

    public String getTextMessage(int timeout, boolean acknowledge)
            throws JMSException {

        String greeting = NO_GREETING;

        // read a message from the queue destination
        Message message = messageConsumer.receive(timeout);

        // check if a message was received
        if (message != null) {
            // cast the message to the correct type
            TextMessage textMessage = (TextMessage) message;

            // retrieve the message content
            String text = textMessage.getText();
            LOGGER.debug(clientId + ": received message with text='{}'",
                    text);

            if (acknowledge) {
                // acknowledge the successful processing of the message
                message.acknowledge();
                LOGGER.debug(clientId + ": message acknowledged");
            } else {
                LOGGER.debug(clientId + ": message not acknowledged");
            }

            // create greeting
            greeting = text;
        } else {
            LOGGER.debug(clientId + ": no message received");
        }

        LOGGER.info("greeting={}", greeting);
        return greeting;
    }
}
