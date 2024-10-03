package com.eastnets.call_center.repository;

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
            call.setCallID(rs.getInt("callID"));
            call.setAgentID(rs.getLong("agentID"));
            call.setStartTime(rs.getTimestamp("startTime").toLocalDateTime());
            call.setEndTime(rs.getTimestamp("endTime") != null ? rs.getTimestamp("endTime").toLocalDateTime() : null);
            call.setDuration(rs.getInt("duration"));
            return call;
        }
    }

    @Override
    public void save(Call call) {
        String sql = "INSERT INTO calls (agentID, startTime, endTime, duration) VALUES (:agentID, :startTime, :endTime, :duration)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agentID", call.getAgentID());
        params.addValue("startTime", call.getStartTime());
        params.addValue("endTime", call.getEndTime());
        params.addValue("duration", call.getDuration());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Call findById(int callID) {
        String sql = "SELECT * FROM calls WHERE callID = :callID";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("callID", callID);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new CallMapper());
    }

    @Override
    public List<Call> findAll() {
        String sql = "SELECT * FROM calls";
        return namedParameterJdbcTemplate.query(sql, new CallMapper());
    }

    @Override
    public void update(Call call) {
        String sql = "UPDATE calls SET agentID = :agentID, startTime = :startTime, endTime = :endTime, duration = :duration WHERE callID = :callID";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agentID", call.getAgentID());
        params.addValue("startTime", call.getStartTime());
        params.addValue("endTime", call.getEndTime());
        params.addValue("duration", call.getDuration());
        params.addValue("callID", call.getCallID());
        namedParameterJdbcTemplate.update(sql, params);
    }
}
