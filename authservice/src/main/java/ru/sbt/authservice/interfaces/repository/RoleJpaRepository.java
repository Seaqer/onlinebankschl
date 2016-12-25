package ru.sbt.authservice.interfaces.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sbt.core.authservice.entity.InfoRole;

@Repository
public interface RoleJpaRepository  extends JpaRepository<InfoRole, Long> {
    InfoRole findFirstByName(String name);
}
