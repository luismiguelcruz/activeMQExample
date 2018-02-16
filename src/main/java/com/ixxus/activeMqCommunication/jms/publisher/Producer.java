package com.ixxus.activeMqCommunication.jms.publisher;

import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component("producer")
public class Producer{

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Producer.class);

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;
    private ConnectionFactory connectionFactory;
    private JmsTemplate jmsTemplate;
    private Queue queue;

    @Autowired
    Producer(ConnectionFactory connectionFactory, JmsTemplate jmsTemplate, Queue queue){
        this.connectionFactory = connectionFactory;
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }

    public void create(String clientId, String queueName)
            throws JMSException {
        this.clientId = clientId;

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
        LOGGER.debug("producer sent message with text='{}'", text);

        jmsTemplate.send(queue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(text);
            }});
    }

    public Connection getConnection(){
        return connection;
    }
}
