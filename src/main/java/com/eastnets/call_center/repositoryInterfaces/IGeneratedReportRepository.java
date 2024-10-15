package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.model.GeneratedReport;
import java.util.List;

public interface IGeneratedReportRepository {
    void save(GeneratedReport report);

    void update(GeneratedReport report);

    GeneratedReport findByAgentID(long agentID);

    List<GeneratedReport> findAll();

    void truncateGeneratedReports();
}
