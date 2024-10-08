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

    private static final class GeneratedReportMapper implements RowMapper<GeneratedReport> {
        @Override
        public GeneratedReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeneratedReport report = new GeneratedReport();
            report.setReportID(rs.getInt("reportID"));
            report.setAgentID(rs.getLong("agentID"));
            report.setTotalNumberOfCalls(rs.getObject("totalNumberOfCalls", Integer.class));
            report.setTotalTalkTime(rs.getObject("totalTalkTime", Long.class));
            report.setLongestTalkTime(rs.getObject("longestTalkTime", Long.class));
            report.setShortestTalkTime(rs.getObject("shortestTalkTime", Long.class));
            report.setTotalTimeNotReady(rs.getObject("totalTimeNotReady", Long.class));
            report.setAvgRecOnTotal(rs.getObject("avgRecOnTotal", Double.class));
            return report;
        }
    }

    @Override
    public void save(GeneratedReport report) {
        String sql = "INSERT INTO generated_reports (agentID, totalNumberOfCalls, totalTalkTime, longestTalkTime, shortestTalkTime, totalTimeNotReady, avgRecOnTotal) " +
                "VALUES (:agentID, :totalNumberOfCalls, :totalTalkTime, :longestTalkTime, :shortestTalkTime, :totalTimeNotReady, :avgRecOnTotal)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agentID", report.getAgentID());
        params.addValue("totalNumberOfCalls", report.getTotalNumberOfCalls());
        params.addValue("totalTalkTime", report.getTotalTalkTime());
        params.addValue("longestTalkTime", report.getLongestTalkTime());
        params.addValue("shortestTalkTime", report.getShortestTalkTime());
        params.addValue("totalTimeNotReady", report.getTotalTimeNotReady());
        params.addValue("avgRecOnTotal", report.getAvgRecOnTotal());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<GeneratedReport> findAll() {
        String sql = "SELECT * FROM generated_reports";
        return namedParameterJdbcTemplate.query(sql, new GeneratedReportMapper());
    }
}
