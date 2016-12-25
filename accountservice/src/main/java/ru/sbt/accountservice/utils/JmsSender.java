package ru.sbt.accountservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void sendList(final String operation, final List<T> list) {
        jmsTemplate.send(session -> {
            ObjectMessage objectMessage = session.createObjectMessage(new ArrayList<>(list));
            objectMessage.setStringProperty("Operation", operation);
            return objectMessage;
        });
    }
}
