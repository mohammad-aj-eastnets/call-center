package com.eastnets.call_center.repository;

import com.eastnets.call_center.enums.CallStatus;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CallRepository implements ICallRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CallRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final class CallMapper implements RowMapper<Call> {
        @Override
        public Call mapRow(ResultSet rs, int rowNum) throws SQLException {
            Call call = new Call();
            call.setId(rs.getLong("id"));
            call.setAgentId(rs.getLong("agent_id"));
            call.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
            call.setEndTime(rs.getTimestamp("end_time") != null ? rs.getTimestamp("end_time").toLocalDateTime() : null);
            call.setStatus(CallStatus.valueOf(rs.getString("status")));
            call.setCallDuration(rs.getInt("call_duration"));
            return call;
        }
    }

    @Override
    public Call findById(Long id) {
        String sql = "SELECT * FROM calls WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Call> calls = namedParameterJdbcTemplate.query(sql, params, new CallMapper());
        return calls.isEmpty() ? null : calls.get(0);
    }

    @Override
    public List<Call> findAll() {
        String sql = "SELECT * FROM calls";
        return namedParameterJdbcTemplate.query(sql, new CallMapper());
    }

    @Override
    public void save(Call call) {
        String sql = "INSERT INTO calls (agent_id, start_time, end_time, status, call_duration) VALUES (:agent_id, :start_time, :end_time, :status, :call_duration)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agent_id", call.getAgentId());
        params.addValue("start_time", call.getStartTime());
        params.addValue("end_time", call.getEndTime());
        params.addValue("status", call.getStatus().name());
        params.addValue("call_duration", call.getCallDuration());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM calls WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
