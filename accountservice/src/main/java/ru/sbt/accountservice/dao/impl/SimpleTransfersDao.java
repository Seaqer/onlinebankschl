package ru.sbt.accountservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sbt.core.accountservice.entity.InfoTransfers;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.accountservice.dao.TransfersDao;
import ru.sbt.core.accountservice.Transfers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SimpleTransfersDao implements TransfersDao {
    private final String INSERT_TRANSFERS="INSERT INTO TRANSFERS VALUES (TRNS_ID_SEQ.NEXTVAL, :amount , :fromAccount , :toAccount ,SYSDATE);";
    private final String UPDATE_TRANSFERS="UPDATE TRANSFERS SET AMOUNT= :amount , FROM_ACCOUNT= :fromAccount , TO_ACCOUNT= :toAccount , TRNS_DATE = SYSDATE WHERE TRNS_ID= :id ;";
    private final String DELETE_TRANSFERS="DELETE FROM TRANSFERS WHERE TRNS_ID= :id ;";
    private final String SEARCH_TRANSFERS="SELECT * FROM TRANSFERS WHERE TRNS_ID= :id ;";
    private final String SEARCH_TRANSFERS_ACCOUNT="SELECT * FROM TRANSFERS WHERE FROM_ACCOUNT = :fromAccount AND TRNS_DATE > trunc(SYSDATE-30) ;";
    private final TransfersRowMapper statusRowMapper = new TransfersRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Transfers addElement(Transfers transfers) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_TRANSFERS, new BeanPropertySqlParameterSource(transfers), keyHolder);
        Long id = (Long) keyHolder.getKey();
        transfers.setId(id);
        return transfers;
    }

    @Override
    public long updateElement(Transfers transfers) {
        return namedParameterJdbcTemplate.update(UPDATE_TRANSFERS, new BeanPropertySqlParameterSource(transfers));
    }

    @Override
    public long deleteElement(Transfers transfers) {
        return namedParameterJdbcTemplate.update(DELETE_TRANSFERS, new BeanPropertySqlParameterSource(transfers));
    }

    @Override
    public List<Transfers> getElements(Transfers transfers) {
        List<Transfers> transferses = namedParameterJdbcTemplate.query(SEARCH_TRANSFERS, new MapSqlParameterSource("id", transfers.getId()), statusRowMapper);
        if (transferses.isEmpty()) {
            throw new AccountServiceException("переводы не найдены");
        }
        return transferses;
    }

    @Override
    public List<Transfers> getTransfersByAccount(Long accountId) {
        List<Transfers> transferses = namedParameterJdbcTemplate.query(SEARCH_TRANSFERS_ACCOUNT, new MapSqlParameterSource("fromAccount", accountId), statusRowMapper);
        if (transferses.isEmpty()) {
            throw new AccountServiceException("переводы не найдены");
        }
        return transferses;
    }

    private static class TransfersRowMapper implements RowMapper<Transfers> {
        @Override
        public Transfers mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new InfoTransfers(
                    rs.getLong("TRNS_ID"),
                    rs.getBigDecimal("AMOUNT"),
                    rs.getLong("FROM_ACCOUNT"),
                    rs.getLong("TO_ACCOUNT"),
                    rs.getTimestamp("TRNS_DATE").toLocalDateTime()
            );
        }
    }
}
