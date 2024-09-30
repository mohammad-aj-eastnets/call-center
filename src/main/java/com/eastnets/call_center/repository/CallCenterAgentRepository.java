package com.eastnets.call_center.repository;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.repositoryInterfaces.ICallCenterAgentRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CallCenterAgentRepository implements ICallCenterAgentRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CallCenterAgentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final class CallCenterAgentMapper implements RowMapper<CallCenterAgent> {
        @Override
        public CallCenterAgent mapRow(ResultSet rs, int rowNum) throws SQLException {
            CallCenterAgent agent = new CallCenterAgent();
            agent.setId(rs.getLong("id"));
            agent.setName(rs.getString("name"));
            agent.setStatus(AgentStatus.valueOf(rs.getString("status")));
            agent.setStatusTime(rs.getTimestamp("status_time"));
            return agent;
        }
    }

    @Override
    public CallCenterAgent findById(Long id) {
        String sql = "SELECT * FROM agents WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<CallCenterAgent> agents = namedParameterJdbcTemplate.query(sql, params, new CallCenterAgentMapper());
        return agents.isEmpty() ? null : agents.get(0);
    }

    @Override
    public List<CallCenterAgent> findAll() {
        String sql = "SELECT * FROM agents";
        return namedParameterJdbcTemplate.query(sql, new CallCenterAgentMapper());
    }

    @Override
    public void save(CallCenterAgent agent) {
        String sql = "INSERT INTO agents (name, status, status_time) VALUES (:name, :status, :status_time)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", agent.getName());
        params.addValue("status", agent.getStatus().name());
        params.addValue("status_time", agent.getStatusTime());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM agents WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Long getStatusDuration(Long id, String status) {
        String sql = "SELECT SUM(TIMESTAMPDIFF(SECOND, status_time, CURRENT_TIMESTAMP)) " +
                "FROM agents WHERE id = :id AND status = :status";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("status", status);
        List<Long> durations = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong(1));
        return durations.isEmpty() ? 0 : durations.get(0);
    }
}
