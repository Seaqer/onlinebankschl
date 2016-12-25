package ru.sbt.core.authservice.entity;


import ru.sbt.core.authservice.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Информация о пользователе
 */
@Entity
@Table(name = "USERS")
public class InfoUser implements User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;
    @Column(name = "PASS", nullable = false)
    private String password;
    @Column(name = "DEL_USER")
    private Long delUser;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<InfoRole> roles = new HashSet<>();

    /**
     * Создать пользователя
     *
     * @param id       идентификатор пользователя
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @param delUser  пользователь который закрыл текущего пользователя
     */
    public InfoUser(Long id, String login, String password, Long delUser) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.delUser = delUser;
    }

    /**
     * Создать пользователя
     */
    public InfoUser() {
    }

    /**
     * Создать пустого пользователя
     *
     * @return пустой пользователь
     */
    public static InfoUser createEmpty() {
        return new InfoUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDelUser() {
        return delUser;
    }

    public void setDelUser(Long delUser) {
        this.delUser = delUser;
    }

    public Set<InfoRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<InfoRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoUser infoUser = (InfoUser) o;

        if (!id.equals(infoUser.id)) return false;
        return login.equals(infoUser.login);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InfoUser{" +
                "login='" + login + '\'' +
                ", delUser=" + delUser +
                '}';
    }
}
