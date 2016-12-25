package ru.sbt.userservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.io.Serializable;

@Component
public class JmsSender<T extends Serializable> {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(final String operation, final String text) {
        jmsTemplate.send(session -> {
            TextMessage textMessage = session.createTextMessage(text);
            textMessage.setStringProperty("Operation", operation);
            return textMessage;
        });
    }

    public void sendObject(final String operation, final T list) {
        jmsTemplate.send(session -> {
            ObjectMessage objectMessage = session.createObjectMessage(list);
            objectMessage.setStringProperty("Operation", operation);
            return objectMessage;
        });
    }
}
