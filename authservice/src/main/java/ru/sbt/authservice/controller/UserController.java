package ru.sbt.authservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import ru.sbt.core.authservice.User;
import ru.sbt.core.authservice.entity.InfoUser;
import ru.sbt.core.common.Message;
import ru.sbt.authservice.interfaces.service.UserService;
import ru.sbt.authservice.utils.JmsSender;

@Controller
public class UserController {

    @Autowired
    JmsSender<User> jmsSender;

    @Autowired
    UserService userService;

    private final String QUEUE = "auth-in";
    private final String CREATE_USER = "Operation = 'CREATE_USER'";
    private final String UPDATE_USER = "Operation = 'UPDATE_USER'";
    private final String SEARCH_USER = "Operation = 'SEARCH_USER'";
    private final String CLOSE_USER = "Operation = 'CLOSE_USER'";
    private final String UPDATE_LOGIN = "Operation = 'UPDATE_LOGIN'";
    private final String UPDATE_PASSWORD = "Operation = 'UPDATE_PASSWORD'";
    private final String OPEN_USER = "Operation = 'OPEN_USER'";
    private final String HAS_PREMISSION = "Operation = 'HAS_PREMISSION'";

    @JmsListener(destination = QUEUE, selector = CREATE_USER)
    public void registrationUser(User user) {
        try {
            userService.registrationUser(user);
            jmsSender.sendMessage(CREATE_USER, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CREATE_USER, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = OPEN_USER)
    public void openUser(String name) {
        try {
            userService.openUser(name);
            jmsSender.sendMessage(OPEN_USER, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(OPEN_USER, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = UPDATE_LOGIN)
    public void updateLogin(Message message) {
        try {
            userService.updateLogin(message.getFrom(), message.getTo());
            jmsSender.sendMessage(UPDATE_LOGIN, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_LOGIN, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = UPDATE_PASSWORD)
    public void updatePassword(Message message) {
        try {
            userService.updatePassword(message.getFrom(), message.getTo());
            jmsSender.sendMessage(UPDATE_PASSWORD, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_PASSWORD, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = HAS_PREMISSION)
    public void hasPremission(Message message) {
        try {
            if (userService.hasPremission(message.getFrom(), message.getTo())) {
                jmsSender.sendMessage(HAS_PREMISSION, "True");
            } else {
                jmsSender.sendMessage(HAS_PREMISSION, "False");
            }
        } catch (Exception ex) {
            jmsSender.sendMessage(HAS_PREMISSION, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = CLOSE_USER)
    public void closeUser(Message message) {
        try {
            userService.closeUser(message.getFrom(), message.getTo());
            jmsSender.sendMessage(CLOSE_USER, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CLOSE_USER, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = SEARCH_USER)
    public void searchUser(String name) {
        try {
            jmsSender.sendObject(SEARCH_USER, userService.searchUser(name));
        } catch (Exception ex) {
            jmsSender.sendObject(SEARCH_USER, new InfoUser());
        }
    }
}
