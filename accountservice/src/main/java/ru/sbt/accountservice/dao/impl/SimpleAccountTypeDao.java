package ru.sbt.accountservice.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sbt.core.accountservice.entity.InfoAccountType;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.accountservice.dao.AccountTypeDao;
import ru.sbt.core.accountservice.AccountType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SimpleAccountTypeDao implements AccountTypeDao {
    private final String INSERT_STATUS="INSERT INTO ACCOUNT_TYPE VALUES(ACTP_ID_SEQ.NEXTVAL,:name);";
    private final String UPDATE_STATUS="UPDATE ACCOUNT_TYPE SET ACTP_NAME= :name where ACTP_ID= :id ;";
    private final String DELETE_STATUS="DELETE FROM ACCOUNT_TYPE WHERE ACTP_ID= :id ;";
    private final String SEARCH_STATUS_ID = "SELECT * FROM ACCOUNT_TYPE WHERE  ACTP_ID = :id ;";
    private final String SEARCH_STATUS_NAME="SELECT * FROM ACCOUNT_TYPE WHERE ACTP_NAME = :name ;";
    private final String SEARCH_ALL_STATUS="SELECT * FROM ACCOUNT_TYPE  ;";
    private final TypeRowMapper typeRowMapper = new TypeRowMapper();


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public AccountType addElement(AccountType accountType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_STATUS, new BeanPropertySqlParameterSource(accountType), keyHolder);
        Long id = (Long) keyHolder.getKey();
        accountType.setId(id);
        return accountType;
    }

    @Override
    public long updateElement(AccountType accountType) {
        return namedParameterJdbcTemplate.update(UPDATE_STATUS, new BeanPropertySqlParameterSource(accountType));
    }

    @Override
    public long deleteElement(AccountType accountType) {
        return namedParameterJdbcTemplate.update(DELETE_STATUS, new BeanPropertySqlParameterSource(accountType));
    }

    @Override
    public List<AccountType> getElements(AccountType accountType) {
        List<AccountType> accountTypes = namedParameterJdbcTemplate.query(SEARCH_STATUS_ID, new MapSqlParameterSource("id", accountType.getId()), typeRowMapper);
        if (accountTypes.isEmpty()) {
            throw new AccountServiceException("тип счета не найден");
        }
        return accountTypes;
    }

    @Override
    public List<AccountType> getAccountTypeByName(AccountType accountType) {
        List<AccountType> accountTypes = namedParameterJdbcTemplate.query(SEARCH_STATUS_NAME, new MapSqlParameterSource("name", accountType.getName()), typeRowMapper);
        if (accountTypes.isEmpty()) {
            throw new AccountServiceException("тип счета не найден");
        }
        return accountTypes;
    }

    @Override
    public List<AccountType> getAllAccountType() {
        List<AccountType> accountTypes = namedParameterJdbcTemplate.query(SEARCH_ALL_STATUS, typeRowMapper);
        if (accountTypes.isEmpty()) {
            throw new AccountServiceException("тип счета не найден");
        }
        return accountTypes;
    }

    private static class TypeRowMapper implements RowMapper<AccountType> {
        @Override
        public AccountType mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new InfoAccountType(
                    rs.getLong("ACTP_ID"),
                    rs.getString("ACTP_NAME")
            );
        }
    }
}
