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
import java.sql.Timestamp;
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
            agent.setId(rs.getLong("agentID"));
            agent.setName(rs.getString("agent"));
            agent.setStatus(AgentStatus.valueOf(rs.getString("status")));
            agent.setStatusTime(rs.getTimestamp("time"));
            agent.setTotalNumberOfCalls(rs.getInt("totalNumberOfCalls"));
            return agent;
        }
    }

    @Override
    public CallCenterAgent findById(Long id) {
        String sql = "SELECT * FROM Agents WHERE agentID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<CallCenterAgent> agents = namedParameterJdbcTemplate.query(sql, params, new CallCenterAgentMapper());
        return agents.isEmpty() ? null : agents.get(0);
    }

    @Override
    public List<CallCenterAgent> findAll() {
        String sql = "SELECT * FROM Agents";
        return namedParameterJdbcTemplate.query(sql, new CallCenterAgentMapper());
    }

    @Override
    public void save(CallCenterAgent agent) {
        String sql = "INSERT INTO Agents (agentID, status, agent, time, totalNumberOfCalls) VALUES (:agentID, :status, :agent, :time, :totalNumberOfCalls)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agentID", agent.getId());
        params.addValue("status", agent.getStatus().name());
        params.addValue("agent", agent.getName());
        params.addValue("time", agent.getStatusTime());
        params.addValue("totalNumberOfCalls", agent.getTotalNumberOfCalls());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Agents WHERE agentID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Long getStatusDuration(Long id, String status) {
        String sql = "SELECT SUM(TIMESTAMPDIFF(SECOND, time, CURRENT_TIMESTAMP)) " +
                "FROM Agents WHERE agentID = :id AND status = :status";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("status", status);
        List<Long> durations = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong(1));
        return durations.isEmpty() ? 0 : durations.get(0);
    }

    @Override
    public boolean updateStatus(Long id, AgentStatus newStatus) {
        String sql = "UPDATE Agents SET status = :status, time = :time WHERE agentID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", newStatus.name());
        params.addValue("time", new Timestamp(System.currentTimeMillis()));
        params.addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public void incrementTotalCalls(Long id) {
        String sql = "UPDATE Agents SET totalNumberOfCalls = totalNumberOfCalls + 1 WHERE agentID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
