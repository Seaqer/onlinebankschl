package ru.sbt.accountservice.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sbt.core.accountservice.entity.InfoStatus;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.accountservice.dao.StatusDao;
import ru.sbt.core.accountservice.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SimpleStatusDao implements StatusDao {
    private final String INSERT_STATUS = "insert into STATUS VALUES(STAT_ID_SEQ.NEXTVAL,:name);";
    private final String UPDATE_STATUS = "UPDATE STATUS SET STAT_NAME= :name WHERE STAT_ID= :id;";
    private final String DELETE_STATUS = "DELETE FROM STATUS WHERE STAT_ID= :id ;";
    private final String SEARCH_STATUS_ID = "SELECT * FROM STATUS WHERE STAT_ID = :id ;";
    private final String SEARCH_STATUS_NAME = "SELECT * FROM STATUS WHERE STAT_NAME = :name ;";
    private final String SEARCH_ALL_STATUS = "SELECT * FROM STATUS ;";
    private final StatusRowMapper statusRowMapper = new StatusRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Status addElement(Status status) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_STATUS, new BeanPropertySqlParameterSource(status), keyHolder);
        Long id = (Long) keyHolder.getKey();
        status.setId(id);
        return status;
    }

    @Override
    public long updateElement(Status status) {
        return namedParameterJdbcTemplate.update(UPDATE_STATUS, new BeanPropertySqlParameterSource(status));
    }

    @Override
    public long deleteElement(Status status) {
        return namedParameterJdbcTemplate.update(DELETE_STATUS, new BeanPropertySqlParameterSource(status));
    }

    @Override
    public List<Status> getElements(Status status) {
        List<Status> statuses = namedParameterJdbcTemplate.query(SEARCH_STATUS_ID, new MapSqlParameterSource("id", status.getId()), statusRowMapper);
        if (statuses.isEmpty()) {
            throw new AccountServiceException("статусы не найдены");
        }
        return statuses;
    }

    @Override
    public List<Status> getStatusByName(Status status) {
        List<Status> statuses = namedParameterJdbcTemplate.query(SEARCH_STATUS_NAME, new MapSqlParameterSource("name", status.getName()), statusRowMapper);
        if (statuses.isEmpty()) {
            throw new AccountServiceException("статусы не найдены");
        }
        return statuses;
    }

    @Override
    public List<Status> getAllStatus() {
        List<Status> statuses = namedParameterJdbcTemplate.query(SEARCH_ALL_STATUS, statusRowMapper);
        if (statuses.isEmpty()) {
            throw new AccountServiceException("статусы не найдены");
        }
        return statuses;
    }

    private static class StatusRowMapper implements RowMapper<Status> {
        @Override
        public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new InfoStatus(
                    rs.getLong("STAT_ID"),
                    rs.getString("STAT_NAME")
            );
        }
    }
}
