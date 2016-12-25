package ru.sbt.authservice.interfaces.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sbt.core.authservice.entity.InfoOperation;

@Repository
public interface OperationJpaRepository extends JpaRepository<InfoOperation, Long>{
    InfoOperation findFirstByName(String name);
}
