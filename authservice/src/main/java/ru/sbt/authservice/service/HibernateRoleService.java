package ru.sbt.authservice.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.core.authservice.Role;
import ru.sbt.core.authservice.entity.InfoRole;
import ru.sbt.core.authservice.entity.InfoUser;
import ru.sbt.authservice.exceptions.AuthServiceException;
import ru.sbt.authservice.interfaces.repository.RoleJpaRepository;
import ru.sbt.authservice.interfaces.service.RoleService;
import ru.sbt.authservice.interfaces.service.UserService;
import ru.sbt.core.common.Validator;


@Service
public class HibernateRoleService implements RoleService {
    private final RoleJpaRepository roleRepository;
    private final UserService userService;
    private final Validator<String> validator;
    private static final Logger LOGGER = Logger.getLogger(HibernateRoleService.class);

    /**
     * Создать сервисный слой
     * @param userService Сервис для работы с пользователями
     * @param roleRepository Интерфейс для работы с БД
     * @param validator Интерфейс для проверки
     */
    @Autowired
    public HibernateRoleService(RoleJpaRepository roleRepository, UserService userService, Validator<String> validator) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean createRole(Role role) {
        validator.validate(role.getName());
        if(roleRepository.findFirstByName(role.getName())!=null){
            throw new AuthServiceException("Роль уже существует");
        }

        roleRepository.save(new InfoRole(role.getName()));
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean updateRole(String nameRole, String newNameRole) {
        validator.validate(newNameRole);
        InfoRole role = searchRole(nameRole);
        role.setName(newNameRole);
        roleRepository.save(role);
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean deleteRole(Role role) {
        InfoRole sRole = searchRole(role.getName());
        roleRepository.delete(sRole);
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public InfoRole searchRole(String nameRole) {
        validator.validate(nameRole);
        InfoRole role = roleRepository.findFirstByName(nameRole);
        if (role == null) {
            LOGGER.trace("SimpleOperationsService:searchUser роль не найденна");
            throw new AuthServiceException("запрашиваемая роль не найденна");
        }
        return role;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean giveUserRole(String login, String nameRole) {
        InfoRole role = searchRole(nameRole);
        InfoUser user = userService.searchUser(login);
        return user.getRoles().add(role);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean removeUserRole(String login, String nameRole){
        InfoRole role = searchRole(nameRole);
        InfoUser user = userService.searchUser(login);
        return user.getRoles().remove(role);
    }
}
