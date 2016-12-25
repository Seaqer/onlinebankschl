package ru.sbt.core.authservice.entity;

import ru.sbt.core.authservice.Operation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Информация об операции
 */
@Entity
@Table(name = "OPERATIONS")
public class InfoOperation implements Operation  {

    @Id
    @GeneratedValue
    @Column(name = "OPER_ID")
    private Long id;

    @Column(name = "OPER_NAME", unique = true, nullable = false)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<InfoRole> roles = new HashSet<>();

    /**
     * Создать операцию
     *
     * @param name имя операции
     */
    public InfoOperation(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Создать операцию
     *
     * @param name имя операции
     */
    public InfoOperation(String name) {

        this.name = name;
    }

    public InfoOperation() {

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

        InfoOperation that = (InfoOperation) o;

        if (!id.equals(that.id)) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InfoOperation{" +
                "name='" + name + '\'' +
                '}';
    }
}
