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
import java.util.HashMap;
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
            long durationInSeconds = rs.getLong("statusDurationSeconds");
            agent.updateStatusDuration(durationInSeconds);
            agent.setTotalNumberOfCalls(rs.getInt("totalNumberOfCalls"));
            agent.accumulateNotReadyTime(rs.getLong("totalNotReadyTime"));
            return agent;
        }
    }

    @Override
    public CallCenterAgent findById(Long id) {
        String sql = "SELECT *, DATEDIFF(SECOND, time, CURRENT_TIMESTAMP) AS statusDurationSeconds FROM Agents WHERE agentID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<CallCenterAgent> agents = namedParameterJdbcTemplate.query(sql, params, new CallCenterAgentMapper());
        return agents.isEmpty() ? null : agents.get(0);
    }

    @Override
    public List<CallCenterAgent> findAll() {
        String sql = "SELECT *, DATEDIFF(SECOND, time, CURRENT_TIMESTAMP) AS statusDurationSeconds FROM Agents";
        return namedParameterJdbcTemplate.query(sql, new CallCenterAgentMapper());
    }

    @Override
    public void save(CallCenterAgent agent) {
        String sql = "INSERT INTO Agents (agentID, status, agent, time, totalNumberOfCalls, totalNotReadyTime) VALUES (:agentID, :status, :agent, CURRENT_TIMESTAMP, :totalNumberOfCalls, :totalNotReadyTime)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agentID", agent.getId());
        params.addValue("status", agent.getStatus().name());
        params.addValue("agent", agent.getName());
        params.addValue("totalNumberOfCalls", agent.getTotalNumberOfCalls());
        params.addValue("totalNotReadyTime", agent.getTotalTimeNotReady());
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
        String sql = "SELECT SUM(DATEDIFF(SECOND, time, CURRENT_TIMESTAMP)) " +
                "FROM Agents WHERE agentID = :id AND status = :status";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("status", status);
        List<Long> durations = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong(1));
        return durations.isEmpty() ? 0 : durations.get(0);
    }

    @Override
    public boolean updateStatus(Long id, AgentStatus newStatus) {
        String sql = "UPDATE Agents SET status = :status, time = CURRENT_TIMESTAMP WHERE agentID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", newStatus.name());
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

    public void updateTotalTimeNotReady(Long id, Long totalNotReadyTime) {
        String sql = "UPDATE Agents SET totalNotReadyTime = :totalNotReadyTime WHERE agentID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("totalNotReadyTime", totalNotReadyTime);
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void resetAgents() {
        String resetAgentsStatus = "UPDATE Agents SET status='NOT_READY'";
        String resetAgentsTime = "UPDATE Agents SET time=GETDATE()";
        String resetTotalNumberOfCalls = "UPDATE Agents SET totalNumberOfCalls=0";
        String resetTotalReadyTime = "UPDATE Agents SET totalReadyTime=0";
        String resetTotalNotReadyTime = "UPDATE Agents SET totalNotReadyTime=0";
        String resetTotalOnCallTime = "UPDATE Agents SET totalOnCallTime=0";

        namedParameterJdbcTemplate.update(resetAgentsStatus, new HashMap<>());
        namedParameterJdbcTemplate.update(resetAgentsTime, new HashMap<>());
        namedParameterJdbcTemplate.update(resetTotalNumberOfCalls, new HashMap<>());
        namedParameterJdbcTemplate.update(resetTotalReadyTime, new HashMap<>());
        namedParameterJdbcTemplate.update(resetTotalNotReadyTime, new HashMap<>());
        namedParameterJdbcTemplate.update(resetTotalOnCallTime, new HashMap<>());
    }
}
