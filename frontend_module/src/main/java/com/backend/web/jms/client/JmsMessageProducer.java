package com.backend.web.jms.client;
/**
 *
 * @author armen
 */

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsMessageProducer {

 
    private JmsTemplate template = null;
    private int messageCount = 10;

    /**
     * Generates JMS messages
     */
    public void generateMessages() throws JMSException {
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            final String text = "Message number is " + i + ".";

            template.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage(text);
                    message.setIntProperty("messageCount", index);
                  System.out.println("Sending messahe " + message.getText());
                    return message;
                }
            });
        }
    }

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
}