package ru.sbt.authservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import ru.sbt.core.authservice.Operation;
import ru.sbt.core.authservice.entity.InfoOperation;
import ru.sbt.core.common.Message;
import ru.sbt.authservice.interfaces.service.OperationsService;
import ru.sbt.authservice.utils.JmsSender;

@Controller
public class OperationController {

    @Autowired
    JmsSender<Operation> jmsSender;

    @Autowired
    OperationsService operationsService;

    private final String QUEUE = "auth-in";
    private final String CREATE_OPERATION = "Operation = 'CREATE_OPERATION'";
    private final String SEARCH_OPERATION = "Operation = 'SEARCH_OPERATION'";
    private final String DELETE_OPERATION = "Operation = 'DELETE_OPERATION'";
    private final String UPDATE_OPERATION = "Operation = 'DELETE_OPERATION'";
    private final String GIVE_ROLE_OPERATION = "Operation = 'GIVE_ROLE_OPERATION'";
    private final String REMOVE_ROLE_OPERATION = "Operation = 'REMOVE_ROLE_OPERATION'";

    @JmsListener(destination = QUEUE, selector = CREATE_OPERATION)
    public void createOperation(Operation operation){
        try {
            operationsService.createOperation(operation);
            jmsSender.sendMessage(CREATE_OPERATION, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CREATE_OPERATION, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = DELETE_OPERATION)
    public void deleteOperation(Operation operation){
        try {
            operationsService.deleteOperation(operation);
            jmsSender.sendMessage(DELETE_OPERATION, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_OPERATION, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = SEARCH_OPERATION)
    public  void searchOperation(String name){
        try {
            jmsSender.sendObject(SEARCH_OPERATION, operationsService.searchOperation(name));
        } catch (Exception ex) {
            jmsSender.sendObject(SEARCH_OPERATION, new InfoOperation());
        }
    }

    @JmsListener(destination = QUEUE, selector = GIVE_ROLE_OPERATION)
    public void giveRoleOperation(Message message) {
        try {
            operationsService.giveRoleOperation(message.getFrom(), message.getTo());
            jmsSender.sendMessage(GIVE_ROLE_OPERATION, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(GIVE_ROLE_OPERATION, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = REMOVE_ROLE_OPERATION)
    public void removeRoleOperation(Message message) {
        try {
            operationsService.removeRoleOperation(message.getFrom(), message.getTo());
            jmsSender.sendMessage(REMOVE_ROLE_OPERATION, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(REMOVE_ROLE_OPERATION, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = UPDATE_OPERATION)
    public void updateOperation(Message message) {
        try {
            operationsService.updateOperation(message.getFrom(), message.getTo());
            jmsSender.sendMessage(UPDATE_OPERATION, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_OPERATION, ex.getMessage());
        }
    }
}
