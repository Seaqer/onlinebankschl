package ru.sbt.authservice.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.core.authservice.Operation;
import ru.sbt.core.authservice.entity.InfoOperation;
import ru.sbt.core.authservice.entity.InfoRole;
import ru.sbt.authservice.exceptions.AuthServiceException;
import ru.sbt.authservice.interfaces.repository.OperationJpaRepository;
import ru.sbt.authservice.interfaces.service.OperationsService;
import ru.sbt.authservice.interfaces.service.RoleService;
import ru.sbt.core.common.Validator;

@Service
public class HibernateOperationService implements OperationsService {
    private final OperationJpaRepository operationRepository;
    private final RoleService roleService;
    private final Validator<String> validator;
    private static final Logger LOGGER = Logger.getLogger(HibernateOperationService.class);

    @Autowired
    public HibernateOperationService(OperationJpaRepository hibernateOperationRepository, RoleService roleService, Validator<String> validator) {
        this.operationRepository = hibernateOperationRepository;
        this.roleService = roleService;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean createOperation(Operation operation) {
        validator.validate(operation.getName());
        if(operationRepository.findFirstByName(operation.getName())!=null){
            throw new AuthServiceException("Операция уже существует");
        }

        operationRepository.save(new InfoOperation(operation.getName()));
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean updateOperation(String nameOperation, String newNameOperation) {
        validator.validate(newNameOperation);
        InfoOperation operation = searchOperation(nameOperation);
        operation.setName(newNameOperation);
        operationRepository.save(operation);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean deleteOperation(Operation operation) {
        InfoOperation sOperation = searchOperation(operation.getName());
        operationRepository.delete(sOperation);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public InfoOperation searchOperation(String nameOperation) {
        validator.validate(nameOperation);
        InfoOperation operation = operationRepository.findFirstByName(nameOperation);
        if (operation == null) {
            LOGGER.trace("HibernateOperationService:searchOperation операция не найденна");
            throw new AuthServiceException("запрашиваемая операция не найденна");
        }
        return operation;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean giveRoleOperation(String nameOperation, String nameRole) {
        InfoOperation operation = searchOperation(nameOperation);
        InfoRole role = roleService.searchRole(nameRole);
        return role.getOperations().add(operation);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean removeRoleOperation(String nameOperation, String nameRole) {
        InfoOperation operation = searchOperation(nameOperation);
        InfoRole role = roleService.searchRole(nameRole);
        return role.getOperations().remove(operation);
    }
}
