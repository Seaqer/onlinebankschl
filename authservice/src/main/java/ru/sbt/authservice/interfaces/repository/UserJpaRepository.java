package ru.sbt.authservice.interfaces.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sbt.core.authservice.entity.InfoUser;

@Repository
public interface UserJpaRepository extends JpaRepository<InfoUser, Long> {
    InfoUser findFirstByLogin(String login);
}
