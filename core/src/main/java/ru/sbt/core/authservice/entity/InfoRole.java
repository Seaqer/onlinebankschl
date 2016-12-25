package ru.sbt.core.authservice.entity;


import ru.sbt.core.authservice.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Информация о роли
 */
@Entity
@Table(name = "ROLES")
public class InfoRole implements Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(name = "ROLE_NAME", unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<InfoUser> users = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<InfoOperation> operations = new HashSet<>();

    /**
     * Создать роль
     *
     * @param id   идентификатор роли
     * @param name название роли
     */
    public InfoRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Создать роль
     *
     * @param name название роли
     */
    public InfoRole(String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Создать роль
     */
    public InfoRole(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoRole infoRole = (InfoRole) o;

        if (!id.equals(infoRole.id)) return false;
        return name.equals(infoRole.name);

    }

    public Set<InfoUser> getUsers() {
        return users;
    }

    public void setUsers(Set<InfoUser> users) {
        this.users = users;
    }

    public Set<InfoOperation> getOperations() {
        return operations;
    }

    public void setOperations(Set<InfoOperation> operations) {
        this.operations = operations;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InfoRole{" +
                "name='" + name + '\'' +
                '}';
    }
}
