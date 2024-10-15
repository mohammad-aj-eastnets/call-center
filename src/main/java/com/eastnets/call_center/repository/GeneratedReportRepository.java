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
            report.setTotalNumberOfCalls(rs.getInt("totalNumberOfCalls"));
            report.setTotalTalkTime(rs.getLong("totalTalkTime"));
            report.setLongestTalkTime(rs.getLong("longestTalkTime"));
            report.setShortestTalkTime(rs.getLong("shortestTalkTime"));
            report.setTotalTimeNotReady(rs.getLong("totalTimeNotReady"));
            report.setAvgRecOnTotal(rs.getDouble("avgRecOnTotal"));
            return report;
        }
    }

    @Override
    public void save(GeneratedReport report) {
        String sql = "INSERT INTO generated_reports (agentID, totalNumberOfCalls, totalTalkTime, longestTalkTime, shortestTalkTime, totalTimeNotReady, avgRecOnTotal) " +
                "VALUES (:agentID, :totalNumberOfCalls, :totalTalkTime, :longestTalkTime, :shortestTalkTime, :totalTimeNotReady, :avgRecOnTotal)";
        MapSqlParameterSource params = getSqlParameterSource(report);
        namedParameterJdbcTemplate.update(sql, params);
    }

    private static MapSqlParameterSource getSqlParameterSource(GeneratedReport report) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agentID", report.getAgentID());
        params.addValue("totalNumberOfCalls", report.getTotalNumberOfCalls());
        params.addValue("totalTalkTime", report.getTotalTalkTime());
        params.addValue("longestTalkTime", report.getLongestTalkTime());
        params.addValue("shortestTalkTime", report.getShortestTalkTime());
        params.addValue("totalTimeNotReady", report.getTotalTimeNotReady());
        params.addValue("avgRecOnTotal", report.getAvgRecOnTotal());
        return params;
    }

    @Override
    public void update(GeneratedReport report) {
        String sql = "UPDATE generated_reports SET totalNumberOfCalls = :totalNumberOfCalls, totalTalkTime = :totalTalkTime, longestTalkTime = :longestTalkTime, " +
                "shortestTalkTime = :shortestTalkTime, totalTimeNotReady = :totalTimeNotReady, avgRecOnTotal = :avgRecOnTotal WHERE agentID = :agentID";
        MapSqlParameterSource params = getMapSqlParameterSource(report);
        namedParameterJdbcTemplate.update(sql, params);
    }

    private static MapSqlParameterSource getMapSqlParameterSource(GeneratedReport report) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("totalNumberOfCalls", report.getTotalNumberOfCalls());
        params.addValue("totalTalkTime", report.getTotalTalkTime());
        params.addValue("longestTalkTime", report.getLongestTalkTime());
        params.addValue("shortestTalkTime", report.getShortestTalkTime());
        params.addValue("totalTimeNotReady", report.getTotalTimeNotReady());
        params.addValue("avgRecOnTotal", report.getAvgRecOnTotal());
        params.addValue("agentID", report.getAgentID());
        return params;
    }

    @Override
    public GeneratedReport findByAgentID(long agentID) {
        String sql = "SELECT * FROM generated_reports WHERE agentID = :agentID";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("agentID", agentID);
        List<GeneratedReport> reports = namedParameterJdbcTemplate.query(sql, params, new GeneratedReportMapper());
        return reports.isEmpty() ? null : reports.get(0);
    }

    @Override
    public List<GeneratedReport> findAll() {
        String sql = "SELECT * FROM generated_reports";
        return namedParameterJdbcTemplate.query(sql, new GeneratedReportMapper());
    }

    @Override
    public void truncateGeneratedReports() {
        String truncateGeneratedReports = "TRUNCATE TABLE generated_reports";
        namedParameterJdbcTemplate.getJdbcTemplate().execute(truncateGeneratedReports);
    }
}
