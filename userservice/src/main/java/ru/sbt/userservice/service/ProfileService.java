package ru.sbt.userservice.service;


import ru.sbt.core.userservice.Client;

public interface ProfileService {
    /**
     * Регистрация профиля
     *
     * @param profile профиль
     * @return зарегистрирован ли профиль
     */
    boolean createProfile(Client profile);

    /**
     * Смена ФИО в профиле
     *
     * @param profile
     * @return изменен ли ФИО
     */
    boolean updateFIO(Client profile);

    /**
     * Смена ИНН
     *
     * @param profile профиль
     * @return сменен ли ИНН
     */
    boolean updateInn(Client profile);

    /**
     * Найти пользователь которому принадлежит профиль
     *
     * @param idUser логин
     * @return Найденный пользователь
     */
    Client searchProfile(Long idUser);

    /**
     * Удалить профиль
     *
     * @param idUser пользователь которому принадлежит профиль
     * @return удален ли профиль
     */
    boolean deleteProfile(Long idUser);
}
