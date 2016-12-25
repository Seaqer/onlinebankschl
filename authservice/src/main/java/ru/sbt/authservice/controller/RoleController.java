package ru.sbt.authservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import ru.sbt.core.authservice.Role;
import ru.sbt.core.authservice.entity.InfoRole;
import ru.sbt.core.common.Message;
import ru.sbt.authservice.interfaces.service.RoleService;
import ru.sbt.authservice.utils.JmsSender;

@Controller
public class RoleController {

    @Autowired
    JmsSender<Role> jmsSender;

    @Autowired
    RoleService roleService;

    private final String QUEUE = "auth-in";
    private final String CREATE_ROLE = "Operation = 'CREATE_ROLE'";
    private final String SEARCH_ROLE = "Operation = 'SEARCH_ROLE'";
    private final String DELETE_ROLE = "Operation = 'DELETE_ROLE'";
    private final String UPDATE_ROLE = "Operation = 'UPDATE_ROLE'";
    private final String GIVE_USER_ROLE = "Operation = 'GIVE_USER_ROLE'";
    private final String REMOVE_USER_ROLE = "Operation = 'REMOVE_USER_ROLE'";


    @JmsListener(destination = QUEUE, selector = CREATE_ROLE)
    public void createRole(Role role){
        try {
            roleService.createRole(role);
            jmsSender.sendMessage(CREATE_ROLE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CREATE_ROLE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = DELETE_ROLE)
    public void deleteRole(Role role){
        try {
            roleService.deleteRole(role);
            jmsSender.sendMessage(DELETE_ROLE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_ROLE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = SEARCH_ROLE)
    public  void searchRole(String name){
        try {
            jmsSender.sendObject(SEARCH_ROLE, roleService.searchRole(name));
        } catch (Exception ex) {
            jmsSender.sendObject(SEARCH_ROLE, new InfoRole());
        }
    }

    @JmsListener(destination = QUEUE, selector = GIVE_USER_ROLE)
    public void giveUserRole(Message message) {
        try {
            roleService.giveUserRole(message.getFrom(), message.getTo());
            jmsSender.sendMessage(GIVE_USER_ROLE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(GIVE_USER_ROLE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = REMOVE_USER_ROLE)
    public void removeUserRole(Message message) {
        try {
            roleService.removeUserRole(message.getFrom(), message.getTo());
            jmsSender.sendMessage(REMOVE_USER_ROLE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(REMOVE_USER_ROLE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = UPDATE_ROLE)
    public void updateRole(Message message) {
        try {
            roleService.updateRole(message.getFrom(), message.getTo());
            jmsSender.sendMessage(UPDATE_ROLE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_ROLE, ex.getMessage());
        }
    }
}
