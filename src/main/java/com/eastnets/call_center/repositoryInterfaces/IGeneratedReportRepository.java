package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.model.GeneratedReport;

import java.util.List;

public interface IGeneratedReportRepository {
    GeneratedReport findById(Long id);
    List<GeneratedReport> findAll();
    void save(GeneratedReport report);
    void delete(Long id);
}
