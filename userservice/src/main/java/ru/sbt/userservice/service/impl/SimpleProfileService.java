package ru.sbt.userservice.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.core.common.Validator;
import ru.sbt.core.userservice.Client;
import ru.sbt.userservice.dao.ProfileDao;
import ru.sbt.core.userservice.entity.InfoClient;
import ru.sbt.userservice.exception.UserServiceException;
import ru.sbt.userservice.service.ProfileService;

import java.util.List;

@Service
public class SimpleProfileService implements ProfileService {
    private final ProfileDao profileDao;
    private final Validator validator;
    private static final Logger LOGGER = Logger.getLogger(SimpleProfileService.class);

    @Autowired
    public SimpleProfileService(ProfileDao profileRepository, Validator validator) {
        this.profileDao = profileRepository;
        this.validator = validator;
    }

    @Override
    public boolean createProfile(Client profile) {
        backgroundChecks(profile);
        profileDao.addElement(profile);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean updateFIO(Client profile) {
        backgroundChecks(profile);
        Client profileOld = searchProfile(profile.getIdUser());
        profileOld.setFirst_name(profile.getFirst_name());
        profileOld.setLast_name(profile.getLast_name());
        profileOld.setSecond_name(profile.getSecond_name());

        if (profileDao.updateElement(profileOld) < 1) {
            LOGGER.trace("SimpleProfileService:updateFIO попытка обновления не удалась" + profile);
            throw new UserServiceException("профиль не обновлен");
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean updateInn(Client profile) {
        Client profileOld = searchProfile(profile.getIdUser());
        profileOld.setInn(profile.getInn());
        if (profileDao.updateElement(profileOld) < 1) {
            LOGGER.trace("SimpleProfileService:updateInn попытка обновления не удалась" + profile);
            throw new UserServiceException("профиль не обновлен");
        }
        return true;
    }

    @Override
    public Client searchProfile(Long idUser) {
        Client profile = InfoClient.createEmpty();
        profile.setIdUser(idUser);

        List<Client> profiles = profileDao.getElements(profile);
        if (profiles.size() < 1) {
            LOGGER.trace("SimpleProfileService:searchProfile профиль не найден idUser:" + idUser);
            throw new UserServiceException("профиль не найден");
        }
        return profiles.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean deleteProfile(Long idUser) {
        Client profile = searchProfile(idUser);
        if (profileDao.deleteElement(profile) < 1) {
            LOGGER.trace("SimpleProfileService:deleteProfile попытка удаления не удалась" + profile.toString());
            throw new UserServiceException("профиль не удален");
        }
        return true;
    }

    private void backgroundChecks(Client client){
        validator.validate(client.getFirst_name());
        validator.validate(client.getLast_name());
        validator.validate(client.getSecond_name());
    }
}
