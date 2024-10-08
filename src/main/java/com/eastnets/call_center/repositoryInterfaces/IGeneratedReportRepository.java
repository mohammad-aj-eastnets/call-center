package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.model.GeneratedReport;
import java.util.List;

public interface IGeneratedReportRepository {
    void save(GeneratedReport report);
    List<GeneratedReport> findAll();
}
