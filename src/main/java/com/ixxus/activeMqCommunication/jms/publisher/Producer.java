package com.ixxus.activeMqCommunication.jms.publisher;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component("producer")
public class Producer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Producer.class);

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;
    private ConnectionFactory connectionFactory;

    @Autowired
    Producer(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public void create(String clientId, String queueName)
            throws JMSException {
        this.clientId = clientId;

        // create a Connection Factory
/*        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(
                        "tcp://localhost:61616");*/

        // create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);

        // create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create the Queue to which messages will be sent
        Queue queue = session.createQueue(queueName);

        // create a MessageProducer for sending messages
        messageProducer = session.createProducer(queue);
    }

    public void close() throws JMSException {
        connection.close();
    }

    public void sendName(String firstName, String lastName)
            throws JMSException {
        String text = firstName + " " + lastName;

        // create a JMS TextMessage
        TextMessage textMessage = session.createTextMessage(text);

        // send the message to the queue destination
        messageProducer.send(textMessage);

        LOGGER.debug(clientId + ": sent message with text='{}'", text);
    }

    public void sendText(String text)
            throws JMSException {
        // create a JMS TextMessage
        TextMessage textMessage = session.createTextMessage(text);

        // send the message to the queue destination
        messageProducer.send(textMessage);

        LOGGER.debug("producer sent message with text='{}'", text);
    }

    public Connection getConnection(){
        return connection;
    }
}
