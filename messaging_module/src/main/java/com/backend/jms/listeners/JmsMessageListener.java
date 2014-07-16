/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.jms.listeners;

import org.slf4j.LoggerFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.slf4j.Logger;

/**
 *
 * @author armen
 */
public class JmsMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageListener.class);

    /**
     * Listener 
     */
    public void onMessage(Message message) {
        try {
            int messageCount = message.getIntProperty("messageCount");
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String msg = tm.getText();
                System.out.println("I RECEIVED " + msg + " | " + messageCount);
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
