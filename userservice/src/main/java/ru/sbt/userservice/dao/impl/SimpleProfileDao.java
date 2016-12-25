package ru.sbt.userservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sbt.core.userservice.Client;
import ru.sbt.core.userservice.entity.InfoClient;
import ru.sbt.userservice.exception.UserServiceException;
import ru.sbt.userservice.dao.ProfileDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SimpleProfileDao implements ProfileDao {
    private final String INSERT_PROFILE = "INSERT INTO CLIENTS VALUES(CLIENT_SEQ.NEXTVAL, :idUser , :first_name , :last_name , :second_name , :inn ) ;";
    private final String UPDATE_PROFILE = "update CLIENTS SET FIST_NAME= :first_name , LAST_NAME = :last_name , SECOND_NAME= :second_name, INN = :inn WHERE CLN_ID= :id ;";
    private final String DELETE_PROFILE = "DELETE FROM CLIENTS WHERE CLN_ID = :id ;";
    private final String SEARCH_PROFILE = "SELECT * FROM CLIENTS WHERE USER_USER_ID= :idUser; ";


    private final ProfileRowMapper profileRowMapper = new ProfileRowMapper();
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Client addElement(Client profile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_PROFILE, new BeanPropertySqlParameterSource(profile), keyHolder);
        Long id = (Long) keyHolder.getKey();
        profile.setId(id);
        return profile;
    }

    @Override
    public long updateElement(Client profile) {
        return namedParameterJdbcTemplate.update(UPDATE_PROFILE, new BeanPropertySqlParameterSource(profile));
    }


    @Override
    public long deleteElement(Client profile) {
        return namedParameterJdbcTemplate.update(DELETE_PROFILE, new BeanPropertySqlParameterSource(profile));
    }

    @Override
    public List<Client> getElements(Client profile) {
        List<Client> user = namedParameterJdbcTemplate.query(SEARCH_PROFILE, new MapSqlParameterSource("idUser", profile.getIdUser()), profileRowMapper);
        if (user.isEmpty()) {
            throw new UserServiceException("Не найдено ни одного профиля");
        }
        return user;
    }

    private static class ProfileRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new InfoClient(
                    rs.getLong("CLN_ID"),
                    rs.getLong("USER_USER_ID"),
                    rs.getString("FIST_NAME"),
                    rs.getString("LAST_NAME"),
                    rs.getString("SECOND_NAME"),
                    rs.getLong("INN")
            );
        }
    }
}
