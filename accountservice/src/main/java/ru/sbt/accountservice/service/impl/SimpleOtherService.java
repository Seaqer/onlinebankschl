package ru.sbt.accountservice.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.accountservice.dao.AccountTypeDao;
import ru.sbt.accountservice.dao.StatusDao;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.accountservice.service.OtherService;
import ru.sbt.core.accountservice.AccountType;
import ru.sbt.core.accountservice.Status;
import ru.sbt.core.accountservice.entity.InfoAccountType;
import ru.sbt.core.accountservice.entity.InfoStatus;
import ru.sbt.core.common.Validator;

import java.util.List;

@Service
public class SimpleOtherService implements OtherService {
    private final StatusDao statusDao;
    private final AccountTypeDao accountTypeDao;
    Validator<String> validator;
    private static final Logger LOGGER = Logger.getLogger(SimpleOtherService.class);

    @Autowired
    public SimpleOtherService(StatusDao statusDao, AccountTypeDao accountTypeDao, Validator<String> validator) {
        this.statusDao = statusDao;
        this.accountTypeDao = accountTypeDao;
        this.validator = validator;
    }

    /**
     * Cоздание статуса счета
     *
     * @param status статус
     * @return создан ли статус
     */
    @Transactional
    @Override
    public boolean createStatus(Status status) {
        validator.validate(status.getName());
        statusDao.addElement(status);
        return true;
    }

    /**
     * Создание типа счета
     *
     * @param accountType
     * @return создан ли тип
     */
    @Transactional
    @Override
    public boolean createType(AccountType accountType) {
        validator.validate(accountType.getName());
        accountTypeDao.addElement(accountType);
        return true;
    }

    /**
     * Обновление статуса счета
     *
     * @param nameStatus    имя статуса
     * @param newNameStatus новое имя статуса
     * @return обновлен ли статуса счета
     */
    @Transactional
    @Override
    public boolean updateStatus(String nameStatus, String newNameStatus) {
        validator.validate(newNameStatus);
        Status status = searchStatusByName(nameStatus);
        status.setName(newNameStatus);
        statusDao.updateElement(status);
        return true;
    }

    /**
     * Обновление типа счета
     *
     * @param nameType    название типа счета
     * @param newNameType новое название типа счета
     * @return обновлен ли тип счета
     */
    @Transactional
    @Override
    public boolean updateType(String nameType, String newNameType) {
        validator.validate(newNameType);
        AccountType accountType = searchTypeByName(nameType);
        accountType.setName(newNameType);
        return accountTypeDao.updateElement(accountType) < 0;
    }

    /**
     * Удалить статус счета
     *
     * @param name навзание
     * @return удален ли
     */
    @Transactional
    @Override
    public boolean deleteStatus(String name) {
        Status status = searchStatusByName(name);
        statusDao.deleteElement(status);
        return true;
    }

    /**
     * Удалить тип счета
     *
     * @param name название
     * @return удален ли
     */
    @Transactional
    @Override
    public boolean deleteAccountType(String name) {
        AccountType accountType = searchTypeByName(name);
        accountTypeDao.deleteElement(accountType);
        return true;
    }

    /**
     * Поиск статуса счета по ID
     *
     * @param id id статуса
     * @return статус
     */
    @Transactional
    @Override
    public Status searchStatusById(Long id) {
        Status status = InfoStatus.createEmpty();
        status.setId(id);

        List<Status> statusList = statusDao.getElements(status);
        if (statusList.size() < 1) {
            LOGGER.trace("SimpleOtherService:searchStatusById cтатус счета не найден id:" + id);
            throw new AccountServiceException("cтатус счета не найден");
        }
        return statusList.get(0);
    }

    /**
     * Поиск типа счета по ID
     *
     * @param id id типа
     * @return тип
     */
    @Transactional
    @Override
    public AccountType searchTypeById(Long id) {
        AccountType accountType = InfoAccountType.createEmpty();
        accountType.setId(id);

        List<AccountType> accountTypes = accountTypeDao.getElements(accountType);
        if (accountTypes.size() < 1) {
            LOGGER.trace("SimpleOtherService:searchTypeById тип счета не найден id:" + id);
            throw new AccountServiceException("cтатус счета не найден");
        }
        return accountTypes.get(0);
    }

    /**
     * Поиск статуса счета по названию
     *
     * @param name название
     * @return статус
     */
    @Transactional
    @Override
    public Status searchStatusByName(String name) {
        validator.validate(name);
        Status status = InfoStatus.createEmpty();
        status.setName(name);

        List<Status> statusList = statusDao.getStatusByName(status);
        if (statusList.size() < 1) {
            LOGGER.trace("SimpleOtherService:searchStatusById cтатус счета не найден, навзание:" + name);
            throw new AccountServiceException("cтатус счета не найден");
        }
        return statusList.get(0);
    }

    /**
     * Поиск типа счета по названию
     *
     * @param name название
     * @return тип
     */
    @Transactional
    @Override
    public AccountType searchTypeByName(String name) {
        validator.validate(name);
        AccountType accountType = InfoAccountType.createEmpty();
        accountType.setName(name);

        List<AccountType> accountTypes = accountTypeDao.getAccountTypeByName(accountType);
        if (accountTypes.size() < 1) {
            LOGGER.trace("SimpleOtherService:searchTypeById тип счета не найден, название:" + name);
            throw new AccountServiceException("cтатус счета не найден");
        }
        return accountTypes.get(0);
    }

    /**
     * Получение всех статусов
     *
     * @return статусs
     */
    @Transactional
    @Override
    public List<Status> searchAllStatus() {
        List<Status> statusList = statusDao.getAllStatus();
        if (statusList.size() < 1) {
            LOGGER.trace("SimpleOtherService:searchStatusById cтатус счетов не найдены");
            throw new AccountServiceException("cтатусы счетов не найден");
        }
        return statusList;
    }

    /**
     * Получить все типы счетов
     *
     * @return тип
     */
    @Transactional
    @Override
    public List<AccountType> searchALLType() {
        List<AccountType> accountTypes = accountTypeDao.getAllAccountType();
        if (accountTypes.size() < 1) {
            LOGGER.trace("SimpleOtherService:searchTypeById тип счета не найдены");
            throw new AccountServiceException("cтатус счета не найден");
        }
        return accountTypes;
    }

}
