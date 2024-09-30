package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.model.GeneratedReport;

import java.util.List;

public interface IGeneratedReportService {
    GeneratedReport getReportById(Long id);
    List<GeneratedReport> getAllReports();
    void saveReport(GeneratedReport report);
    void deleteReport(Long id);
    GeneratedReport generateDailyReport(Long agentId);
}
