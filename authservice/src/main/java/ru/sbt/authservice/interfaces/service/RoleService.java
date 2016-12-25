package ru.sbt.authservice.interfaces.service;


import ru.sbt.core.authservice.Role;
import ru.sbt.core.authservice.entity.InfoRole;

/**
 * Сервисный слой для работы с ролями
 */
public interface RoleService {
    /**
     * Создание роли
     * @param role имя роли
     * @return создана ли роль
     */
    boolean createRole(Role role);

    /**
     * Обновление роли
     * @param nameRole имя роли
     * @param newNameRole новое имя роли
     * @return обновлена ли роль
     */
    boolean updateRole(String nameRole, String newNameRole);

    /**
     * Удаление роли
     * @param role имя роли
     * @return удалена ли роль
     */
    boolean deleteRole(Role role);

    /**
     * Поиск роли
     * @param nameRole имя роли
     * @return роль
     */
    InfoRole searchRole(String nameRole);

    /**
     * Выдача роли пользователю
     * @param login логин
     * @param nameRole имя роли
     * @return выдана ли роль
     */
    boolean giveUserRole(String login, String nameRole);

    /**
     * Забрать роль у пользователя
     * @param login логин
     * @param nameRole имя роли
     * @return убрана ли роль
     */
    boolean removeUserRole(String login, String nameRole);
}
