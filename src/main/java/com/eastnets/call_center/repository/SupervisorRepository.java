package com.eastnets.call_center.repository;

import com.eastnets.call_center.model.Supervisor;
import com.eastnets.call_center.repositoryInterfaces.ISupervisorRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SupervisorRepository implements ISupervisorRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SupervisorRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final class SupervisorMapper implements RowMapper<Supervisor> {
        @Override
        public Supervisor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Supervisor supervisor = new Supervisor();
            supervisor.setId(rs.getLong("id"));
            supervisor.setName(rs.getString("name"));
            supervisor.setUsername(rs.getString("username"));
            supervisor.setPassword(rs.getString("password"));
            return supervisor;
        }
    }

    @Override
    public Supervisor findByUsername(String username) {
        String sql = "SELECT * FROM supervisors WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        List<Supervisor> supervisors = namedParameterJdbcTemplate.query(sql, params, new SupervisorMapper());
        return supervisors.isEmpty() ? null : supervisors.get(0);
    }

    @Override
    public Supervisor findById(Long id) {
        String sql = "SELECT * FROM supervisors WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Supervisor> supervisors = namedParameterJdbcTemplate.query(sql, params, new SupervisorMapper());
        return supervisors.isEmpty() ? null : supervisors.get(0);
    }
}
