package com.ixxus.activeMqCommunication.jms.producer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.jms.JMSException;

import com.ixxus.activeMqCommunication.jms.consumer.Consumer;
import com.ixxus.activeMqCommunication.jms.publisher.Producer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProducerTest {

    private static Producer producer;
    private static Consumer consumer;

    @BeforeClass
    public static void setUpBeforeClass() throws JMSException {
        producer = new Producer();
        producer.create("producer-pointtopoint", "pointtopoint.q");

        consumer = new Consumer();
        consumer.create("consumer-pointtopoint",
                "pointtopoint.q");
    }

    @AfterClass
    public static void tearDownAfterClass() throws JMSException {
        producer.close();
        consumer.close();
    }

    @Test
    public void testGetGreeting() {
        try {
            producer.sendText("Hello World!");

            String greeting = consumer.getGreeting(1000, true);
            assertEquals("Hello World!", greeting);
        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }

    @Test
    public void testNoGreeting() {
        try {
            String greeting = consumer.getGreeting(1000, true);
            assertEquals("no greeting", greeting);

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }
}
