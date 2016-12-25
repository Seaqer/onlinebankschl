package ru.sbt.authservice.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.core.authservice.User;
import ru.sbt.core.authservice.entity.InfoOperation;
import ru.sbt.core.authservice.entity.InfoRole;
import ru.sbt.core.authservice.entity.InfoUser;
import ru.sbt.authservice.exceptions.AuthServiceException;
import ru.sbt.authservice.interfaces.repository.OperationJpaRepository;
import ru.sbt.authservice.interfaces.repository.UserJpaRepository;
import ru.sbt.authservice.interfaces.service.UserService;
import ru.sbt.authservice.interfaces.utils.PasswordManager;
import ru.sbt.authservice.interfaces.utils.UserValidator;

import java.util.Set;


@Service
@Transactional
public class HibernateUserService implements UserService {

    private final PasswordManager passwordManager;
    private final UserValidator userValidator;
    private final UserJpaRepository userRepository;
    private final OperationJpaRepository operationsService;
    private static final Logger LOGGER = Logger.getLogger(HibernateUserService.class);

    /**
     * Создать сервисный слой
     *  @param passwordManager   Менеджер паролей
     * @param userValidator     Интерфейс для проверки пользователей
     * @param userRepository    Интерфейс для работы с БД
     * @param operationsService интерфейс для работы с операциями
     */
    @Autowired
    public HibernateUserService(PasswordManager passwordManager, UserValidator userValidator, UserJpaRepository userRepository, OperationJpaRepository operationsService) {
        this.passwordManager = passwordManager;
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.operationsService = operationsService;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean registrationUser(User user) {
        userValidator.validate(user.getLogin(), user.getPassword());
        if (userRepository.findFirstByLogin(user.getLogin()) != null) {
            throw new AuthServiceException("пользователь с таких логином уже существует");
        }
        userRepository.save(new InfoUser(null, user.getLogin(), user.getPassword(), null));
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean updateLogin(String login, String newLogin) {
        userValidator.validateLogin(newLogin);
        InfoUser user = searchUser(login);
        user.setLogin(newLogin);
        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean updatePassword(String login, String newPassword) {
        userValidator.validatePassword(newPassword);

        InfoUser user = searchUser(login);
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean openUser(String login) {
        InfoUser user = searchUser(login);
        user.setDelUser(null);
        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean closeUser(String login, String delUserLogin) {
        InfoUser user = searchUser(login);
        InfoUser delUser = searchUser(delUserLogin);
        user.setDelUser(delUser.getId());
        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public InfoUser searchUser(String login) {
        userValidator.validateLogin(login);
        InfoUser user = userRepository.findFirstByLogin(login);
        if (user == null) {
            LOGGER.trace("HibernateUserService:searchUser пользователь не найден");
            throw new AuthServiceException("запрашиваемый пользователь не найден");
        }
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasPremission(String nameUser, String nameOperation) {
        boolean result = false;
        InfoOperation operation = operationsService.findFirstByName(nameOperation);
        InfoUser user = userRepository.findFirstByLogin(nameOperation);
        Set<InfoRole> roles = user.getRoles();
        for (InfoRole role : roles) {
            if (role.getOperations().contains(operation)) {
                result = true;
            }
        }
        return result;
    }
}
