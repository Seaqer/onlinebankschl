package ru.sbt.authservice.interfaces.service;


import ru.sbt.core.authservice.User;
import ru.sbt.core.authservice.entity.InfoUser;

/**
 * Сервисный слой для работы с пользователями
 */
public interface UserService {

    /**
     * Регистрация пользователя
     *
     * @param user Логин и пароль
     * @return зарегистрирован ли пользователь
     */
    boolean registrationUser(User user);

    /**
     * Сменить логин пользователя
     *
     * @param login    логин
     * @param newLogin новый логин
     * @return зарегистрирован ли пользователь
     */
    boolean updateLogin(String login, String newLogin);

    /**
     * Сменить пароль пользователя
     *
     * @param login       логин
     * @param newPassword новый пароль
     * @return сменен ли пароль
     */
    boolean updatePassword(String login, String newPassword);

    /**
     * Разблокировать пользователя
     *
     * @param login логин
     * @return разблокирован ли прользователь
     */
    boolean openUser(String login);

    /**
     * Закрыть пользователя
     *
     * @param login        логин закрываемого пользователя
     * @param delUserLogin логин закрывающего пользователя
     * @return закрыт ли пользователь
     */
    boolean closeUser(String login, String delUserLogin);

    /**
     * Найти пользователя
     *
     * @param login логин
     * @return Найденный пользователь
     */
    InfoUser searchUser(String login);

    /**
     * Проверка прав у пользователя на операцию
     *
     * @param nameOperation      Пользователь
     * @param nameOperation Операция
     * @return есть ли права на операцию
     */
    boolean hasPremission(String nameUser, String nameOperation);

}
