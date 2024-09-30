package com.eastnets.call_center.repository;

import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.repositoryInterfaces.IGeneratedReportRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GeneratedReportRepository implements IGeneratedReportRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GeneratedReportRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final class ReportMapper implements RowMapper<GeneratedReport> {
        @Override
        public GeneratedReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeneratedReport report = new GeneratedReport();
            report.setId(rs.getLong("id"));
            report.setAgentId(rs.getLong("agent_id"));
            report.setReportDate(rs.getDate("report_date").toLocalDate());
            report.setTotalCalls(rs.getInt("total_calls"));
            report.setTotalTalkTime(rs.getLong("total_talk_time"));
            report.setLongestTalkTime(rs.getLong("longest_talk_time"));
            report.setShortestTalkTime(rs.getLong("shortest_talk_time"));
            report.setTotalNotReadyTime(rs.getLong("total_not_ready_time"));
            report.setAverageCalls(rs.getDouble("average_calls"));
            return report;
        }
    }

    @Override
    public GeneratedReport findById(Long id) {
        String sql = "SELECT * FROM reports WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<GeneratedReport> reports = namedParameterJdbcTemplate.query(sql, params, new ReportMapper());
        return reports.isEmpty() ? null : reports.get(0);
    }

    @Override
    public List<GeneratedReport> findAll() {
        String sql = "SELECT * FROM reports";
        return namedParameterJdbcTemplate.query(sql, new ReportMapper());
    }

    @Override
    public void save(GeneratedReport report) {
        String sql = "INSERT INTO reports (agent_id, report_date, total_calls, total_talk_time, longest_talk_time, shortest_talk_time, total_not_ready_time, average_calls) VALUES (:agent_id, :report_date, :total_calls, :total_talk_time, :longest_talk_time, :shortest_talk_time, :total_not_ready_time, :average_calls)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agent_id", report.getAgentId());
        params.addValue("report_date", report.getReportDate());
        params.addValue("total_calls", report.getTotalCalls());
        params.addValue("total_talk_time", report.getTotalTalkTime());
        params.addValue("longest_talk_time", report.getLongestTalkTime());
        params.addValue("shortest_talk_time", report.getShortestTalkTime());
        params.addValue("total_not_ready_time", report.getTotalNotReadyTime());
        params.addValue("average_calls", report.getAverageCalls());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reports WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
