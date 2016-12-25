package ru.sbt.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import ru.sbt.core.userservice.Client;
import ru.sbt.core.userservice.entity.InfoClient;
import ru.sbt.userservice.service.ProfileService;
import ru.sbt.userservice.utils.JmsSender;

@Controller
public class ProfileController {

    @Autowired
    JmsSender<Client> jmsSender;

    @Autowired
    ProfileService profileService;

    private final String QUEUE = "user-in";
    private final String CREATE_PROFILE = "Operation = 'CREATE_PROFILE'";
    private final String UPDATE_FIO = "Operation = 'UPDATE_FIO'";
    private final String UPDATE_INN = "Operation = 'UPDATE_INN'";
    private final String SEARCH_PROFILE = "Operation = 'SEARCH_PROFILE'";
    private final String DELETE_PROFILE = "Operation = 'DELETE_PROFILE'";

    @JmsListener(destination = QUEUE, selector = CREATE_PROFILE)
    public void createProfile(Client profile){
        try {
            profileService.createProfile(profile);
            jmsSender.sendMessage(CREATE_PROFILE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CREATE_PROFILE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = UPDATE_FIO)
    public void updateFIO(Client profile){
        try {
            profileService.updateFIO(profile);
            jmsSender.sendMessage(UPDATE_FIO, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_FIO, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = UPDATE_INN)
    public void updateInn(Client profile){
        try {
            profileService.updateInn(profile);
            jmsSender.sendMessage(UPDATE_INN, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_INN, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = DELETE_PROFILE)
    public void deleteProfile(Long idUser){
        try {
            profileService.deleteProfile(idUser);
            jmsSender.sendMessage(DELETE_PROFILE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_PROFILE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = SEARCH_PROFILE)
    public  void searchProfile(Long idUser){
        try {
            jmsSender.sendObject(SEARCH_PROFILE, profileService.searchProfile(idUser));
        } catch (Exception ex) {
            jmsSender.sendObject(SEARCH_PROFILE, InfoClient.createEmpty());
        }
    }
}
