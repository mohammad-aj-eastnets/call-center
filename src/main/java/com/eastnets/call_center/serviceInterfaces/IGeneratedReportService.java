package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.model.GeneratedReport;
import java.util.List;

public interface IGeneratedReportService {
    void generateReports();
    List<GeneratedReport> getAllReports();
}
