package ru.sbt.accountservice.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sbt.accountservice.dao.AccountDao;
import ru.sbt.core.accountservice.entity.InfoAccount;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.core.accountservice.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SimpleAccountDao implements AccountDao {
    private final String INSERT_ACCOUNT = "INSERT INTO ACCOUNT VALUES(ACC_ID_SEQ.NEXTVAL,ACC_NUMBER_SEQ.NEXTVAL, 0 , :accountType , :clientId , SYSDATE , :accountStatus );";
    private final String UPDATE_ACCOUNT = "UPDATE ACCOUNT SET ACC_NUMBER= :accountNumber , BALANCE= :balance , ACTP_ACTP_NAME= :accountType WHERE ACC_ID= :id ";
    private final String DELETE_ACCOUNT = "DELETE FROM ACCOUNT WHERE ACC_ID = :id ;";
    private final String SEARCH_ACCOUNT = "SELECT * FROM ACCOUNT WHERE ACC_NUMBER = :accountNumber ;";
    private final String SEARCH_ACCOUNT_CLNT = "SELECT * FROM ACCOUNT WHERE CLIENT_ID = :clientId ;";
    private final String UPDATE_BALANCE = "UPDATE ACCOUNT SET BALANCE= :balance WHERE ACC_ID= :id ; ";
    private final String UPDATE_ACCOUNT_STATUS = "UPDATE ACCOUNT SET STAT_STAT_ID= :accountStatus WHERE ACC_ID= :id ;";
    private final AccountRowMapper accountRowMapper = new AccountRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Account addElement(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_ACCOUNT, new BeanPropertySqlParameterSource(account), keyHolder);
        Long id = (Long) keyHolder.getKey();
        account.setId(id);
        return account;
    }

    @Override
    public long updateElement(Account account) {
        return namedParameterJdbcTemplate.update(UPDATE_ACCOUNT, new BeanPropertySqlParameterSource(account));
    }

    @Override
    public long deleteElement(Account account) {
        return namedParameterJdbcTemplate.update(DELETE_ACCOUNT, new BeanPropertySqlParameterSource(account));
    }

    @Override
    public List<Account> getElements(Account account) {
        List<Account> accounts = namedParameterJdbcTemplate.query(SEARCH_ACCOUNT, new MapSqlParameterSource("accountNumber", account.getAccountNumber()), accountRowMapper);
        if (accounts.isEmpty()) {
            throw new AccountServiceException("Не найдено ни одного счета");
        }
        return accounts;
    }

    @Override
    public List<Account> getElementsByClntId(Account account) {
        return namedParameterJdbcTemplate.query(SEARCH_ACCOUNT_CLNT, new MapSqlParameterSource("clientId", account.getClientId()), accountRowMapper);
    }

    @Override
    public long updateBalance(Account account) {
        return namedParameterJdbcTemplate.update(UPDATE_BALANCE, new BeanPropertySqlParameterSource(account));
    }

    @Override
    public long updateAccountStatus(Account account) {
        return namedParameterJdbcTemplate.update(UPDATE_ACCOUNT_STATUS, new BeanPropertySqlParameterSource(account));
    }

    private static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new InfoAccount(
                    rs.getLong("ACC_ID"),
                    rs.getLong("ACC_NUMBER"),
                    rs.getBigDecimal("BALANCE"),
                    rs.getLong("ACTP_ACTP_ID"),
                    rs.getLong("CLIENT_ID"),
                    rs.getTimestamp("CREATION_DATE").toLocalDateTime(),
                    rs.getLong("STAT_STAT_ID")
            );
        }
    }
}
