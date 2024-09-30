package com.eastnets.call_center.service;

import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.repositoryInterfaces.IGeneratedReportRepository;
import com.eastnets.call_center.serviceInterfaces.IGeneratedReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GeneratedReportService implements IGeneratedReportService {

    private final IGeneratedReportRepository reportRepository;

    @Autowired
    public GeneratedReportService(IGeneratedReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public GeneratedReport getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public List<GeneratedReport> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public void saveReport(GeneratedReport report) {
        reportRepository.save(report);
    }

    @Override
    public void deleteReport(Long id) {
        reportRepository.delete(id);
    }

    @Override
    public GeneratedReport generateDailyReport(Long agentId) {
        // Logic to generate daily report for the agent
        GeneratedReport report = new GeneratedReport();
        report.setAgentId(agentId);
        report.setReportDate(LocalDate.now());
        // Set other fields based on business logic
        reportRepository.save(report);
        return report;
    }
}
